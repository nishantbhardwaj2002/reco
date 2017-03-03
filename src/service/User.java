package service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class User {

    public static model.User signin (final String username, final String password) {

        final model.User user = new repository.jdbc.User().retrieveUsingUsername(username);
        if(user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        } else {
            return null;
        }
    }

    public static model.User create (final String username, final String passwordHash) {

        return new repository.jdbc.User().create(username, passwordHash);
    }
}
