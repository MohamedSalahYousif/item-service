<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login & Sign Up</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="login-wrap">
    <div class="login-html">
        <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
        <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
        <div class="login-form">

          <form action="login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${not empty username ? username : ''}">
    
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="${not empty password ? password : ''}">
    
    <label for="rememberMe">Remember Me:</label>
    <input type="checkbox" id="rememberMe" name="rememberMe">
    
    <button type="submit">Login</button>
    
</form>
           <!-- Sign Up Form -->
            <form class="sign-up-htm" action="signup" method="post"> <!-- Form submits to SignupServlet -->
                <div class="group">
                    <label for="user" class="label">Username</label>
                    <input id="user" name="username" type="text" class="input">
                </div>
                <div class="group">
                    <label for="pass" class="label">Password</label>
                    <input id="pass" name="password" type="password" class="input" data-type="password">
                </div>
                <div class="group">
                    <input type="submit" class="button" value="Sign Up">
                </div>
                <div class="hr"></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
