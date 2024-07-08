package registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
@MultipartConfig
public class registrationservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");
        Part filePart = req.getPart("profilePicture");
        RequestDispatcher dispatcher = null;
        Connection con = null;

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Passwords do not match!");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
            req.setAttribute("errorMessage", "Only JPG or PNG images are allowed!");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }

        // Save the file to the server
        String filePath = uploadPath + File.separator + fileName;
        try (InputStream inputStream = filePart.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            PreparedStatement pt = con.prepareStatement("INSERT INTO userinfo (username, email, password,picture) VALUES (?, ?, ?,?)");
            pt.setString(1, username);
            pt.setString(2, email);
            pt.setString(3, password);
            pt.setString(4, fileName);

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
