package registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class registrationservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");
        RequestDispatcher dispatcher = null;
        Connection con = null;

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Passwords do not match!");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }




        PrintWriter out = resp.getWriter();
        out.print(username);
        out.print(email);
        out.print(password);

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp", "root", "020510Dev#T");

            // Prepare SQL statement
            PreparedStatement pt = con.prepareStatement("INSERT INTO userinfo (username, email, password) VALUES (?, ?, ?)");
            pt.setString(1, username);
            pt.setString(2, email);
            pt.setString(3, password);

            // Execute update
            int rc = pt.executeUpdate();
            dispatcher = req.getRequestDispatcher("Login.jsp");

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
