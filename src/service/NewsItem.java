package service;

import com.google.gson.Gson;
import repository.jdbc.News;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsItem {

    final static Gson gson = new Gson();

    public String getNewsItem (final String id) {

        final model.News news = new News().retrieveUsingId(id);

        final Map recommendedNewsMap = new HashMap<String, String>();
        recommendedNewsMap.put(news.getId(), news.getBody());

        return gson.toJson(recommendedNewsMap);
    }
}
