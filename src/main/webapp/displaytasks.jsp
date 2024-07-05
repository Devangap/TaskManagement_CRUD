<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Task.Task, java.util.List" %>

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
    <title>Your Tasks</title>
    <style>
    .task-form label {
        display: block; /* Ensures labels are on their own line */
        font-weight: bold; /* Makes labels bold */
        margin-left: 20px !important; /* Adds a left margin of 20px */
    }
       body {
                   font-family: 'Arial', sans-serif;
                   background: #fafafa;
                   height: 100vh;
                   margin: 0;
                   display: flex;
                   justify-content: center;
                   align-items: center;
                   flex-direction: column;
               }

               .container {
                   background-color: rgba(255, 255, 255, 0.9);
                   padding: 30px;
                   border-radius: 12px;
                   box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
                   max-width: 800px;
                   width: 100%;
                   text-align: center;
                   margin-top: 70px; /* Added to account for the fixed navbar */
                   display: flex;
                   flex-direction: column;
                   align-items: center;
               }

        .task-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
        .task-form {
                    display: none;
                    position: fixed;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
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
                .container.blur {
                    filter: blur(4px);
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
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <div class="navbar">
        <a class="navbar-brand" href="#">Taskpro</a>
        <div class="navbar-right">
            <span class="username">Welcome, <%= session.getAttribute("username") %></span>

            <a href="home.jsp" class="logout-button">Add Tasks</a>
            <a class="logout-button" href="logout">Logout</a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
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
                    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                    if (tasks != null && !tasks.isEmpty()) {
                        for (Task task : tasks) {
                %>
                            <tr>
                                <td><%= task.getTaskId() %></td>
                                <td><%= task.getTitle() %></td>
                                <td><%= task.getDate() %></td>
                                <td><%= task.getPriority() %></td>
                                <td><%= task.getDescription() %></td>
                                <td>
                                    <button onclick="showTaskForm('<%= task.getTaskId() %>', '<%= task.getTitle() %>', '<%= task.getDate() %>', '<%= task.getPriority() %>', '<%= task.getDescription() %>')" class="action-button edit-button">Edit</button>
                                    <a href='deletetask?taskId=<%= task.getTaskId() %>' class='action-button delete-button'>Delete</a>
                                </td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr><td colspan='5'>No tasks found.</td></tr>
                <%
                    }
                %>

            </tbody>
        </table>
    </div>

    <!-- Optional: Script to fetch user ID -->
    <script>
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
        function showTaskForm(taskId, title, date, priority, description) {
                    document.getElementById('taskForm').style.display = 'block';
                    document.querySelector('.container').classList.add('blur');
                    document.getElementById('taskIdInput').value = taskId;
                    document.getElementById('taskTitleInput').value = title;
                    document.getElementById('taskDateInput').value = date;
                    document.getElementById('priorityInput').value = priority;
                    document.getElementById('descriptionInput').value = description;
                }

                function hideTaskForm() {
                    document.getElementById('taskForm').style.display = 'none';
                    document.querySelector('.container').classList.remove('blur');
                }
    </script>
    <!-- Edit Task Form -->
    <div id="taskForm" class="task-form">
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
            <button type="button" onclick="hideTaskForm()">Cancel</button>
        </form>
    </div>

</body>
</html>
