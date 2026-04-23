package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playerName = request.getParameter("name");
        int playerAge = Integer.parseInt(request.getParameter("age"));
        int indexId = Integer.parseInt(request.getParameter("index"));
        float value = Float.parseFloat(request.getParameter("value"));

        // Insert the new player information
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/player_evaluation", "root", "")) {
            String query = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, getPlayerId(con, playerName, playerAge));
            stmt.setInt(2, indexId);
            stmt.setFloat(3, value);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetch the player list to display on the page
        List<Player> playerList = null;
        try {
            playerList = getPlayerList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Set the playerList as a request attribute to be used in JSP
        request.setAttribute("playerList", playerList);

        // Forward the request to the JSP page
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private int getPlayerId(Connection con, String playerName, int playerAge) throws SQLException {
        String query = "SELECT player_id FROM player WHERE name = ? AND age = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, playerName);
        stmt.setInt(2, playerAge);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("player_id");
        }
        return -1;  // Return -1 if no player is found
    }

    private List<Player> getPlayerList() throws SQLException {
        List<Player> playerList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/player_evaluation", "root", "")) {
            String query = "SELECT p.player_id, p.name, p.age, i.name AS index_name, pi.value " +
                    "FROM player p " +
                    "JOIN player_index pi ON p.player_id = pi.player_id " +
                    "JOIN indexer i ON pi.index_id = i.index_id";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("index_name"),
                        rs.getFloat("value")
                );
                playerList.add(player);
            }
        }
        return playerList;
    }

}