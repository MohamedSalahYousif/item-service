package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Resource(name = "jdbc/web_item")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authenticateUser(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check for cookies
        Cookie[] cookies = request.getCookies();
        String username = "";
        String password = "";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }

        request.setAttribute("username", username);
        request.setAttribute("password", password);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }


    
    private void authenticateUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        try {
            if (validateUser(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                // Set cookie for "Remember Me"
                if ("on".equals(rememberMe)) {
                    Cookie usernameCookie = new Cookie("username", username);
                    usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                    usernameCookie.setPath("/"); // Set the path to root
                    response.addCookie(usernameCookie);

                    Cookie passwordCookie = new Cookie("password", password);
                    passwordCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                    passwordCookie.setPath("/");
                    passwordCookie.setHttpOnly(true); // Prevent JavaScript access
                    response.addCookie(passwordCookie);
                }

                response.sendRedirect("home.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login. Please try again.");
            try {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return password.equals(storedPassword); 
                }
            }
        }
        return false; 
    }
}
