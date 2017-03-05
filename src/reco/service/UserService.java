package reco.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.UserModel;
import reco.repository.jdbc.UserJdbcRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class UserService {

    private final UserJdbcRepository userJdbcRepository;

    @Autowired
    public UserService(final UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public UserModel signup(final String username, final String password) {

        final UserModel existingUserModel = userJdbcRepository.retrieveUsingUsername(username);
        if(existingUserModel != null) {
            return null;
        } else {
            return userJdbcRepository.create(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        }
    }

    public UserModel signin(final String username, final String password) {

        final UserModel userModel = userJdbcRepository.retrieveUsingUsername(username);
        if(userModel != null && BCrypt.checkpw(password, userModel.getPassword())) {
            return userModel;
        } else {
            return null;
        }
    }
}
