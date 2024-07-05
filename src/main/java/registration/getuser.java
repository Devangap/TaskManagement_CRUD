package registration;

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

@WebServlet("/GetUserIdServlet")
public class getuser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        // Database connection variables
        String url = "jdbc:mysql://localhost:3306/jsp";
        String dbUsername = "root";
        String dbPassword = "020510Dev#T";

        // SQL query to retrieve iduserinfo based on username
        String sql = "SELECT iduserinfo FROM userinfo WHERE username = ?";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepare SQL statement
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, username);

            // Execute query
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                // Retrieve iduserinfo from the result set
                String iduserinfo = rs.getString("iduserinfo");

                // Set iduserinfo in session (if needed)
                HttpSession session = req.getSession();
                session.setAttribute("iduserinfo", iduserinfo);

                // Send response if required
                resp.setContentType("text/plain");
                resp.getWriter().write(iduserinfo); // Send iduserinfo as plain text response
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found"); // Handle case where username not found
            }

            // Close resources
            rs.close();
            pt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error"); // Handle database errors
        }
    }
}

