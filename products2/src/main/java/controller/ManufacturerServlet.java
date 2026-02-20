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
import domain.Manufacturer;

@WebServlet("/manufacturers")
public class ManufacturerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    ConnectionProperty prop;
    String select_all_manufacturers = "SELECT id, name, country, contact_person, phone FROM manufacturers ORDER BY id";
    String insert_manufacturer = "INSERT INTO manufacturers (name, country, contact_person, phone) VALUES(?, ?, ?, ?)";
    
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    String userPath;
    
    public ManufacturerServlet() throws FileNotFoundException, IOException {
        prop = new ConnectionProperty();
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
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
        
        userPath = request.getServletPath();
        if ("/manufacturers".equals(userPath)) {
            request.getRequestDispatcher("/view/manufacturers.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ProductConnBuilder builder = new ProductConnBuilder();
        
        try (Connection conn = builder.getConnection()) {
            
            // Получение параметров из формы
            String name = request.getParameter("name");
            String country = request.getParameter("country");
            String contactPerson = request.getParameter("contactPerson");
            String phone = request.getParameter("phone");
            
            // Создание объекта нового производителя
            Manufacturer newManufacturer = new Manufacturer(name, country, contactPerson, phone);
            
            // Подготовка и выполнение INSERT запроса
            try (PreparedStatement preparedStatement = conn.prepareStatement(insert_manufacturer)) {
                preparedStatement.setString(1, newManufacturer.getName());
                preparedStatement.setString(2, newManufacturer.getCountry());
                preparedStatement.setString(3, newManufacturer.getContactPerson());
                preparedStatement.setString(4, newManufacturer.getPhone());
                
                int result = preparedStatement.executeUpdate();
                System.out.println("Добавлено записей: " + result);
                
            } catch (Exception e) {
                System.out.println("Ошибка при добавлении производителя: " + e.getMessage());
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
            // В случае ошибки перенаправляем на страницу
            getServletContext().getRequestDispatcher("/view/manufacturers.jsp").forward(request, response);
            return;
        }
        
        // После успешного добавления вызываем doGet для обновления списка
        doGet(request, response);
    }
}