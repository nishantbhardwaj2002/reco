package service;

import com.google.gson.Gson;
import repository.jdbc.News;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsRecommendation {

    final static Gson gson = new Gson();

    public String getRecommendedNewsHeads(final String context) {

        if(context.equals("4")) {
            return "";
        }
        final model.News news = new News().retrieve("1");

        final Map recommendedNewsMap = new HashMap<String, String>();
        recommendedNewsMap.put(news.getId(), news.getHead());
        recommendedNewsMap.put("2", "head2");
        recommendedNewsMap.put("3", "head3");
        recommendedNewsMap.put("4", "head4");

        return gson.toJson(recommendedNewsMap);
    }
}
