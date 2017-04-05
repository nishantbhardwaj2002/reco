package reco.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.UserModel;
import reco.repository.UserRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel signup(final String username, final String password) {

        final UserModel existingUserModel = userRepository.retrieveUsingUsername(username);
        if(existingUserModel != null) {
            return null;
        } else {
            return userRepository.create(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        }
    }

    public UserModel signin(final String username, final String password) {

        final UserModel userModel = userRepository.retrieveUsingUsername(username);
        if(userModel != null && BCrypt.checkpw(password, userModel.getPassword())) {
            return userModel;
        } else {
            return null;
        }
    }
}
