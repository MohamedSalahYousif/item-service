<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        h1 {
            color: #4CAF50;
            text-align: center;
            margin-bottom: 20px;
        }

        h2 {
            color: #555;
            text-align: center;
            margin-bottom: 15px;
        }

        h3 {
            color: #007BFF;
            text-align: center;
            cursor: pointer;
            text-decoration: none;
            transition: color 0.3s;
        }

        h3:hover {
            color: #0056b3;
        }

        .welcome-container {
            text-align: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto;
        }

        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <h1>Welcome to the Home Page!</h1>
        
        <%
            String username = (String) session.getAttribute("username");
            if (username != null) {
        %>
            <h2>Hello, <%= username %>!</h2>
        <%
            } else {
        %>
            <h2>Hello, Guest!</h2>
        <%
            }
        %>
        
        <a href="ItemController">
            <h3>Start Work</h3>
        </a>
    </div>
</body>
</html>

