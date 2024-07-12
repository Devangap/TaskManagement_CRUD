package Service;

import Model.*;
import Repository.userrepo;

import java.util.Date;

public class userservice {
    private userrepo userRepository;

    public userservice() {
        this.userRepository = new userrepo();
    }

    public User validateUser(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    public ErrorModel login(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            // Check if user is inactive
            if ("inactive".equals(user.getStatus())) {
                return new ErrorModel("Account is inactive due to multiple failed login attempts.", null);
            }

            // Validate password
            User validatedUser = userRepository.validateUser(username, password);

            if (validatedUser != null) {
                // Reset login attempts on successful login
                user.setLoginAttempts(0);
                user.setLastLogin(new Date());
                userRepository.updateUser(user);
                return new ErrorModel(null, "success");
            } else {
                // Password is incorrect, increment login attempts
                int attempts = user.getLoginAttempts() + 1;
                user.setLoginAttempts(attempts);

                // Check if max attempts are reached
                if (attempts >= 3) {
                    user.setStatus("inactive");
                }
                userRepository.updateUser(user);
                return new ErrorModel("Invalid credentials", null);
            }
        } else {
            return new ErrorModel("Invalid credentials", null);
        }
    }


    public boolean registerUser(User user) {
        return userRepository.registerUser(user);
    }

    public String getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}


