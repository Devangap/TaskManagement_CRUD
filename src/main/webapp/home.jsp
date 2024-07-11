<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Model.Task, java.util.List" %>
    <%@ page import="java.sql.*, java.util.*" %>





    <%
        // Check if the user is logged in
        if (session.getAttribute("username") == null) {
            response.sendRedirect("Login.jsp"); // Redirect to login page if not logged in
            return;
        }

         Integer userid = (Integer) session.getAttribute("userid");


    %>


    <%
        // Check if the user is not logged in, redirect to login page if not
        if (session.getAttribute("username") == null) {
            response.sendRedirect("Login.jsp"); // Adjust this to your actual login page
        }
    %>
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
 function logMessages() {
            <% if ("success".equals(request.getAttribute("curstatus"))) { %>
                console.log("Success: <%= request.getAttribute("successMessage") %>");
            <% } else if ("fail".equals(request.getAttribute("curstatus"))) { %>
                console.error("Error: <%= request.getAttribute("errorMessage") %>");
            <% } %>
        }

        // Call the logMessages function when the page loads
        window.onload = function() {
            logMessages();
        };

        $(document).ready(function() {
            var userId = <%= userid %>;

             $.ajax({
                   url: 'tasks',
                   type: 'GET',
                   data: { userid: userId },
                   success: function(response) {
                       var taskList = $('#task-list');
                       taskList.empty();

                       // Add table headers
                       var tableHtml = '<table class="task-table">' +
                                           '<thead>' +
                                               '<tr>' +
                                                   '<th>ID</th>' +
                                                   '<th>Title</th>' +
                                                   '<th>Date</th>' +
                                                   '<th>Priority</th>' +
                                                   '<th>Description</th>' +
                                                   '<th>Action</th>' +
                                               '</tr>' +
                                           '</thead>' +
                                           '<tbody>';

                       response.forEach(function(task) {
                           console.log("dataaaaaa", response);
                           tableHtml += '<tr>' +
                                            '<td>' + task.idtasks + '</td>' +
                                            '<td>' + task.title + '</td>' +
                                            '<td>' + task.date + '</td>' +
                                            '<td>' + task.priority + '</td>' +
                                            '<td>' + task.description + '</td>' +
                                            '<td>' +
                                                '<button onclick="showEditTaskForm(\'' + task.idtasks + '\', \'' + task.title + '\', \'' + task.date + '\', \'' + task.priority + '\', \'' + task.description + '\')" class="action-button edit-button">Edit</button>' +
                                                '<a href="javascript:void(0);" onclick="confirmDelete(\'' + task.idtasks + '\');" class="action-button delete-button">Delete</a>' +
                                            '</td>' +
                                        '</tr>';
                       });

                       tableHtml += '</tbody></table>';
                       taskList.append(tableHtml);
                   },
                   error: function(xhr, status, error) {
                       console.error('Error fetching tasks:', error);
                   }
               });
           });

    </script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to Taskpro</title>
     <style>
     #task-search {
             padding: 10px;
             width: 300px; /* Adjust width as needed */
             border: 1px solid #ccc;
             border-radius: 4px;
             margin-bottom: 10px;
             font-size: 16px;
             box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
         }

         /* Style for the placeholder text in the search bar */
         #task-search::placeholder {
             color: #999;
         }

         /* Style for the search bar on focus */
         #task-search:focus {
             outline: none; /* Remove default focus outline */
             border-color: #007bff; /* Adjust color on focus */
             box-shadow: 0 0 8px rgba(0, 123, 255, 0.4); /* Add a subtle shadow */
         }
     .profile-picture {
                 width: 40px;
                 height: 40px;
                 border-radius: 50%;
                 margin-right: 10px;
             }
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
                margin-right:20px;
                margin-bottom:10px !important;
            }

            .navbar .navbar-right .logout-button:hover {
                background-color: #d40;
            }

            .navbar .navbar-right .username {
                color: #fff;
                margin-right: 20px;
                margin-bottom:10px;
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
                background: #ffffff; /* No transparency */
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
            .task-form textarea,
            .task-form select{
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

            .error {
                        color: red;
                        font-size: 12px;
                        margin-top: -10px;
                        margin-bottom: 10px;
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

    function validateForm() {
        const taskTitle = document.getElementById('taskTitle').value;
        const taskDate = document.getElementById('taskDate').value;
        const priority = document.getElementById('priority').value;
        const description = document.getElementById('description').value;

        var isValid = true;
       if (taskTitle === "") {
                showError("taskTitle", "Task title is required.");
                isValid = false;
            } else {
                clearError("taskTitle");
            }


        if (taskDate === "") {
                showError("taskDate", "Task date is required.");
                isValid = false;
            } else {
                clearError("taskDate");
            }

        if (priority === "") {
                showError("priority", "Priority is required.");
                isValid = false;
            } else {
                clearError("priority");
            }


        if (description === "") {
                showError("description", "Description is required.");
                isValid = false;
            } else {
                clearError("description");
            }

            return isValid;


    }
    function showError(elementId, message) {
                var element = document.getElementById(elementId);
                element.style.borderColor = "#f06";
                var errorElement = document.getElementById(elementId + "-error");
                if (!errorElement) {
                    errorElement = document.createElement("div");
                    errorElement.id = elementId + "-error";
                    errorElement.className = "error";
                    element.parentNode.insertBefore(errorElement, element.nextSibling);
                }
                errorElement.innerText = message;
            }

            function clearError(elementId) {
                var element = document.getElementById(elementId);
                element.style.borderColor = "#ddd";
                var errorElement = document.getElementById(elementId + "-error");
                if (errorElement) {
                    errorElement.innerText = "";
                }
            }

            function clearErrorOnFocus() {
                var elements = ["taskTitle","taskDate","priority", "description"];
                elements.forEach(function(id) {
                    document.getElementById(id).addEventListener("focus", function() {
                        clearError(id);
                    });
                });
            }

            window.onload = function() {
                clearErrorOnFocus();
            };
     function showEditTaskForm(taskId, title, date, priority, description) {
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
                clearTaskForm(); // Clear the form fields
            }

            function clearTaskForm() {
                document.getElementById('taskTitle').value = "";
                document.getElementById('taskDate').value = "";
                document.getElementById('priority').value = "";
                document.getElementById('description').value = "";
            }

       function confirmDelete(taskId) {
               var confirmation = confirm("Are you sure that you want to delete?");
               if (confirmation) {
                   window.location.href = 'deletetask?taskId=' + taskId;
               } else {
                   // Do nothing or handle cancellation
               }
           }
          document.addEventListener('DOMContentLoaded', (event) => {
              const today = new Date().toISOString().split('T')[0];
              document.getElementById('taskDate').setAttribute('min', today);
              document.getElementById('taskDateInput').setAttribute('min', today);
          });


          function validateEditForm() {
                  const taskTitle = document.getElementById('taskTitleInput').value;
                  const taskDate = document.getElementById('taskDateInput').value;
                  const priority = document.getElementById('priorityInput').value;
                  const description = document.getElementById('descriptionInput').value;

                  var isValid = true;
                  if (taskTitle === "") {
                      showError("taskTitleInput", "Task title is required.");
                      isValid = false;
                  } else {
                      clearError("taskTitleInput");
                  }

                  if (taskDate === "") {
                      showError("taskDateInput", "Task date is required.");
                      isValid = false;
                  } else {
                      clearError("taskDateInput");
                  }

                  if (priority === "") {
                      showError("priorityInput", "Priority is required.");
                      isValid = false;
                  } else {
                      clearError("priorityInput");
                  }

                  if (description === "") {
                      showError("descriptionInput", "Description is required.");
                      isValid = false;
                  } else {
                      clearError("descriptionInput");
                  }

                  return isValid;
              }

              function showError(elementId, message) {
                  var element = document.getElementById(elementId);
                  element.style.borderColor = "#f06";
                  var errorElement = document.getElementById(elementId + "-error");
                  if (!errorElement) {
                      errorElement = document.createElement("div");
                      errorElement.id = elementId + "-error";
                      errorElement.className = "error";
                      element.parentNode.insertBefore(errorElement, element.nextSibling);
                  }
                  errorElement.innerText = message;
              }

              function clearError(elementId) {
                  var element = document.getElementById(elementId);
                  element.style.borderColor = "#ddd";
                  var errorElement = document.getElementById(elementId + "-error");
                  if (errorElement) {
                      errorElement.innerText = "";
                  }
              }

              function clearErrorOnFocus() {
                  var elements = ["taskTitleInput", "taskDateInput", "priorityInput", "descriptionInput"];
                  elements.forEach(function(id) {
                      document.getElementById(id).addEventListener("focus", function() {
                          clearError(id);
                      });
                  });
              }

              window.onload = function() {
                  clearErrorOnFocus();
              };

              function filterTasks() {
                      // Declare variables
                      var input, filter, table, tr, td, i, txtValue;
                      input = document.getElementById("task-search");
                      filter = input.value.toUpperCase();
                      table = document.getElementsByClassName("task-table")[0]; // Assuming there's only one table
                      tr = table.getElementsByTagName("tr");

                      // Loop through all table rows, and hide those that don't match the search query
                      for (i = 0; i < tr.length; i++) {
                          td = tr[i].getElementsByTagName("td")[1]; // Index 1 for the title column
                          if (td) {
                              txtValue = td.textContent || td.innerText;
                              if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                  tr[i].style.display = "";
                              } else {
                                  tr[i].style.display = "none";
                              }
                          }
                      }
                  }



    </script>
</head>
<body>

   <% if ("success".equals(request.getAttribute("curstatus"))) { %>
           <div class="alert alert-success">
               <strong>Success!</strong> <%= request.getAttribute("successMessage") %>
           </div>
       <% } %>

       <!-- Display error message if task addition failed -->
       <% if ("fail".equals(request.getAttribute("curstatus"))) { %>
           <div class="alert alert-danger">
               <strong>Error!</strong> <%= request.getAttribute("errorMessage") %>
           </div>
       <% } %>

    <!-- Navigation Bar -->
   <div class="navbar">
       <a class="navbar-brand" href="#">Taskpro</a>
       <div class="navbar-right">

           <span class="username">Welcome, <%= session.getAttribute("username") %></span>


           <a class="logout-button" href="logout">Logout</a>
           <img src="uploads/<%= session.getAttribute("profilePicture") %>" alt="Profile Picture" class="profile-picture" onclick="showProfilePicture()">
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
    <input type="text" id="task-search" placeholder="Search tasks by Title" onkeyup="filterTasks()">

    <div id="task-list">

            </div>
             </div>





    <!-- Task Form -->
    <!-- Task Form -->
    <div id="taskForm" class="task-form">
        <h2>Add a Task</h2>
        <form action="addtask" method="post" onsubmit="return validateForm()" >
            <label for="taskTitle" style="display: block; margin-bottom: 5px; margin-left: 20px;">Task Title</label>
            <span style="color: red;">
                    <% if (request.getAttribute("error") != null) { %>
                        <%= request.getAttribute("error") %>
                    <% } %>
                </span>

                <!-- Success message display -->
                <span style="color: green;">
                    <% if (request.getAttribute("success") != null) { %>
                        <%= request.getAttribute("success") %>
                    <% } %>
                </span>

            <input type="text" id="taskTitle" name="taskTitle" placeholder="Task Title" maxlength="30"><br>

            <label for="taskDate" style="display: block; margin-bottom: 5px; margin-left: 20px;">Task Date</label>
            <input type="date" id="taskDate" name="taskDate" placeholder="Task Date"><br>

            <label for="priority" style="display: block; margin-bottom: 5px; margin-left: 20px;">Priority:</label>
            <select id="priority" name="priority" style="margin-left: 20px;" >
                <option value="" disabled selected>Select Priority</option>
                <option value="High">High</option>
                <option value="Medium">Medium</option>
                <option value="Low">Low</option>
                <option value="Routine">Routine</option>
                <option value="No Priority">No Priority</option>
            </select><br>



            <label for="description" style="display: block; margin-bottom: 5px; margin-left: 20px;">Description</label>
            <textarea id="description" name="description" rows="4" placeholder="Description" maxlength="100"></textarea><br>

            <button type="submit" class="taskpro-button">Submit</button>
            <button type="button" onclick="hideTaskForm()" class="taskpro-button">Cancel</button>
        </form>
    </div>
    <div id="edittaskForm" class="task-form">
            <h2>Edit Task</h2>
            <form action="updatetask" method="post" onsubmit="return validateEditForm()">
                <input type="hidden" id="taskIdInput" name="taskId">

                <label for="taskTitleInput">Task Title:</label><br>
                <input type="text" id="taskTitleInput" name="taskTitle" placeholder="Task Title" maxlength="30"><br>

                <label for="taskDateInput">Task Date:</label><br>
                <input type="date" id="taskDateInput" name="taskDate" placeholder="Task Date" ><br>

               <label for="priorityInput">Priority:</label><br>
               <select id="priorityInput" name="priority" required>
                   <option value="" disabled selected>Select Priority</option>
                   <option value="High">High</option>
                   <option value="Medium">Medium</option>
                   <option value="Low">Low</option>
                   <option value="Routine">Routine</option>
                   <option value="No Priority">No Priority</option>
               </select><br>

                <label for="descriptionInput">Description:</label><br>
                <textarea id="descriptionInput" name="description" rows="4" placeholder="Description" maxlength="100"></textarea><br>

                <button type="submit">Save</button>
                <button type="button" onclick="hideeditTaskForm()">Cancel</button>
            </form>
        </div>





</body>
</html>
