package repository.jdbc;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class User implements repository.User{

    @Override
    public model.User create(final String username, final String passwordHash) {

        if(username.equals("nishant")) {
            return null;
        } else {
            final model.User user = new model.User();
            user.setId("2");
            user.setUsername(username);
            user.setPasswordHash(passwordHash);
            return user;
        }
    }

    @Override
    public model.User retrieve(final String id) {

        if(id.equals("1")) {
            final model.User user = new model.User();
            user.setId("1");
            user.setUsername("nishant");
            user.setPasswordHash("$2a$06$.05ZxTl1amBs0WtYYXlY..veNK2yWiOv6ER13mob./CKWT70yxlMi");

            return user;
        } else {
            return null;
        }
    }

    @Override
    public model.User retrieveUsingUsername(final String username) {

        if(username.equals("nishant")) {
            final model.User user = new model.User();
            user.setId("1");
            user.setUsername("nishant");
            user.setPasswordHash("$2a$06$.05ZxTl1amBs0WtYYXlY..veNK2yWiOv6ER13mob./CKWT70yxlMi");

            return user;
        } else {
            return null;
        }
    }
}
