<%@ page import="com.example.demo.servlet.Player" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Player</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        label {
            font-size: 18px;
        }
        input, select {
            font-size: 16px;
            padding: 8px;
            margin: 10px 0;
            width: 100%;
        }
        button {
            font-size: 18px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h1>Edit Player Information</h1>

    <form action="PlayerServlet" method="POST">
        <input type="hidden" name="id" value="<%= request.getAttribute("player").getId() %>">
        <label for="name">Player Name:</label>
        <input type="text" name="name" value="<%= request.getAttribute("player").getName() %>" required><br>

        <label for="age">Player Age:</label>
        <input type="number" name="age" value="<%= request.getAttribute("player").getAge() %>" required><br>

        <label for="index">Index Name:</label>
        <select name="index" required>
            <option value="1" <%= request.getAttribute("player").getIndexName().equals("Speed") ? "selected" : "" %>>Speed</option>
            <option value="2" <%= request.getAttribute("player").getIndexName().equals("Strength") ? "selected" : "" %>>Strength</option>
            <option value="3" <%= request.getAttribute("player").getIndexName().equals("Accuracy") ? "selected" : "" %>>Accuracy</option>
        </select><br>

        <label for="value">Value:</label>
        <input type="number" name="value" value="<%= request.getAttribute("player").getValue() %>" required><br>

        <button type="submit">Update Player</button>
    </form>

    <br>
    <a href="index.jsp">Back to Player List</a>
</body>
</html>