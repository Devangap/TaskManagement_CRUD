package registration;

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
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class loginservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp", "root", "020510Dev#T");

            // Prepare SQL statement
            PreparedStatement pt = con.prepareStatement("SELECT * FROM userinfo WHERE username = ? and password = ?");
            pt.setString(1, username);
            pt.setString(2, password);

            // Execute query
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                // Set username in session
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("userid", rs.getString("iduserinfo"));
                session.setAttribute("status", "success");
                dispatcher = req.getRequestDispatcher("home.jsp");
            } else {
                session.setAttribute("status", "fail");
                dispatcher = req.getRequestDispatcher("Login.jsp");
            }
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
