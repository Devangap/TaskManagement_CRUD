package Service;


import Model.Task;
import Repository.taskrepo;

public class TaskService {
    private taskrepo taskRepository;

    public TaskService() {
        this.taskRepository = new taskrepo();
    }

    public boolean addTask(Task task) {
        return taskRepository.saveTask(task);
    }

    public boolean deleteTask(int taskId) {
        return taskRepository.deleteTask(taskId);
    }

    public boolean updateTask(int taskId, String title, String date, String priority, String description) {
        return taskRepository.updateTask(taskId, title, date, priority, description);
    }
}

