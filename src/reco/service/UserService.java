package reco.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.UserModel;
import reco.repository.dynamoDb.UserDynamoDbRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class UserService {

    private final UserDynamoDbRepository userDynamoDbRepository;

    @Autowired
    public UserService(final UserDynamoDbRepository userDynamoDbRepository) {
        this.userDynamoDbRepository = userDynamoDbRepository;
    }

    public UserModel signup(final String username, final String password) {

        final UserModel existingUserModel = userDynamoDbRepository.retrieveUsingUsername(username);
        if(existingUserModel != null) {
            return null;
        } else {
            return userDynamoDbRepository.create(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        }
    }

    public UserModel signin(final String username, final String password) {

        final UserModel userModel = userDynamoDbRepository.retrieveUsingUsername(username);
        if(userModel != null && BCrypt.checkpw(password, userModel.getPassword())) {
            return userModel;
        } else {
            return null;
        }
    }
}
