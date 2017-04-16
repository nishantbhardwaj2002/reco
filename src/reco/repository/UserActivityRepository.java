package reco.repository;

import reco.model.UserActivityModel;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
public interface UserActivityRepository {
    UserActivityModel create(final String userId);
    UserActivityModel update(final String userId, final String newsId);
    UserActivityModel retrieve(final String userId);
}
