package taskServlet;

import Model.Task;
import Service.TaskService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addtask")
public class addtask extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() {
        taskService = new TaskService();
    }

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
        Integer userid = (Integer) session.getAttribute("userid"); // Retrieve as Integer

        if (userid == null) {
            resp.sendRedirect("Login.jsp"); // Redirect to login page if userid is not found in session
            return;
        }

        // Create a Task object
        Task task = new Task(0, title, date, priority, description);
        task.setUsername(userid); // Assign userid to the task

        // Initialize dispatcher for forwarding
        RequestDispatcher dispatcher;
        boolean isTaskAdded = taskService.addTask(task);
        dispatcher = req.getRequestDispatcher("home.jsp");

        if (isTaskAdded) {
            req.setAttribute("curstatus", "success");
        } else {
            req.setAttribute("curstatus", "fail");
        }
        dispatcher.forward(req, resp);
    }
}
