//package com.example.demo;
//
//import com.example.demo.servlet.Player;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
////@WebServlet("/PlayerServlet")
//public class PlayerServlet extends HttpServlet {
//
//    // Define the connection parameters
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/player_evaluation";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "";
//
//    // For handling new player creation
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String playerName = request.getParameter("name");
//        int playerAge = Integer.parseInt(request.getParameter("age"));
//        int indexId = Integer.parseInt(request.getParameter("index"));
//        float value = Float.parseFloat(request.getParameter("value"));
//
//        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            String query = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
//            PreparedStatement stmt = con.prepareStatement(query);
//            stmt.setInt(1, getPlayerId(con, playerName, playerAge));
//            stmt.setInt(2, indexId);
//            stmt.setFloat(3, value);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Fetch the player list to display on the page
//        List<Player> playerList = null;
//        try {
//            playerList = getPlayerList();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Set the playerList as a request attribute to be used in JSP
//        request.setAttribute("playerList", playerList);
//
//        // Forward the request to the JSP page
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
//    }
//
//    // Get the player ID based on the name and age
//    private int getPlayerId(Connection con, String playerName, int playerAge) throws SQLException {
//        String query = "SELECT player_id FROM player WHERE name = ? AND age = ?";
//        PreparedStatement stmt = con.prepareStatement(query);
//        stmt.setString(1, playerName);
//        stmt.setInt(2, playerAge);
//        ResultSet rs = stmt.executeQuery();
//
//        if (rs.next()) {
//            return rs.getInt("player_id");
//        }
//        return -1;  // Return -1 if no player is found
//    }
//
//    // Get a list of all players
//    private List<Player> getPlayerList() throws SQLException {
//        List<Player> playerList = new ArrayList<>();
//
//        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            String query = "SELECT p.player_id, p.name, p.age, i.name AS index_name, pi.value " +
//                    "FROM player p " +
//                    "JOIN player_index pi ON p.player_id = pi.player_id " +
//                    "JOIN indexer i ON pi.index_id = i.index_id";
//            PreparedStatement stmt = con.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Player player = new Player(
//                        rs.getInt("player_id"),
//                        rs.getString("name"),
//                        rs.getInt("age"),
//                        rs.getString("index_name"),
//                        rs.getFloat("value")
//                );
//                playerList.add(player);
//            }
//        }
//        return playerList;
//    }
//
//    // For editing a player's details
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if ("edit".equals(action)) {
//            int playerId = Integer.parseInt(request.getParameter("id"));
//            Player player = null;
//            try {
//                player = getPlayerById(playerId);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            request.setAttribute("player", player);
//            request.getRequestDispatcher("/editPlayer.jsp").forward(request, response);
//        } else if ("delete".equals(action)) {
//            int playerId = Integer.parseInt(request.getParameter("id"));
//            deletePlayer(playerId);
//            response.sendRedirect("index.jsp");
//        }
//    }
//
//    // Fetch a specific player by their ID
//    private Player getPlayerById(int playerId) throws SQLException {
//        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            String query = "SELECT p.player_id, p.name, p.age, i.name AS index_name, pi.value " +
//                    "FROM player p " +
//                    "JOIN player_index pi ON p.player_id = pi.player_id " +
//                    "JOIN indexer i ON pi.index_id = i.index_id " +
//                    "WHERE p.player_id = ?";
//            PreparedStatement stmt = con.prepareStatement(query);
//            stmt.setInt(1, playerId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return new Player(
//                        rs.getInt("player_id"),
//                        rs.getString("name"),
//                        rs.getInt("age"),
//                        rs.getString("index_name"),
//                        rs.getFloat("value")
//                );
//            }
//        }
//        return null;
//    }
//
//    // Delete a player from the database
//    private void deletePlayer(int playerId) {
//        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//            String query = "DELETE FROM player_index WHERE player_id = ?";
//            PreparedStatement stmt = con.prepareStatement(query);
//            stmt.setInt(1, playerId);
//            stmt.executeUpdate();
//
//            query = "DELETE FROM player WHERE player_id = ?";
//            stmt = con.prepareStatement(query);
//            stmt.setInt(1, playerId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}