package reco.repository.jdbc;

import org.springframework.stereotype.Repository;
import reco.model.NewsModel;
import reco.repository.NewsRepository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Repository
public class NewsJdbcRepository implements NewsRepository {

    @Override
    public NewsModel create(final String head, final String body) {

        final NewsModel newsModel = new NewsModel();
        newsModel.setId("2");
        newsModel.setHead(head);
        newsModel.setBody(body);

        return newsModel;
    }

    /*
     * TODO : to be user for recommendation and can return multiple news items.
     */
    @Override
    public NewsModel retrieve(final String id) {

        if(id.equals("1")) {
            final NewsModel newsModel = new NewsModel();
            newsModel.setId("1");
            newsModel.setHead("First human landed on mars.");
            newsModel.setBody("First human landed on mars today.");

            return newsModel;
        } else {
            return null;
        }
    }

    @Override
    public NewsModel retrieveUsingId(final String id) {

        if(id.equals("1")) {
            final NewsModel newsModel = new NewsModel();
            newsModel.setId("1");
            newsModel.setHead("First human landed on mars.");
            newsModel.setBody("First human landed on mars today.");

            return newsModel;
        } else {
            return null;
        }
    }
}
