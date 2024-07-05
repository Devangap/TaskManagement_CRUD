package Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/updatetask")
public class updatetask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve task parameters from request
        String taskId = request.getParameter("taskId");
        String taskTitle = request.getParameter("taskTitle");
        String taskDate = request.getParameter("taskDate");
        String priority = request.getParameter("priority");
        String description = request.getParameter("description");

        // Database connection details
        String dbUrl = "jdbc:mysql://localhost:3306/jsp";
        String dbUser = "root";
        String dbPassword = "020510Dev#T";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "UPDATE tasks SET title = ?, date = ?, priority = ?, description = ? WHERE idtasks = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, taskTitle);
                statement.setString(2, taskDate);
                statement.setString(3, priority);
                statement.setString(4, description);
                statement.setInt(5, Integer.parseInt(taskId));

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Task updated successfully!");
                } else {
                    System.out.println("Task update failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Redirect to the display tasks servlet
        response.sendRedirect("displaytaskservlet");
    }
}
