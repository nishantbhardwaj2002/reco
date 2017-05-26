package reco.repository;

import reco.model.UserModel;


public interface UserRepository {

    UserModel create(final String username, final String passwordHash);
    UserModel retrieve(final String userId);
    UserModel retrieveUsingUsername(final String username);
}
