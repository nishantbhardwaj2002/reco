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
public class NewsItemService {

    private final Gson gson = new Gson();

    private final NewsJdbcRepository newsJdbcRepository;

    @Autowired
    public NewsItemService(final NewsJdbcRepository newsJdbcRepository) {
        this.newsJdbcRepository = newsJdbcRepository;
    }


    public String getNewsItem (final String id) {

        final NewsModel newsModel = newsJdbcRepository.retrieveUsingId(id);

        final Map recommendedNewsMap = new HashMap<String, String>();
        recommendedNewsMap.put(newsModel.getId(), newsModel.getBody());

        return gson.toJson(recommendedNewsMap);
    }
}
