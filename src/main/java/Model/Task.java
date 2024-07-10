package Model;

public class Task {
    private int idtasks;
    private int username;


    private String title;
    private String date;
    private String priority;
    private String description;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public Task(int idtasks, String title, String date, String priority, String description) {
        this.idtasks = idtasks;
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.description = description;
    }

    // Getters and setters
    public int getTaskId() {
        return idtasks;
    }

    public void setTaskId(int idtasks) {
        this.idtasks = idtasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
