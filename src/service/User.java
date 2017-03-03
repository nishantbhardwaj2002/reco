package service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class User {

    public static model.User signin (final String username, final String password) {

        final String passwordHash = new repository.jdbc.User().getPasswordHash(username);
        if(passwordHash != null && BCrypt.checkpw(password, passwordHash)) {
            return new repository.jdbc.User().retrieve(username);
        } else {
            return null;
        }
    }

    public static model.User create (final String username, final String password) {

        return new repository.jdbc.User().create(username, password);
    }
}
