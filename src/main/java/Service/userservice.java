package Service;

import Model.*;
import Repository.userrepo;

public class userservice {
    private userrepo userRepository;

    public userservice() {
        this.userRepository = new userrepo();
    }

    public User validateUser(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    public ErrorModel login(String username, String password) {
        User user = userRepository.validateUser(username, password);
        if (user != null) {
            return new ErrorModel(null, "success");
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
}


