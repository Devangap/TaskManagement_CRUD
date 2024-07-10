package userServlets;

import Model.User;
import Service.userservice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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

        User user = userService.validateUser(username, password);
        if (user != null) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userid", user.getId());
            session.setAttribute("profilePicture", user.getPicture());
            session.setAttribute("status", "success");
            dispatcher = req.getRequestDispatcher("home.jsp");
        } else {
            session.setAttribute("status", "fail");
            dispatcher = req.getRequestDispatcher("Login.jsp");
        }
        dispatcher.forward(req, resp);
    }
}
