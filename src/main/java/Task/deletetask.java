package Task;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet("/deletetask")
public class deletetask extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve task ID from the request
        String idtasks = req.getParameter("taskId");
        System.out.println("Retrieved Task ID: " + idtasks);

        // Get the userid from the session
        HttpSession session = req.getSession(false);

        Connection con = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp", "root", "020510Dev#T");

            // Prepare SQL statement to delete the task
            PreparedStatement pt = con.prepareStatement("DELETE FROM tasks WHERE idtasks = ? ");
            pt.setString(1, idtasks);


            // Execute update
            int rc = pt.executeUpdate();

            if (rc > 0) {
                // Redirect to displaytaskservlet to refresh the task list
                resp.sendRedirect("displaytaskservlet?status=deletesuccess");
            } else {
                resp.sendRedirect("displaytaskservlet?status=deletefail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("displaytaskservlet?status=error");
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
