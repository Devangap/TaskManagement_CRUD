package Repository;

import DB_connection.DBConnection;
import Model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class taskrepo {

    public boolean checkTaskTitleExists(String title) {
        Connection con = null;
        boolean titleExists = false;
        try {
            con = DBConnection.getConnection(); // Get connection from DBConnection class
            String query = "SELECT COUNT(*) AS count FROM tasks WHERE title = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    titleExists = true;
                }
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
        return titleExists;
    }

    public boolean saveTask(Task task) {
        boolean isSuccess = false;
        Connection con = null;
        try {
            con = DBConnection.getConnection(); // Get connection from DBConnection class
            String query = "INSERT INTO tasks (title, date, priority, description, userid, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setString(1, task.getTitle());
                pt.setString(2, task.getDate());
                pt.setString(3, task.getPriority());
                pt.setString(4, task.getDescription());
                pt.setString(5, task.getUsername());
                pt.setString(6, task.getStatus());
                isSuccess = pt.executeUpdate() > 0;
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
        return isSuccess;
    }

    public boolean deleteTask(int taskId) {
        boolean isSuccess = false;
        Connection con = null;
        try {
            con = DBConnection.getConnection(); // Get connection from DBConnection class
            String query = "DELETE FROM tasks WHERE idtasks = ?";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setInt(1, taskId);
                isSuccess = pt.executeUpdate() > 0;
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
        return isSuccess;
    }

    public boolean updateTask(int taskId, String title, String date, String priority, String description) {
        boolean isSuccess = false;
        Connection con = null;
        try {
            con = DBConnection.getConnection(); // Get connection from DBConnection class
            String query = "UPDATE tasks SET title = ?, date = ?, priority = ?, description = ? WHERE idtasks = ?";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setString(1, title);
                pt.setString(2, date);
                pt.setString(3, priority);
                pt.setString(4, description);
                pt.setInt(5, taskId);
                isSuccess = pt.executeUpdate() > 0;
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
        return isSuccess;
    }

    public List<Task> getTasksByUserId(int userid) throws SQLException, ClassNotFoundException {
        List<Task> tasks = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnection.getConnection(); // Get connection from DBConnection class
            String sql = "SELECT * FROM tasks WHERE userid = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setInt(1, userid);
            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                int idtasks = rs.getInt("idtasks");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String priority = rs.getString("priority");
                String description = rs.getString("description");

                Task task = new Task(idtasks, title, date, priority, description);
                tasks.add(task);
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
        return tasks;
    }
}
