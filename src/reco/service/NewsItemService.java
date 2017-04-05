package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.NewsRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsItemService {
    
    private final NewsRepository newsRepository;

    @Autowired
    public NewsItemService(final NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    public NewsModel getNewsItem (final String newsId) {

        return newsRepository.retrieve(newsId);
    }
}
