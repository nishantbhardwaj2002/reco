package reco.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.repository.jdbc.NewsJdbcRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsRecommendationService {

    private final Gson gson = new Gson();

    private final NewsJdbcRepository newsJdbcRepository;

    @Autowired
    public NewsRecommendationService(final NewsJdbcRepository newsJdbcRepository) {
        this.newsJdbcRepository = newsJdbcRepository;
    }

    public String getRecommendedNewsHeads(final String context) {

        if(context.equals("4")) {
            return null;
        }
        final NewsModel newsModel = newsJdbcRepository.retrieve("1");

        final Map recommendedNewsMap = new HashMap<String, String>();
        recommendedNewsMap.put(newsModel.getId(), newsModel.getHead());
        recommendedNewsMap.put("2", "head2");
        recommendedNewsMap.put("3", "head3");
        recommendedNewsMap.put("4", "head4");

        return gson.toJson(recommendedNewsMap);
    }
}
