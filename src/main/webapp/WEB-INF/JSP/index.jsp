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

    <form action="PlayerServlet" method="POST">
        <label for="name">Player Name:</label>
        <input type="text" name="name" required><br><br>

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
                <th>Player Age</th>
                <th>Index Name</th>
                <th>Value</th>
            </tr>
        </thead>
        <tbody>
            <!-- The list of players will be displayed here -->
            <%
                // Retrieve the playerList attribute from the request
                List<Player> playerList = (List<Player>) request.getAttribute("playerList");
                if (playerList != null) {
                    for (Player p : playerList) {
            %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getIndexName() %></td>
                    <td><%= p.getValue() %></td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>