package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manufacturers")
public class ManufacturerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Перенаправление на JSP страницу
        request.getRequestDispatcher("/view/manufacturers.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Здесь будет обработка POST запроса (добавление нового производителя)
        // Пока просто перенаправляем на GET
        doGet(request, response);
    }
}