import java.io.IOException;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.User;
import Model.ErrorModel;
import Service.userservice;

@WebServlet("/LoginServlet")
public class loginservlet extends HttpServlet {
    private userservice userService;

    @Override
    public void init() {
        userService = new userservice();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = null;

        ErrorModel errorModel = userService.login(username, password);

        if (errorModel.getSuccess() != null) {
            // User authenticated successfully
            User user = userService.validateUser(username, password); // Fetch user details

            // Update last login time
            user.setLastLogin(new Date()); // Set current timestamp

            // Update user in the database
            userService.updateUser(user); // Implement updateUser method in userService

            // Set session attributes
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userid", user.getId());
            session.setAttribute("profilePicture", user.getPicture());
            session.setAttribute("status", "success");

            // Pass user details to home.jsp
            req.setAttribute("user", user); // This makes 'user' available in home.jsp
            dispatcher = req.getRequestDispatcher("home.jsp");
        } else {
            // Authentication failed
            session.setAttribute("status", "fail");
            req.setAttribute("errorModel", errorModel); // Pass errorModel to Login.jsp
            dispatcher = req.getRequestDispatcher("Login.jsp");
        }

        // Forward the request and response to the appropriate page
        dispatcher.forward(req, resp);
    }
}
