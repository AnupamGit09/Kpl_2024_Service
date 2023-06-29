<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.kpl.registration.dto.RegistrationResponse" %> <!-- Assuming you have a User class with properties: id, name, etc. -->

<!DOCTYPE html>
<html>
<head>
    <title>Personal Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
        }
        .user-details {
            margin-bottom: 10px;
        }
        .user-details label {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>${name}'s Registration Details</h2>
        <div class="user-details">
            <label>ID:</label>
            <span>${id}</span>
        </div>
        <div class="user-details">
            <label>Name:</label>
            <span>${name}</span>
        </div>
        <!-- <div class="user-details">
            <label>Email:</label>
            <span>${user.email}</span>
        </div> -->
        <!-- Add more user details as needed -->
    </div>
</body>
</html>
