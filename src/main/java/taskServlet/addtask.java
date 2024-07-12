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

        // Set attributes based on the result
        if (errorModel.getError() != null) {
            session.setAttribute("curstatus", "fail");
            session.setAttribute("errorMessage", errorModel.getError());
            System.out.println("Error: " + errorModel.getError()); // Print the error in the console
        } else {
            session.setAttribute("curstatus", "success");
            session.setAttribute("successMessage", errorModel.getSuccess());
        }

        // Redirect to home.jsp
        resp.sendRedirect("home.jsp");
    }
}
