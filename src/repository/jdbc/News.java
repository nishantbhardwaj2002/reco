package repository.jdbc;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class News implements repository.News {

    @Override
    public model.News create(final String head, final String body) {

        final model.News news = new model.News();
        news.setId("2");
        news.setHead(head);
        news.setBody(body);

        return news;
    }

    /*
     * TODO : to be user for recommendation and can return multiple news items.
     */
    @Override
    public model.News retrieve(final String id) {

        if(id.equals("1")) {
            final model.News news = new model.News();
            news.setId("1");
            news.setHead("First human landed on mars.");
            news.setBody("First human landed on mars today.");

            return news;
        } else {
            return null;
        }
    }

    @Override
    public model.News retrieveUsingId(final String id) {

        if(id.equals("1")) {
            final model.News news = new model.News();
            news.setId("1");
            news.setHead("First human landed on mars.");
            news.setBody("First human landed on mars today.");

            return news;
        } else {
            return null;
        }
    }
}
