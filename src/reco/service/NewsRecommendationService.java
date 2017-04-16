package reco.service;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reco.model.NewsModel;
import reco.model.UserActivityModel;
import reco.model.UserModel;
import reco.repository.NewsRepository;
import reco.repository.UserActivityRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Service
public class NewsRecommendationService {

    private final NewsRepository newsRepository;
    private final UserActivityRepository userActivityRepository;

    @Autowired
    public NewsRecommendationService(final NewsRepository newsRepository, final UserActivityRepository userActivityRepository) {
        this.newsRepository = newsRepository;
        this.userActivityRepository = userActivityRepository;
    }

    public Map getRecommendedNewsHeads(final String context, final UserModel userModel) {

        System.out.println("yo boy enterted gnrh");
        final UserActivityModel userActivityModel = userActivityRepository.retrieve(userModel.getUserId());
        System.out.println("1");
        if(userActivityModel == null) {
            System.out.println("null. SHIT");
            final List<NewsModel> newsModelList = newsRepository.retrieveAll();
            final Map recommendedNewsMap = new HashMap<String, String>();

            for(final NewsModel newsModel : newsModelList) {
                recommendedNewsMap.put(newsModel.getNewsId(), newsModel.getNewsHead() + " DP : ");
            }

            return recommendedNewsMap;
        } else {
            System.out.println("ell fine." + userActivityModel.getNewsClicked());
        }
        final String newsClickedString = userActivityModel.getNewsClicked();
        System.out.println("2");
        final String[] newsClicked= newsClickedString.split(",");
        final NewsModel[] clickedNewsModels = new NewsModel[newsClicked.length];
        final double[][] clickedNewsFeatureVectors = new double[newsClicked.length][5];
        System.out.println("3");
        for(int i = 0; i < newsClicked.length; i++) {
            System.out.println("lull");
            clickedNewsModels[i] = newsRepository.retrieve(newsClicked[i]);
            clickedNewsFeatureVectors[i] = clickedNewsModels[i].getNewsFeatureVector();
        }

        double[] y = new double[newsClicked.length];
        Arrays.fill(y, 1);
        double[] theta = Optimization.reg(clickedNewsFeatureVectors, y);


        System.out.println("yo boy done some opti shit. heres theta : " + theta);


        final List<NewsModel> newsModelList = newsRepository.retrieveAll();
        final Map recommendedNewsMap = new HashMap<String, String>();

        for(final NewsModel newsModel : newsModelList) {
            String str = "";
            double[] theta2  = new double[theta.length-1];
            for(int i = 0; i < theta.length -1 ; i++) theta2[i] = theta[i+1];
            ArrayRealVector a = new ArrayRealVector(theta2);
            ArrayRealVector b = new ArrayRealVector(newsModel.getNewsFeatureVector());
            double c = theta[0] + a.dotProduct(b);
            recommendedNewsMap.put(newsModel.getNewsId(), newsModel.getNewsHead() + " DP : " + c);
        }

        return recommendedNewsMap;
    }
}
