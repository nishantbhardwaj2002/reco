package reco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.NewsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsRecommendationService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsRecommendationService(final NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Map getRecommendedNewsHeads(final String context) {

        final List<NewsModel> newsModelList = newsRepository.retrieveAll();
        final Map recommendedNewsMap = new HashMap<String, String>();

        for(final NewsModel newsModel : newsModelList) {
            recommendedNewsMap.put(newsModel.getNewsId(), newsModel.getNewsHead());
        }

        return recommendedNewsMap;
    }
}
