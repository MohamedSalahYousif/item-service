package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.regex.Pattern;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Resource(name = "jdbc/web_item")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        addUser(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!isValidInput(username, password)) {
            request.setAttribute("errorMessage", "Invalid input. Please ensure your username and password meet the requirements.");
            try {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                log("Error forwarding to login.jsp", e);
            }
            return;
        }

        try {
            saveUser(username, password);
            request.setAttribute("successMessage", "User registered successfully!");
            try {
				response.sendRedirect("home.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (SQLException e) {
            log("Database error during user registration", e);
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            try {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Error forwarding to login.jsp", ex);
            }
        }
    }

    private void saveUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }

    private boolean isValidInput(String username, String password) {
        String usernameRegex = "^[a-zA-Z0-9]{3,20}$"; 
        String passwordRegex = "^.{6,}$"; 
        return Pattern.matches(usernameRegex, username) && Pattern.matches(passwordRegex, password);
    }
}
