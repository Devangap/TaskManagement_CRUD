package Repository;

import DB_connection.DBConnection;
import Model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userrepo{
    public User validateUser(String username, String password) {
        User user = null;
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setString(1, username);
                pt.setString(2, password);
                try (ResultSet rs = pt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(rs.getInt("iduserinfo"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("picture"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean registerUser(User user) {
        boolean isRegistered = false;
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO userinfo (username, email, password, picture) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setString(1, user.getUsername());
                pt.setString(2, user.getEmail());
                pt.setString(3, user.getPassword());
                pt.setString(4, user.getPicture());
                int rowsInserted = pt.executeUpdate();
                isRegistered = rowsInserted > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isRegistered;
    }
    public String getUserIdByUsername(String username) {
        String iduserinfo = null;
        String sql = "SELECT iduserinfo FROM userinfo WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pt = con.prepareStatement(sql)) {

            pt.setString(1, username);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                iduserinfo = rs.getString("iduserinfo");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle or log the exception properly
        }

        return iduserinfo;
    }
}
