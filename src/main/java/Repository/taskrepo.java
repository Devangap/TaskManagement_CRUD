package Repository;


import DB_connection.DBConnection;
import Model.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class taskrepo {

    public boolean saveTask(Task task) {
        boolean isSuccess = false;
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO tasks (title, date, priority, description, userid, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setString(1, task.getTitle());
                pt.setString(2, task.getDate());
                pt.setString(3, task.getPriority());
                pt.setString(4, task.getDescription());
                pt.setInt(5, task.getUsername());
                pt.setString(6, task.getStatus());
                isSuccess = pt.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean deleteTask(int taskId) {
        boolean isSuccess = false;
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM tasks WHERE idtasks = ?";
            try (PreparedStatement pt = con.prepareStatement(query)) {
                pt.setInt(1, taskId);
                isSuccess = pt.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean updateTask(int taskId, String title, String date, String priority, String description) {
        boolean isSuccess = false;
        try (Connection con = DBConnection.getConnection()) {
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
        }
        return isSuccess;
    }
}

