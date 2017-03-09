package reco.repository;

import reco.model.UserModel;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public interface UserRepository {

    UserModel create(final String username, final String passwordHash);
    UserModel retrieve(final String userId);
    UserModel retrieveUsingUsername(final String username);
}
