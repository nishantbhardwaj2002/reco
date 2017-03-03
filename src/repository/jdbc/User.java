package repository.jdbc;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class User implements repository.User{

    @Override
    public model.User create(final String username, final String password) {

        if(username.equals("nishant")) {
            return null;
        } else {
            final model.User user = new model.User();
            user.setId("2");
            user.setUsername(username);
            return user;
        }
    }

    @Override
    public model.User retrieve(final String username) {

        model.User user = new model.User();
        user.setId("1");
        user.setUsername("nishant");

        return user;
    }

    @Override
    public String getPasswordHash(final String username) {

        if (username.equals("nishant")) {
            return "chiku";
        }
        else {
            return null;
        }
    }
}
