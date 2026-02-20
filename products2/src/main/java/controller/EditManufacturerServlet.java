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

@WebServlet("/editmanufacturer")
public class EditManufacturerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    ConnectionProperty prop;
    String select_all_manufacturers = "SELECT id, name, country, contact_person, phone FROM manufacturers ORDER BY name";
    String select_manufacturer_ById = "SELECT id, name, country, contact_person, phone FROM manufacturers WHERE id = ?";
    String edit_manufacturer = "UPDATE manufacturers SET name = ?, country = ?, contact_person = ?, phone = ? WHERE id = ?";
    
    ArrayList<Manufacturer> manufacturers = new ArrayList<>();
    ArrayList<Manufacturer> editManufacturers = new ArrayList<>();
    String userPath;
    
    public EditManufacturerServlet() throws FileNotFoundException, IOException {
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
            
            // Загрузка редактируемого производителя
            try (PreparedStatement preparedStatement = conn.prepareStatement(select_manufacturer_ById)) {
                preparedStatement.setLong(1, id);
                rs = preparedStatement.executeQuery();
                
                if (rs != null) {
                    editManufacturers.clear();
                    while (rs.next()) {
                        editManufacturers.add(new Manufacturer(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getString("contact_person"),
                            rs.getString("phone")
                        ));
                    }
                    rs.close();
                    request.setAttribute("manufacturerEdit", editManufacturers);
                } else {
                    System.out.println("Ошибка загрузки производителя для редактирования");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        userPath = request.getServletPath();
        if ("/editmanufacturer".equals(userPath)) {
            request.getRequestDispatcher("/view/editmanufacturer.jsp").forward(request, response);
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
            
            String name = request.getParameter("name");
            String country = request.getParameter("country");
            String contactPerson = request.getParameter("contactPerson");
            String phone = request.getParameter("phone");
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(edit_manufacturer)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, country);
                preparedStatement.setString(3, contactPerson);
                preparedStatement.setString(4, phone);
                preparedStatement.setLong(5, id);
                
                int result = preparedStatement.executeUpdate();
                System.out.println("Обновлено записей: " + result);
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        doGet(request, response);
    }
}