package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ConnectionProperty;
import dao.ProductConnBuilder;
import domain.Product;
import domain.Manufacturer;

@WebServlet("/editproduct")
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    ConnectionProperty prop;
    
    // SQL запросы
    String select_all_products = "SELECT p.id, p.name, p.size, p.weight, "
            + "p.manufacturer_id, m.name as manufacturer_name, m.country, "
            + "m.contact_person, m.phone "
            + "FROM products p "
            + "JOIN manufacturers m ON p.manufacturer_id = m.id "
            + "ORDER BY p.id";
    
    String select_all_manufacturers = "SELECT id, name, country, contact_person, phone FROM manufacturers ORDER BY name";
    
    String select_product_ById = "SELECT p.id, p.name, p.size, p.weight, "
            + "p.manufacturer_id, m.name as manufacturer_name, m.country, "
            + "m.contact_person, m.phone "
            + "FROM products p "
            + "JOIN manufacturers m ON p.manufacturer_id = m.id "
            + "WHERE p.id = ?";
    
    String edit_product = "UPDATE products SET name = ?, size = ?, weight = ?, manufacturer_id = ? WHERE id = ?";
    
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    ArrayList<Product> editProducts = new ArrayList<>();
    
    String userPath;
    
    public EditProductServlet() throws FileNotFoundException, IOException {
        super();
        prop = new ConnectionProperty();
    }
    
    // Поиск производителя по id
    private Manufacturer findManufacturerById(Long id, ArrayList<Manufacturer> manufacturers) {
        if (manufacturers != null) {
            for (Manufacturer m : manufacturers) {
                if (m.getId().equals(id)) {
                    return m;
                }
            }
        }
        return null;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        
        ProductConnBuilder builder = new ProductConnBuilder();
        
        try (Connection conn = builder.getConnection()) {
            
            String strId = request.getParameter("id");
            Long id = null;
            if (strId != null) {
                id = Long.parseLong(strId);
            }
            
            // Загрузка всех производителей (для выпадающего списка)
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select_all_manufacturers);
            
            if (rs != null) {
                manufacturers.clear();
                while (rs.next()) {
                    manufacturers.add(new Manufacturer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getString("contact_person"),
                        rs.getString("phone")
                    ));
                }
                rs.close();
                request.setAttribute("manufacturers", manufacturers);
            } else {
                System.out.println("Ошибка загрузки производителей");
            }
            
            // Загрузка всех товаров (для отображения в левой части)
            Long manufacturerId;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(select_all_products);
            
            if (rs != null) {
                products.clear();
                while (rs.next()) {
                    manufacturerId = rs.getLong("manufacturer_id");
                    products.add(new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getDouble("weight"),
                        manufacturerId,
                        findManufacturerById(manufacturerId, manufacturers)
                    ));
                }
                rs.close();
                request.setAttribute("products", products);
            } else {
                System.out.println("Ошибка загрузки товаров");
            }
            
            // Загрузка редактируемого товара
            try (PreparedStatement preparedStatement = conn.prepareStatement(select_product_ById)) {
                preparedStatement.setLong(1, id);
                rs = preparedStatement.executeQuery();
                
                if (rs != null) {
                    editProducts.clear();
                    while (rs.next()) {
                        manufacturerId = rs.getLong("manufacturer_id");
                        editProducts.add(new Product(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("size"),
                            rs.getDouble("weight"),
                            manufacturerId,
                            findManufacturerById(manufacturerId, manufacturers)
                        ));
                    }
                    rs.close();
                    request.setAttribute("productEdit", editProducts);
                } else {
                    System.out.println("Ошибка загрузки товара для редактирования");
                }
            } catch (Exception e) {
                System.out.println("Ошибка при загрузке товара: " + e.getMessage());
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
        
        userPath = request.getServletPath();
        if ("/editproduct".equals(userPath)) {
            request.getRequestDispatcher("/view/editproduct.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ProductConnBuilder builder = new ProductConnBuilder();
        
        try (Connection conn = builder.getConnection()) {
            
            // Получение параметров из формы
            String strId = request.getParameter("id");
            Long id = null;
            if (strId != null && !strId.isEmpty()) {
                id = Long.parseLong(strId);
            }
            
            String name = request.getParameter("name");
            String size = request.getParameter("size");
            String weightStr = request.getParameter("weight");
            String manufacturerIdStr = request.getParameter("manufacturerId");
            
            // Валидация данных
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Название товара не может быть пустым");
                doGet(request, response);
                return;
            }
            
            Double weight = 0.0;
            try {
                weight = Double.parseDouble(weightStr);
            } catch (NumberFormatException e) {
                weight = 0.0;
            }
            
            Long manufacturerId = null;
            try {
                manufacturerId = Long.parseLong(manufacturerIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Не выбран производитель");
                doGet(request, response);
                return;
            }
            
            // Выполнение UPDATE запроса
            try (PreparedStatement preparedStatement = conn.prepareStatement(edit_product)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, size);
                preparedStatement.setDouble(3, weight);
                preparedStatement.setLong(4, manufacturerId);
                preparedStatement.setLong(5, id);
                
                int result = preparedStatement.executeUpdate();
                System.out.println("Обновлено записей: " + result);
                
                if (result > 0) {
                    request.setAttribute("success", "Товар успешно обновлен");
                } else {
                    request.setAttribute("error", "Не удалось обновить товар");
                }
                
            } catch (Exception e) {
                System.out.println("Ошибка при обновлении товара: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("error", "Ошибка базы данных: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Ошибка подключения к базе данных");
        }
        
        // Перенаправляем на GET для обновления отображения
        doGet(request, response);
    }
}