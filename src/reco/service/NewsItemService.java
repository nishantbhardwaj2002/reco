package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.dynamoDb.NewsDynamoDbRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsItemService {
    
    private final NewsDynamoDbRepository newsDynamoDbRepository;

    @Autowired
    public NewsItemService(final NewsDynamoDbRepository newsDynamoDbRepository) {
        this.newsDynamoDbRepository = newsDynamoDbRepository;
    }


    public NewsModel getNewsItem (final String newsId) {

        return newsDynamoDbRepository.retrieve(newsId);
    }
}
