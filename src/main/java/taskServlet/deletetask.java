package taskServlet;

import Service.TaskService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deletetask")
public class deletetask extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() {
        taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve task ID from the request
        String idtasksStr = req.getParameter("taskId");
        int idtasks = Integer.parseInt(idtasksStr);
        System.out.println("Retrieved Task ID: " + idtasks);

        // Initialize dispatcher for forwarding
        boolean isTaskDeleted = taskService.deleteTask(idtasks);

        if (isTaskDeleted) {
            // Redirect to the home page (home.jsp) after successful deletion
            resp.sendRedirect("home.jsp?status=deletesuccess");
        } else {
            // Redirect to the home page (home.jsp) with delete failure status
            resp.sendRedirect("home.jsp?status=deletefail");
        }
    }
}
