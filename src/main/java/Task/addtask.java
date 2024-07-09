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

@WebServlet("/addtask")
public class addtask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        // Retrieve task details from the form
        String title = req.getParameter("taskTitle");
        String date = req.getParameter("taskDate");
        String priority = req.getParameter("priority");
        String description = req.getParameter("description");

        // Get the userid from the session
        HttpSession session = req.getSession(false);
        String userid = (String) session.getAttribute("userid");

        // Initialize dispatcher for forwarding
        RequestDispatcher dispatcher = null;
        Connection con = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp", "root", "020510Dev#T");

            // Prepare SQL statement
            PreparedStatement pt = con.prepareStatement("INSERT INTO tasks (title, date, priority, description, userid, status) VALUES (?, ?, ?, ?, ?, ?)");
            pt.setString(1, title);
            pt.setString(2, date);
            pt.setString(3, priority);
            pt.setString(4, description);
            pt.setString(5, userid);
            pt.setString(6, "Incomplete"); // Default status as "Incomplete"

            // Execute update
            int rc = pt.executeUpdate();
            dispatcher = req.getRequestDispatcher("home.jsp");

            if (rc > 0) {
                req.setAttribute("curstatus", "success");
            } else {
                req.setAttribute("curstatus", "fail");
            }
            dispatcher.forward(req, resp);

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
