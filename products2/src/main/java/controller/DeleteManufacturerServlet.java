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

@WebServlet("/deletemanufacturer")
public class DeleteManufacturerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    ConnectionProperty prop;
    String select_all_manufacturers = "SELECT id, name, country, contact_person, phone FROM manufacturers ORDER BY name";
    String select_manufacturer_ById = "SELECT id, name, country, contact_person, phone FROM manufacturers WHERE id = ?";
    String delete_manufacturer = "DELETE FROM manufacturers WHERE id = ?";
    
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    ArrayList<Manufacturer> deleteManufacturers = new ArrayList<>();
    String userPath;
    
    public DeleteManufacturerServlet() throws FileNotFoundException, IOException {
        super();
        prop = new ConnectionProperty();
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
            
            // Загрузка производителя для удаления
            try (PreparedStatement preparedStatement = conn.prepareStatement(select_manufacturer_ById)) {
                preparedStatement.setLong(1, id);
                rs = preparedStatement.executeQuery();
                
                if (rs != null) {
                    deleteManufacturers.clear();
                    while (rs.next()) {
                        deleteManufacturers.add(new Manufacturer(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getString("contact_person"),
                            rs.getString("phone")
                        ));
                    }
                    rs.close();
                    request.setAttribute("manufacturerDelete", deleteManufacturers);
                } else {
                    System.out.println("Ошибка загрузки производителя для удаления");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        userPath = request.getServletPath();
        if ("/deletemanufacturer".equals(userPath)) {
            request.getRequestDispatcher("/view/deletemanufacturer.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ProductConnBuilder builder = new ProductConnBuilder();
        
        try (Connection conn = builder.getConnection()) {
            
            String strId = request.getParameter("id");
            Long id = null;
            if (strId != null) {
                id = Long.parseLong(strId);
            }
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(delete_manufacturer)) {
                preparedStatement.setLong(1, id);
                
                int result = preparedStatement.executeUpdate();
                System.out.println("Удалено записей: " + result);
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        response.sendRedirect(request.getContextPath() + "/manufacturers");
    }
}