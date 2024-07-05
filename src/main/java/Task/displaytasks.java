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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/displaytaskservlet")
public class displaytasks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String userid = (String) session.getAttribute("userid");

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp", "root", "020510Dev#T");

            String sql = "SELECT * FROM tasks WHERE userid = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, userid);

            ResultSet rs = pt.executeQuery();

            List<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                int idtasks = rs.getInt("idtasks");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String priority = rs.getString("priority");
                String description = rs.getString("description");

                Task task = new Task(idtasks, title, date, priority, description); // Include ID
                tasks.add(task);
            }

            // Set tasks as a request attribute
            req.setAttribute("tasks", tasks);

            // Forward to JSP page
            req.getRequestDispatcher("/displaytasks.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
