package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
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

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    ConnectionProperty prop;
    String select_all_products = "SELECT p.id, p.name, p.size, p.weight, "
            + "p.manufacturer_id, m.name as manufacturer_name, m.country "
            + "FROM products p "
            + "JOIN manufacturers m ON p.manufacturer_id = m.id "
            + "ORDER BY p.id";
    
    String select_all_manufacturers = "SELECT id, name, country, contact_person, phone FROM manufacturers ORDER BY id";
    
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    String userPath;
    
    public ProductsServlet() throws FileNotFoundException, IOException {
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
            
            // Загрузка всех производителей
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
            
            // Загрузка всех товаров
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
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
        
        userPath = request.getServletPath();
        if ("/products".equals(userPath)) {
            request.getRequestDispatcher("/view/products.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
