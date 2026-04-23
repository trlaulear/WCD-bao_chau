<%@ page import="com.example.demo.servlet.Player" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Player</title>
</head>
<body>
    <h1>Edit Player</h1>

    <form action="PlayerServlet" method="POST">
        <input type="hidden" name="id" value="<%= request.getAttribute("player").getId() %>">

        <label for="name">Player Name:</label>
        <input type="text" name="name" value="<%= request.getAttribute("player").getName() %>" required><br><br>

        <label for="full_name">Full Name:</label>
        <input type="text" name="full_name" value="<%= request.getAttribute("player").getFullName() %>" required><br><br>

        <label for="age">Player Age:</label>
        <input type="number" name="age" value="<%= request.getAttribute("player").getAge() %>" required><br><br>

        <label for="index">Index Name:</label>
        <select name="index" required>
            <option value="1" <%= (request.getAttribute("player").getIndexName().equals("Speed")) ? "selected" : "" %>>Speed</option>
            <option value="2" <%= (request.getAttribute("player").getIndexName().equals("Strength")) ? "selected" : "" %>>Strength</option>
            <option value="3" <%= (request.getAttribute("player").getIndexName().equals("Accuracy")) ? "selected" : "" %>>Accuracy</option>
        </select><br><br>

        <label for="value">Value:</label>
        <input type="number" name="value" value="<%= request.getAttribute("player").getValue() %>" required><br><br>

        <button type="submit">Update Player</button>
    </form>
</body>
</html>