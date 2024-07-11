package taskServlet;

import Model.Task;
import Model.ErrorModel;
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
        Integer userid = (Integer) session.getAttribute("userid");

        // Convert userid to String
        String useridStr = String.valueOf(userid);

        // Create a Task object
        Task task = new Task(0, title, date, priority, description);
        task.setUsername(useridStr); // Set the userid as String

        // Check if task title already exists
        ErrorModel errorModel = taskService.addTask(task);

        // Initialize dispatcher for forwarding
        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("home.jsp");

        // Set attributes based on the result
        if (errorModel.getError() != null) {
            req.setAttribute("curstatus", "fail");
            req.setAttribute("errorMessage", errorModel.getError());
        } else {
            req.setAttribute("curstatus", "success");
            req.setAttribute("successMessage", errorModel.getSuccess());
        }

        dispatcher.forward(req, resp);
    }
}
