<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: #fafafa;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
            max-width: 380px;
            width: 100%;
            text-align: center;
            animation: fadeIn 1s ease-in-out;
        }

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        label {
            display: block;
            text-align: left;
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 14px;
            color: #555;
        }

        input[type="text"], input[type="email"], input[type="password"], input[type="file"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            transition: border-color 0.3s;
            font-size: 14px;
        }

        input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus, input[type="file"]:focus {
            border-color: #f06;
            outline: none;
            box-shadow: 0 0 8px rgba(240, 6, 6, 0.3);
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #ffba08;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #f06;
        }

        .login-link {
            margin-top: 20px;
            font-size: 14px;
            color: #333;
        }

        .error {
            color: red;
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 10px;
        }

        .login-link a {
            color: #f06;
            text-decoration: none;
            font-weight: bold;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        function validateForm() {
            var fullname = document.getElementById("fullname").value.trim();
            var email = document.getElementById("email").value.trim();
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmpassword").value;
            var profilePicture = document.getElementById("profilePicture").value;

            var isValid = true;

            // Validate Full Name
            if (fullname === "") {
                showError("fullname", "Username is required.");
                isValid = false;
            } else {
                clearError("fullname");
            }

            // Validate Email
            var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (email === "") {
                showError("email", "Email is required.");
                isValid = false;
            } else if (!emailPattern.test(email)) {
                showError("email", "Invalid email address.");
                isValid = false;
            } else {
                clearError("email");
            }

            // Validate Password
            if (password === "") {
                showError("password", "Password is required.");
                isValid = false;
            } else {
                clearError("password");
            }

            // Validate Confirm Password
             if (password !== confirmPassword) {
                showError("confirmpassword", "Passwords do not match.");
                isValid = false;
            } else {
                clearError("confirmpassword");
            }

            // Validate Profile Picture
            if (profilePicture === "") {
                showError("profilePicture", "Profile picture is required.");
                isValid = false;
            } else {
                clearError("profilePicture");
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
            var elements = ["fullname", "email", "password", "confirmpassword", "profilePicture"];
            elements.forEach(function(id) {
                document.getElementById(id).addEventListener("focus", function() {
                    clearError(id);
                });
            });
        }

        window.onload = function() {
            clearErrorOnFocus();
        };
    </script>
</head>
<body>
   <div class="container">
       <h2>Register</h2>
       <form action="register" method="post" enctype="multipart/form-data" onsubmit="return validateForm();">
           <label for="fullname">Username</label>
           <input type="text" id="fullname" name="fullname">
           <div id="fullname-error" class="error"></div>

           <label for="email">Email</label>
           <input type="text" id="email" name="email">
           <div id="email-error" class="error"></div>

           <label for="password">Password</label>
           <input type="password" id="password" name="password">
           <div id="password-error" class="error"></div>

           <label for="confirmpassword">Confirm Password</label>
           <input type="password" id="confirmpassword" name="confirmpassword">
           <div id="confirmpassword-error" class="error"></div>

           <label for="profilePicture">Upload Profile Picture</label>
           <input type="file" id="profilePicture" name="profilePicture">
           <div id="profilePicture-error" class="error"></div>

           <input type="submit" value="Register">
       </form>
       <div class="login-link">
           Already registered? <a href="Login.jsp">Login</a>
       </div>
   </div>
   <script>
       <% if (request.getAttribute("status") != null) { %>
           var status = "<%= request.getAttribute("status") %>";
           if (status === "success") {
               Swal.fire({
                   title: 'Success!',
                   text: 'Registration completed successfully.',
                   icon: 'success',
                   confirmButtonText: 'OK'
               });
           } else if (status === "fail") {
               Swal.fire({
                   title: 'Error!',
                   text: 'Registration failed. Please try again.',
                   icon: 'error',
                   confirmButtonText: 'OK'
               });
           }
       <% } %>
   </script>
   <%
       String errorMessage = (String) request.getAttribute("errorMessage");
       if (errorMessage != null) {
   %>
       <script type="text/javascript">
           Swal.fire({
               title: 'Error!',
               text: '<%= errorMessage %>',
               icon: 'error',
               confirmButtonText: 'OK'
           });
       </script>
   <%
       }
   %>
</body>
</html>
