<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Task.Task, java.util.List" %>
    <%@ page import="java.sql.*, java.util.*" %>
    <%@ page import="Task.Task" %>

    <%
        // Check if the user is logged in
        if (session.getAttribute("username") == null) {
            response.sendRedirect("Login.jsp"); // Redirect to login page if not logged in
            return;
        }

        String userid = (String) session.getAttribute("userid");

        // Database connection parameters
        String dbURL = "jdbc:mysql://localhost:3306/jsp";
        String dbUser = "root";
        String dbPassword = "020510Dev#T";

        List<Task> tasks = new ArrayList<>();

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String sql = "SELECT * FROM tasks WHERE userid = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, userid);

            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                int idtasks = rs.getInt("idtasks");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String priority = rs.getString("priority");
                String description = rs.getString("description");

                Task task = new Task(idtasks, title, date, priority, description);
                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>


    <%
        // Check if the user is not logged in, redirect to login page if not
        if (session.getAttribute("username") == null) {
            response.sendRedirect("Login.jsp"); // Adjust this to your actual login page
        }
    %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to Taskpro</title>
     <style>
            body {
                font-family: 'Arial', sans-serif;
                background: #fafafa;
                height: 100vh;
                margin: 0; /* Reset margin to 0 */
                display: flex; /* Use Flexbox for centering */
                flex-direction: column; /* Align items vertically */
                align-items: center; /* Center items horizontally */
            }

            .navbar {
                background-color: #333;
                overflow: hidden;
                width: 100%;
                position: fixed;
                top: 0;
                left: 0;
                z-index: 1000;
            }

            .navbar .navbar-brand {
                float: left;
                display: block;
                color: #fff;
                text-align: center;
                padding: 14px 20px;
                text-decoration: none;
                font-size: 24px;
            }

            .navbar .navbar-right {
                float: right;
                padding: 15px;
            }

            .navbar .navbar-right .logout-button {
                background-color: #f06;
                text-decoration: none;
                padding: 10px 15px;
                border-radius: 6px;
                font-size: 16px;
                color: white;
                margin-left: 10px;
            }

            .navbar .navbar-right .logout-button:hover {
                background-color: #d40;
            }

            .navbar .navbar-right .username {
                color: #fff;
                margin-right: 20px;
                font-size: 16px;
            }

            .container {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
                max-width: 500px;
                width: 100%;
                text-align: center;
                animation: fadeIn 1s ease-in-out;
                transition: filter 0.3s ease-in-out; /* Added for background blur effect */
                margin-top: 100px; /* Space below navbar */
                margin-bottom: 20px; /* Space above task table */
            }
            .container2 {
                            background-color: rgba(255, 255, 255, 0.9);
                            padding: 30px;
                            border-radius: 12px;
                            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
                            max-width: 1000px;
                            width: 100%;
                            text-align: center;
                            animation: fadeIn 1s ease-in-out;
                            transition: filter 0.3s ease-in-out; /* Added for background blur effect */
                            margin-top: 80px; /* Space below navbar */
                            margin-bottom: 20px; /* Space above task table */
                        }

            h1 {
                margin-bottom: 20px;
                font-size: 36px;
                color: #333;
            }

            p {
                font-size: 18px;
                color: #666;
                margin-bottom: 40px;
            }

            .taskpro-button {
                padding: 12px 20px;
                background-color: #ffba08;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 18px;
                transition: background-color 0.3s;
            }

            .taskpro-button:hover {
                background-color: #f06;
            }

            .logout-button {
                padding: 10px 15px;
                background-color: #f06;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                text-decoration: none;
            }

            .logout-button:hover {
                background-color: #d40;
            }

            .task-form {
                display: none; /* Hidden by default */
                position: fixed;
                top: 10%; /* Adjust top position */
                left: 50%;
                transform: translateX(-50%);
                background: rgba(255, 255, 255, 0.9);
                padding: 20px;
                border-radius: 8px;
                z-index: 1000;
                max-width: 400px;
                width: 100%;
                text-align: center;
                animation: fadeIn 0.5s ease-in-out;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .task-form input,
            .task-form textarea {
                width: calc(100% - 20px);
                margin: 10px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }

            .task-form button {
                margin-top: 10px;
                padding: 12px 20px;
                background-color: #ffba08;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 18px;
                transition: background-color 0.3s;
            }

            .task-form button:hover {
                background-color: #f06;
            }

            .task-table {
                width: 100%;
                max-width: 1000px;
                border-collapse: collapse;
                font-size: 16px;
            }

            .task-table th, .task-table td {
                padding: 12px;
                border: 1px solid #ddd;
            }

            .task-table th {
                background-color: #f4f4f4;
            }

            .task-table tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .action-button {
                padding: 5px 10px;
                margin: 2px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
            }

            .edit-button {
                background-color: #4CAF50;
                color: white;
            }

            .edit-button:hover {
                background-color: #45a049;
            }

            .delete-button {
                background-color: #f44336;
                color: white;
            }

            .delete-button:hover {
                background-color: #e31b0c;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .task-table-container {
                width: 100%;
                display: flex;
                justify-content: center; /* Center horizontally */
                align-items: center; /* Center vertically */
            }

            .task-table-wrapper {
                width: 80%;
                overflow-x: auto;
            }
        </style>
    <script>
     function showeditTaskForm(taskId, title, date, priority, description) {
                        document.getElementById('edittaskForm').style.display = 'block';
                        document.querySelector('.container').classList.add('blur');
                        document.getElementById('taskIdInput').value = taskId;
                        document.getElementById('taskTitleInput').value = title;
                        document.getElementById('taskDateInput').value = date;
                        document.getElementById('priorityInput').value = priority;
                        document.getElementById('descriptionInput').value = description;
                    }

                    function hideeditTaskForm() {
                        document.getElementById('edittaskForm').style.display = 'none';
                        document.querySelector('.container').classList.remove('blur');
                    }

        // Function to show the task form and blur background
        function showTaskForm() {
            document.getElementById('taskForm').style.display = 'block';
            document.querySelector('.container').classList.add('blur');
        }

        // Function to hide the task form and remove blur
        function hideTaskForm() {
            document.getElementById('taskForm').style.display = 'none';
            document.querySelector('.container').classList.remove('blur');
        }

        // Function to get useriduserinfo by username using AJAX
        function getUserIdByUsername(username) {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'GetUserIdServlet', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        var iduserinfo = xhr.responseText;
                        document.getElementById('useridSpan').textContent = 'ID: ' + iduserinfo;
                    } else {
                        console.error('Error:', xhr.status, xhr.statusText);
                    }
                }
            };
            xhr.send('username=' + encodeURIComponent(username));
        }

        // Example usage to fetch useriduserinfo by username
        getUserIdByUsername('<%= session.getAttribute("username") %>');
         function loadTasks() {
                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', 'displaytaskservlet', true); // Use the correct servlet URL
                    xhr.setRequestHeader('Accept', 'application/json');
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                            var tasks = JSON.parse(xhr.responseText);
                            var taskTableBody = document.querySelector('.task-table tbody');
                            taskTableBody.innerHTML = ''; // Clear existing rows

                            tasks.forEach(function(task) {
                                var row = '<tr>' +
                                    '<td>' + task.title + '</td>' +
                                    '<td>' + task.date + '</td>' +
                                    '<td>' + task.priority + '</td>' +
                                    '<td>' + task.description + '</td>' +
                                    '</tr>';
                                taskTableBody.innerHTML += row;
                            });
                        }
                    };
                    xhr.send();
                }

                // Load tasks when the page is loaded
                document.addEventListener('DOMContentLoaded', function() {
                    loadTasks();
                });
    </script>
</head>
<body>
    <!-- Navigation Bar -->
   <div class="navbar">
       <a class="navbar-brand" href="#">Taskpro</a>
       <div class="navbar-right">
           <span class="username">Welcome, <%= session.getAttribute("username") %></span>


           <a class="logout-button" href="logout">Logout</a>
       </div>
   </div>

    <!-- Main Content -->
    <div class="container">
        <h1>Welcome to Taskpro</h1>
        <p>Your ultimate task management solution.</p>
        <button class="taskpro-button" onclick="showTaskForm()">Add a Task</button>
    </div>
    <div class="container2">
    <h2>Your Tasks</h2>
    <table class="task-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Date</th>
                        <th>Priority</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Task task : tasks) {
                    %>
                    <tr>
                        <td><%= task.getTaskId() %></td>
                        <td><%= task.getTitle() %></td>
                        <td><%= task.getDate() %></td>
                        <td><%= task.getPriority() %></td>
                        <td><%= task.getDescription() %></td>
                        <td>
                             <button onclick="showeditTaskForm('<%= task.getTaskId() %>', '<%= task.getTitle() %>', '<%= task.getDate() %>', '<%= task.getPriority() %>', '<%= task.getDescription() %>')" class="action-button edit-button">Edit</button>
                             <a href='deletetask?taskId=<%= task.getTaskId() %>' class='action-button delete-button'>Delete</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
             </div>





    <!-- Task Form -->
    <!-- Task Form -->
    <div id="taskForm" class="task-form">
        <h2>Add a Task</h2>
        <form action="addtask" method="post">
            <label for="taskTitle" style="display: block; margin-bottom: 5px; margin-left: 20px;">Task Title</label>
            <input type="text" id="taskTitle" name="taskTitle" placeholder="Task Title" required><br>

            <label for="taskDate" style="display: block; margin-bottom: 5px; margin-left: 20px;">Task Date</label>
            <input type="date" id="taskDate" name="taskDate" placeholder="Task Date" required><br>

            <label for="priority" style="display: block; margin-bottom: 5px; margin-left: 20px;">Priority</label>
            <input type="text" id="priority" name="priority" placeholder="Priority" required><br>

            <label for="description" style="display: block; margin-bottom: 5px; margin-left: 20px;">Description</label>
            <textarea id="description" name="description" rows="4" placeholder="Description"></textarea><br>

            <button type="submit" class="taskpro-button">Submit</button>
            <button type="button" onclick="hideTaskForm()" class="taskpro-button">Cancel</button>
        </form>
    </div>
    <div id="edittaskForm" class="task-form">
            <h2>Edit Task</h2>
            <form action="updatetask" method="post">
                <input type="hidden" id="taskIdInput" name="taskId">

                <label for="taskTitleInput">Task Title:</label><br>
                <input type="text" id="taskTitleInput" name="taskTitle" placeholder="Task Title" required><br>

                <label for="taskDateInput">Task Date:</label><br>
                <input type="date" id="taskDateInput" name="taskDate" placeholder="Task Date" required><br>

                <label for="priorityInput">Priority:</label><br>
                <input type="text" id="priorityInput" name="priority" placeholder="Priority" required><br>

                <label for="descriptionInput">Description:</label><br>
                <textarea id="descriptionInput" name="description" rows="4" placeholder="Description"></textarea><br>

                <button type="submit">Save</button>
                <button type="button" onclick="hideeditTaskForm()">Cancel</button>
            </form>
        </div>





</body>
</html>
