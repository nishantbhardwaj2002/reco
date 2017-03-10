package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.dynamoDb.NewsDynamoDbRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsRecommendationService {

    private final NewsDynamoDbRepository newsDynamoDbRepository;

    @Autowired
    public NewsRecommendationService(final NewsDynamoDbRepository newsDynamoDbRepository) {
        this.newsDynamoDbRepository = newsDynamoDbRepository;
    }

    public Map getRecommendedNewsHeads(final String context) {

        final List<NewsModel> newsModelList = newsDynamoDbRepository.retrieve();
        final Map recommendedNewsMap = new HashMap<String, String>();

        for(final NewsModel newsModel : newsModelList) {
            recommendedNewsMap.put(newsModel.getNewsId(), newsModel.getHead());
        }

        return recommendedNewsMap;
    }
}
