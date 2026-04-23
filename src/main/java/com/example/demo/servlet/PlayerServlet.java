package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PlayerServlet")
public class PlayerServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/player_evaluation";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load MySQL JDBC driver");
        }
    }

    // For handling new player creation (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playerName = request.getParameter("name");
        String fullName = request.getParameter("full_name");
        int playerAge = Integer.parseInt(request.getParameter("age"));
        int indexId = Integer.parseInt(request.getParameter("index"));
        float value = Float.parseFloat(request.getParameter("value"));

        int playerId = -1; // Store generated player_id

        try (Connection con = getConnection()) {
            String playerQuery = "INSERT INTO player (name, full_name, age, index_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement playerStmt = con.prepareStatement(playerQuery, Statement.RETURN_GENERATED_KEYS)) {
                playerStmt.setString(1, playerName);
                playerStmt.setString(2, fullName);
                playerStmt.setInt(3, playerAge);
                playerStmt.setInt(4, indexId);
                playerStmt.executeUpdate();

                try (ResultSet rs = playerStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        playerId = rs.getInt(1); // Capture the generated player_id
                    }
                }

                // Now insert into the player_index table using the generated player_id
                String playerIndexQuery = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
                try (PreparedStatement indexStmt = con.prepareStatement(playerIndexQuery)) {
                    indexStmt.setInt(1, playerId);
                    indexStmt.setInt(2, indexId);
                    indexStmt.setFloat(3, value);
                    indexStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during player creation", e);
        }

        // Fetch the player list to display on the page
        List<Player> playerList = null;
        try {
            playerList = getPlayerList();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during fetching player list", e);
        }

        request.setAttribute("playerList", playerList);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // Helper method to get the database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Get a list of all players
    private List<Player> getPlayerList() throws SQLException {
        List<Player> playerList = new ArrayList<>();

        try (Connection con = getConnection()) {
            String query = "SELECT p.player_id, p.name, p.full_name, p.age, i.name AS index_name, pi.value " +
                    "FROM player p " +
                    "JOIN player_index pi ON p.player_id = pi.player_id " +
                    "JOIN indexer i ON pi.index_id = i.index_id";
            try (PreparedStatement stmt = con.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Player player = new Player(
                            rs.getInt("player_id"),
                            rs.getString("name"),
                            rs.getString("full_name"),
                            rs.getInt("age"),
                            rs.getString("index_name"),
                            rs.getFloat("value")
                    );
                    playerList.add(player);
                }
            }
        }
        return playerList;
    }

    // Fetch a specific player by their ID (for Edit)
    private Player getPlayerById(int playerId) throws SQLException {
        try (Connection con = getConnection()) {
            String query = "SELECT p.player_id, p.name, p.full_name, p.age, i.name AS index_name, pi.value " +
                    "FROM player p " +
                    "JOIN player_index pi ON p.player_id = pi.player_id " +
                    "JOIN indexer i ON pi.index_id = i.index_id " +
                    "WHERE p.player_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, playerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Player(
                                rs.getInt("player_id"),
                                rs.getString("name"),
                                rs.getString("full_name"),
                                rs.getInt("age"),
                                rs.getString("index_name"),
                                rs.getFloat("value")
                        );
                    }
                }
            }
        }
        return null;
    }

    // Handle GET request for editing or deleting a player
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int playerId = Integer.parseInt(request.getParameter("id"));
            Player player = null;
            try {
                player = getPlayerById(playerId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("player", player);
            request.getRequestDispatcher("/editPlayer.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int playerId = Integer.parseInt(request.getParameter("id"));
            deletePlayer(playerId);
            response.sendRedirect("index.jsp");
        }
    }

    // Delete a player from the database
    private void deletePlayer(int playerId) {
        try (Connection con = getConnection()) {
            String query = "DELETE FROM player_index WHERE player_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, playerId);
                stmt.executeUpdate();
            }

            query = "DELETE FROM player WHERE player_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, playerId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}