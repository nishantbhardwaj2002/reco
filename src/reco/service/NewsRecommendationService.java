package reco.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.model.UserActivityModel;
import reco.model.UserModel;
import reco.repository.NewsRepository;
import reco.repository.UserActivityRepository;
import reco.utils.GradientDescent;
import reco.utils.LatentDirichletAllocation;

import java.util.*;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsRecommendationService {

    private static final Gson gson = new Gson();

    // Number of news (latest clicked) to consider for gradient calculation.
    private final int numberOfNewsToConsiderMax = 3;
    private final int numberOfNewsToLoadMax = 10;

    private final NewsRepository newsRepository;
    private final UserActivityRepository userActivityRepository;
    private final GradientDescent gradientDescent;

    @Autowired
    public NewsRecommendationService(final NewsRepository newsRepository,
                                     final UserActivityRepository userActivityRepository,
                                     final GradientDescent gradientDescent) {

        this.newsRepository = newsRepository;
        this.userActivityRepository = userActivityRepository;
        this.gradientDescent = gradientDescent;
    }

    public String getRecommendedNewsHeads(final String context, final UserModel userModel) {

        final Set<String> alreadyLoadedNews = new HashSet(Arrays.asList(context.split(",")));

        final UserActivityModel userActivityModel = userActivityRepository.retrieve(userModel.getUserId());

        // No recommendation.
        if(userActivityModel == null) {

            final List<NewsModel> newsModelList = newsRepository.retrieveAll();

            int numberOfNewsLoaded = 0;
            final JsonObject jsonObj = new JsonObject();
            for(final NewsModel newsModel : newsModelList) {

                if(numberOfNewsLoaded >= numberOfNewsToLoadMax || alreadyLoadedNews.contains(newsModel.getNewsId())) {
                    continue;
                }
                numberOfNewsLoaded++;

                final JsonObject newsItemJson = new JsonObject();
                newsItemJson.addProperty("head", newsModel.getNewsHead());
                newsItemJson.addProperty("thumbnailUrl", newsModel.getThumbnailUrl());
                newsItemJson.addProperty("url", newsModel.getUrl());


                jsonObj.addProperty(newsModel.getNewsId(), gson.toJson(newsItemJson));
            }

            if(numberOfNewsLoaded ==  0) {
                jsonObj.addProperty("-1", "ERROR : No more news to load.");
            }

            return gson.toJson(jsonObj);
        }



        // Calculate theta vector
        final String newsClickedConcatenated = userActivityModel.getNewsClicked();
        final String[] newsClicked = newsClickedConcatenated.split(",");

        System.out.println("newsClicked : " + Arrays.toString(newsClicked));

        final int numberOfNewsToConsiderActual = (newsClicked.length < numberOfNewsToConsiderMax) ? newsClicked.length : numberOfNewsToConsiderMax;

        final NewsModel[] clickedNewsModels = new NewsModel[numberOfNewsToConsiderActual];
        final double[][] clickedNewsFeatureVectors = new double[numberOfNewsToConsiderActual][LatentDirichletAllocation.numberOfTopics];

        for(int i = 0; i < numberOfNewsToConsiderActual; i++) {
            clickedNewsModels[i] = newsRepository.retrieve(newsClicked[newsClicked.length - numberOfNewsToConsiderActual + i]);
            clickedNewsFeatureVectors[i] = clickedNewsModels[i].getNewsFeatureVector();
        }

        final double[] y = new double[numberOfNewsToConsiderActual];
        Arrays.fill(y, 1);

        final double[] theta = gradientDescent.find(clickedNewsFeatureVectors, y);

        System.out.println("Theta : " + Arrays.toString(theta));



        // Calculate scores
        final List<NewsModel> newsModelList = newsRepository.retrieveAll();

        // Multimap from google guava. Doesn't remove any of the items with same keys.
        final Multimap<Double, NewsModel> recommendedNewsSortedMultimap =  MultimapBuilder.treeKeys().arrayListValues().build();

        for(final NewsModel newsModel : newsModelList) {

            if(alreadyLoadedNews.contains(newsModel.getNewsId())) {
                continue;
            }

            final ArrayRealVector thetaVector = new ArrayRealVector(theta);
            final ArrayRealVector featureVector = new ArrayRealVector(newsModel.getNewsFeatureVector());
            final double score = thetaVector.dotProduct(featureVector);

            newsModel.setNewsHead(newsModel.getNewsHead() + " (Score: " + score + ")");
            recommendedNewsSortedMultimap.put(score, newsModel);
        }



        // Create json string to return.
        final JsonObject jsonObject = new JsonObject();

        final ArrayList<NewsModel> values = new ArrayList<>(recommendedNewsSortedMultimap.values());
        Collections.reverse(values);

        int numberOfNewsLoaded = 0;
        for(final NewsModel newsModel : values) {

            if(numberOfNewsLoaded >= numberOfNewsToLoadMax) {
                break;
            }
            numberOfNewsLoaded++;

            final JsonObject newsItemJson = new JsonObject();
            newsItemJson.addProperty("head", newsModel.getNewsHead());
            newsItemJson.addProperty("thumbnailUrl", newsModel.getThumbnailUrl());
            newsItemJson.addProperty("url", newsModel.getUrl());

            jsonObject.addProperty(newsModel.getNewsId(), gson.toJson(newsItemJson));
        }

        if(numberOfNewsLoaded ==  0) {
            jsonObject.addProperty("-1", "ERROR : No more news to load.");
        }

        return gson.toJson(jsonObject);
    }
}
