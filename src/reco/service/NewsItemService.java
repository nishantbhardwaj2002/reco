package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.model.UserActivityModel;
import reco.model.UserModel;
import reco.repository.NewsRepository;
import reco.repository.UserActivityRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsItemService {
    
    private final NewsRepository newsRepository;
    private final UserActivityRepository userActivityRepository;

    @Autowired
    public NewsItemService(final NewsRepository newsRepository, final UserActivityRepository userActivityRepository) {
        this.newsRepository = newsRepository;
        this.userActivityRepository = userActivityRepository;
    }

    public NewsModel getNewsItem (final UserModel userModel, final String newsId) {

        final UserActivityModel userActivityModel = userActivityRepository.update(userModel.getUserId(), newsId);
        return newsRepository.retrieve(newsId);
    }
}
