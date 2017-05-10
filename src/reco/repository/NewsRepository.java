package reco.repository;

import reco.model.NewsModel;

import java.util.List;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public interface NewsRepository {

    NewsModel create (final String head,
                      final String body,
                      final String thumbnailUrl,
                      final String url,
                      final double[] featureVector);

    NewsModel retrieve (final String newsId);

    List retrieveAll ();
}
