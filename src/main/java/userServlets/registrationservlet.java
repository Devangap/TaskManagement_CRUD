package userServlets;

import Model.User;
import Service.userservice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;

@WebServlet("/register")
@MultipartConfig
public class registrationservlet extends HttpServlet {
    private userservice userService;

    @Override
    public void init() {
        userService = new userservice();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");
        Part filePart = req.getPart("profilePicture");
        RequestDispatcher dispatcher = null;

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
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png") && !fileName.endsWith(".JPG")) {
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

        User user = new User(username, email, password, fileName);

        boolean isRegistered = userService.registerUser(user);

        if (isRegistered) {
            dispatcher = req.getRequestDispatcher("Login.jsp");
            req.setAttribute("curstatus", "success");
        } else {
            dispatcher = req.getRequestDispatcher("registration.jsp");
            req.setAttribute("curstatus", "fail");
        }
        dispatcher.forward(req, resp);
    }
}
