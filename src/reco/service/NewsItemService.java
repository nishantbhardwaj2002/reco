package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.jdbc.NewsJdbcRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsItemService {
    
    private final NewsJdbcRepository newsJdbcRepository;

    @Autowired
    public NewsItemService(final NewsJdbcRepository newsJdbcRepository) {
        this.newsJdbcRepository = newsJdbcRepository;
    }


    public NewsModel getNewsItem (final String newsId) {

        return newsJdbcRepository.retrieve(newsId);
    }
}
