package taskServlet;

import Service.TaskService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updatetask")
public class updatetask extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() {
        taskService = new TaskService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve task parameters from request
        String taskIdStr = request.getParameter("taskId");
        int taskId = Integer.parseInt(taskIdStr);
        String taskTitle = request.getParameter("taskTitle");
        String taskDate = request.getParameter("taskDate");
        String priority = request.getParameter("priority");
        String description = request.getParameter("description");

        // Update the task using the service
        boolean isTaskUpdated = taskService.updateTask(taskId, taskTitle, taskDate, priority, description);

        if (isTaskUpdated) {
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Task update failed!");
        }

        // Redirect to the home.jsp page
        response.sendRedirect("home.jsp");
    }
}
