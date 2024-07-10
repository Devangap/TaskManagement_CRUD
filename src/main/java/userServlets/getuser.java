package userServlets;

import Service.userservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/GetUserIdServlet")
public class getuser extends HttpServlet {

    private userservice userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new userservice();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        String iduserinfo = userService.getUserIdByUsername(username);

        if (iduserinfo != null) {
            HttpSession session = req.getSession();
            session.setAttribute("iduserinfo", iduserinfo);
            resp.setContentType("text/plain");
            resp.getWriter().write(iduserinfo); // Send iduserinfo as plain text response
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found"); // Handle case where username not found
        }
    }
}
