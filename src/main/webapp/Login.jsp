<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
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

        input[type="text"], input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            transition: border-color 0.3s;
            font-size: 14px;
        }

        input[type="text"]:focus, input[type="password"]:focus {
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
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="LoginServlet" method="post" onsubmit= "return validateForm();">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" >

            <label for="password">Password</label>
            <input type="password" id="password" name="password">

            <input type="submit" value="Login">
        </form>
         <div class="login-link">
        <p>Don't have an account? <a href="registration.jsp">Sign up</a></p>
        </div>
    </div>
    <script>
    function validateForm() {
                var fullname = document.getElementById("username").value.trim();
                var password = document.getElementById("password").value;

                var isValid = true;

                // Validate Full Name
                if (fullname === "") {
                    showError("username", "Username is required.");
                    isValid = false;
                } else {
                    clearError("username");
                }

                // Validate Password
                if (password === "") {
                    showError("password", "Password is required.");
                    isValid = false;
                } else {
                    clearError("password");
                }
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
                var elements = ["username", "password"];
                elements.forEach(function(id) {
                    document.getElementById(id).addEventListener("focus", function() {
                        clearError(id);
                    });
                });
            }

            window.onload = function() {
                clearErrorOnFocus();
            };

        document.addEventListener('DOMContentLoaded', function() {
            <% if (session.getAttribute("status") != null) { %>
                var status = "<%= session.getAttribute("status") %>";
                if (status === "success") {
                    Swal.fire({
                        title: 'Success!',
                        text: 'Login successful.',
                        icon: 'success',
                        confirmButtonText: 'OK',
                        position: 'center',
                        showConfirmButton: true,
                        backdrop: false
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = 'home.jsp';
                        }
                    });
                } else if (status === "fail") {
                    Swal.fire({
                        title: 'Error!',
                        text: 'Login failed. Please check your credentials and try again.',
                        icon: 'error',
                        confirmButtonText: 'OK',
                        position: 'center',
                        showConfirmButton: true,
                        backdrop: false
                    });
                }
                <% session.removeAttribute("status"); %>
            <% } %>
        });
    </script>
</body>
</html>
