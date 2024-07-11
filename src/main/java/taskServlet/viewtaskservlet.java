package taskServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import Model.Task;



@WebServlet("/tasks")
public class viewtaskservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String dbURL = "jdbc:mysql://localhost:3306/jsp";
    private static final String dbUser = "root";
    private static final String dbPassword = "020510Dev#T";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Connection con = null;
        List<Task> tasks = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // Assuming userid is passed as a parameter
            int userid = Integer.parseInt(request.getParameter("userid"));
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

            // Convert tasks list to JSON using Gson
            Gson gson = new Gson();
            String tasksJson = gson.toJson(tasks);

            out.println(tasksJson);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }
    }
}
