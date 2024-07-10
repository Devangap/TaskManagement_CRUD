package Service;

import Model.User;
import Repository.userrepo;

public class userservice {
    private userrepo userRepository;

    public userservice() {
        this.userRepository = new userrepo();
    }

    public User validateUser(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    public boolean registerUser(User user) {
        return userRepository.registerUser(user);
    }

    public String getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }
}


