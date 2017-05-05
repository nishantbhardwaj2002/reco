package reco.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
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

    // Number of news (latest clicked) to consider for gradient calculation.
    private final int numberOfNewsToConsiderMax = 1000;
    private final int numberOfNewsToLoadMax = 1000;

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

    public JsonObject getRecommendedNewsHeads(final String context, final UserModel userModel) {

        final Set<String> alreadyLoadedNews = new HashSet(Arrays.asList(context.split(",")));

        final UserActivityModel userActivityModel = userActivityRepository.retrieve(userModel.getUserId());

        if(userActivityModel == null) {

            final List<NewsModel> newsModelList = newsRepository.retrieveAll();
            final Map recommendedNewsMap = new HashMap<String, String>();

            int numberOfNewsLoaded = 0;
            final JsonObject jsonObj = new JsonObject();
            for(final NewsModel newsModel : newsModelList) {

                if(numberOfNewsLoaded >= numberOfNewsToLoadMax || alreadyLoadedNews.contains(newsModel.getNewsId())) {
                    continue;
                }
                numberOfNewsLoaded++;

                recommendedNewsMap.put(newsModel.getNewsId(), newsModel.getNewsHead());
                jsonObj.addProperty(newsModel.getNewsId(), newsModel.getNewsHead());
            }

            if(numberOfNewsLoaded ==  0) {
                jsonObj.addProperty("-1", "ERROR : No more news to load.");
            }
            return jsonObj;
        }


        // Calculate theta
        final String newsClickedConcatenated = userActivityModel.getNewsClicked();
        final String[] newsClicked = newsClickedConcatenated.split(",");

        System.out.println("clicked news : " + Arrays.toString(newsClicked));

        final NewsModel[] clickedNewsModels = new NewsModel[newsClicked.length];
        final double[][] clickedNewsFeatureVectors = new double[newsClicked.length][LatentDirichletAllocation.numberOfTopics];

        for(int i = 0; i < newsClicked.length; i++) {
            clickedNewsModels[i] = newsRepository.retrieve(newsClicked[i]);
            clickedNewsFeatureVectors[i] = clickedNewsModels[i].getNewsFeatureVector();
        }

        final int numberOfNewsToConsiderActual = (newsClicked.length < numberOfNewsToConsiderMax) ? newsClicked.length : numberOfNewsToConsiderMax;
        final double[] y = new double[numberOfNewsToConsiderActual];
        Arrays.fill(y, 1);

        final double[] theta = gradientDescent.findCustom(Arrays.copyOfRange(clickedNewsFeatureVectors, newsClicked.length - numberOfNewsToConsiderActual, newsClicked.length), y);

        System.out.println("theta : " + Arrays.toString(theta));

        // Calculate ranks
        final List<NewsModel> newsModelList = newsRepository.retrieveAll();
        final Multimap<Double, NewsModel> recommendedNewsSortedMultimap =  MultimapBuilder.treeKeys().arrayListValues().build();

        for(final NewsModel newsModel : newsModelList) {

            if(alreadyLoadedNews.contains(newsModel.getNewsId())) {
                continue;
            }

            final ArrayRealVector thetaRealVector = new ArrayRealVector(theta);
            final ArrayRealVector featureVector = new ArrayRealVector(newsModel.getNewsFeatureVector());
            final double score = thetaRealVector.dotProduct(featureVector);

            newsModel.setNewsHead(newsModel.getNewsHead() + score);
            recommendedNewsSortedMultimap.put(score, newsModel);
        }

        // Create json object return.
        final JsonObject jsonObject = new JsonObject();

        final ArrayList<NewsModel> values = new ArrayList<>(recommendedNewsSortedMultimap.values());
        Collections.reverse(values);

        int numberOfNewsLoaded = 0;
        for(final NewsModel newsModel : values) {

            if(numberOfNewsLoaded >= numberOfNewsToLoadMax) {
                break;
            }
            numberOfNewsLoaded++;

            jsonObject.addProperty(newsModel.getNewsId(), newsModel.getNewsHead());
        }

        if(numberOfNewsLoaded ==  0) {
            jsonObject.addProperty("-1", "ERROR : No more news to load.");
        }
        return jsonObject;
    }
}
