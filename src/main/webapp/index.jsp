<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.servlet.Player" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <h1>Player Information</h1>

    <!-- Add Player Form -->
    <form action="PlayerServlet" method="POST">
        <label for="name">Player Name:</label>
        <input type="text" name="name" required><br><br>

        <label for="full_name">Full Name:</label>
        <input type="text" name="full_name" required><br><br>

        <label for="age">Player Age:</label>
        <input type="number" name="age" required><br><br>

        <label for="index">Index Name:</label>
        <select name="index" required>
            <option value="1">Speed</option>
            <option value="2">Strength</option>
            <option value="3">Accuracy</option>
        </select><br><br>

        <label for="value">Value:</label>
        <input type="number" name="value" required><br><br>

        <button type="submit">Add Player</button>
    </form>

    <h2>Players List</h2>
    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Player Name</th>
                <th>Full Name</th>
                <th>Player Age</th>
                <th>Index Name</th>
                <th>Value</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Display list of players -->
            <%
                List<Player> playerList = (List<Player>) request.getAttribute("playerList");
                if (playerList != null) {
                    for (Player p : playerList) {
            %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getFullName() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getIndexName() %></td>
                    <td><%= p.getValue() %></td>
                    <td>
                        <!-- Edit Button -->
                        <a href="PlayerServlet?action=edit&id=<%= p.getId() %>">Edit</a> |
                        <!-- Delete Button -->
                        <a href="PlayerServlet?action=delete&id=<%= p.getId() %>">Delete</a>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>