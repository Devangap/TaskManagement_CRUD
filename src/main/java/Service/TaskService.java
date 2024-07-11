package Service;


import Model.*;
import Repository.taskrepo;

import java.util.List;

public class TaskService {
    private taskrepo taskRepository;

    public TaskService() {
        this.taskRepository = new taskrepo();
    }

    public ErrorModel addTask(Task task) {
        boolean isTitleExists = taskRepository.checkTaskTitleExists(task.getTitle());

        if (isTitleExists) {
            return new ErrorModel("Task title already exists.", null);
        } else {
            boolean isTaskAdded = taskRepository.saveTask(task);
            if (isTaskAdded) {
                return new ErrorModel(null, "Task added successfully.");
            } else {
                return new ErrorModel("Failed to add task.", null);
            }
        }
    }

    public boolean deleteTask(int taskId) {
        return taskRepository.deleteTask(taskId);
    }

    public boolean updateTask(int taskId, String title, String date, String priority, String description) {
        return taskRepository.updateTask(taskId, title, date, priority, description);
    }
    public List<Task> getTasks(int userid) throws Exception {
        return taskRepository.getTasksByUserId(userid);
    }
}

