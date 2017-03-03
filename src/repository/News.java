package repository;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public interface News {

    model.News create (final String head, final String body);
    model.News retrieve (final String id);
    model.News retrieveUsingId (final String id);
}
