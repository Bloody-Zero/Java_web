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

@WebServlet("/deleteproduct")
public class DeleteProductServlet extends HttpServlet {
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
    
    String delete_product = "DELETE FROM products WHERE id = ?";
    
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    ArrayList<Product> deleteProducts = new ArrayList<>();
    
    String userPath;
    
    public DeleteProductServlet() throws FileNotFoundException, IOException {
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
            if (strId != null && !strId.isEmpty()) {
                id = Long.parseLong(strId);
            }
            
            // Загрузка всех производителей (для отображения названий)
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
            }
            
            // Загрузка товара для удаления
            if (id != null) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(select_product_ById)) {
                    preparedStatement.setLong(1, id);
                    rs = preparedStatement.executeQuery();
                    
                    if (rs != null) {
                        deleteProducts.clear();
                        while (rs.next()) {
                            manufacturerId = rs.getLong("manufacturer_id");
                            deleteProducts.add(new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("size"),
                                rs.getDouble("weight"),
                                manufacturerId,
                                findManufacturerById(manufacturerId, manufacturers)
                            ));
                        }
                        rs.close();
                        request.setAttribute("productDelete", deleteProducts);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка при загрузке товара для удаления: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
        
        userPath = request.getServletPath();
        if ("/deleteproduct".equals(userPath)) {
            request.getRequestDispatcher("/view/deleteproduct.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ProductConnBuilder builder = new ProductConnBuilder();
        
        try (Connection conn = builder.getConnection()) {
            
            String strId = request.getParameter("id");
            Long id = null;
            if (strId != null && !strId.isEmpty()) {
                id = Long.parseLong(strId);
            }
            
            // Выполнение DELETE запроса
            try (PreparedStatement preparedStatement = conn.prepareStatement(delete_product)) {
                preparedStatement.setLong(1, id);
                
                int result = preparedStatement.executeUpdate();
                System.out.println("Удалено записей: " + result);
                
                if (result > 0) {
                    request.getSession().setAttribute("success", "Товар успешно удален");
                } else {
                    request.getSession().setAttribute("error", "Не удалось удалить товар");
                }
                
            } catch (Exception e) {
                System.out.println("Ошибка при удалении товара: " + e.getMessage());
                e.printStackTrace();
                
                // Проверка на ошибку внешнего ключа
                if (e.getMessage().contains("foreign key")) {
                    request.getSession().setAttribute("error", 
                        "Невозможно удалить товар, так как на него есть ссылки в других таблицах");
                } else {
                    request.getSession().setAttribute("error", "Ошибка базы данных: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Ошибка подключения к базе данных");
        }
        
        // Перенаправление на страницу со списком товаров
        response.sendRedirect(request.getContextPath() + "/products");
    }
}