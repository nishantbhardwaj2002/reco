package reco.repository.jdbc;

import org.springframework.stereotype.Repository;
import reco.model.UserModel;
import reco.repository.UserRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Repository
public class UserJdbcRepository implements UserRepository {

    @Override
    public UserModel create(final String username, final String passwordHash) {

        if(username.equals("nishant")) {
            return null;
        } else {
            final UserModel userModel = new UserModel();
            userModel.setId("2");
            userModel.setUsername(username);
            userModel.setPassword(passwordHash);
            return userModel;
        }
    }

    @Override
    public UserModel retrieve(final String id) {

        if(id.equals("1")) {
            final UserModel userModel = new UserModel();
            userModel.setId("1");
            userModel.setUsername("nishant");
            userModel.setPassword("$2a$06$.05ZxTl1amBs0WtYYXlY..veNK2yWiOv6ER13mob./CKWT70yxlMi");

            return userModel;
        } else {
            return null;
        }
    }

    @Override
    public UserModel retrieveUsingUsername(final String username) {

        if(username.equals("nishant")) {
            final UserModel userModel = new UserModel();
            userModel.setId("1");
            userModel.setUsername("nishant");
            userModel.setPassword("$2a$06$.05ZxTl1amBs0WtYYXlY..veNK2yWiOv6ER13mob./CKWT70yxlMi");

            return userModel;
        } else {
            return null;
        }
    }
}
