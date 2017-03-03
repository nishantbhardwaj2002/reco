package repository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public interface User {

    model.User create(final String username, final String passwordHash);
    model.User retrieve(final String id);
    model.User retrieveUsingUsername(final String username);
}
