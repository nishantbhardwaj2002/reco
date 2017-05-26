package reco.repository;

import reco.model.UserActivityModel;


public interface UserActivityRepository {

    UserActivityModel create(final String userId);
    UserActivityModel update(final String userId, final String newsId);
    UserActivityModel retrieve(final String userId);
}
