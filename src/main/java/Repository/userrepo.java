package Repository;

import DB_connection.DBConnection;
import Model.User;

import java.sql.*;

public class userrepo {
    public User validateUser(String username, String password) {
        User user = null;
        Connection con = null;
        PreparedStatement pt = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
            pt = con.prepareStatement(query);
            pt.setString(1, username);
            pt.setString(2, password);

            rs = pt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("iduserinfo"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("picture")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public boolean registerUser(User user) {
        Connection con = null;
        PreparedStatement pt = null;

        try {
            con = DBConnection.getConnection();
            String query = "INSERT INTO userinfo (username, email, password, picture) VALUES (?, ?, ?, ?)";
            pt = con.prepareStatement(query);

            pt.setString(1, user.getUsername());
            pt.setString(2, user.getEmail());
            pt.setString(3, user.getPassword());
            pt.setString(4, user.getPicture());

            int rowsInserted = pt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserIdByUsername(String username) {
        Connection con = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        String iduserinfo = null;

        try {
            con = DBConnection.getConnection();
            String sql = "SELECT iduserinfo FROM userinfo WHERE username = ?";
            pt = con.prepareStatement(sql);
            pt.setString(1, username);

            rs = pt.executeQuery();
            if (rs.next()) {
                iduserinfo = rs.getString("iduserinfo");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return iduserinfo;
    }
    public void updateUser(User user) {
        Connection con = null;
        PreparedStatement pt = null;

        try {
            con = DBConnection.getConnection();
            String query = "UPDATE userinfo SET lastLogin = ? WHERE iduserinfo = ?";
            pt = con.prepareStatement(query);

            pt.setTimestamp(1, new Timestamp(user.getLastLogin().getTime()));
            pt.setInt(2, user.getId());

            pt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
