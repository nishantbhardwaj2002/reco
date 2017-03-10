package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reco.model.NewsModel;
import reco.repository.NewsRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static reco.repository.dynamoDb.Constants.newsTableName;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Repository
public class NewsDynamoDbRepository implements NewsRepository {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public NewsDynamoDbRepository(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public NewsModel create(final String head, final String body) {

        final NewsModel newsModel = new NewsModel();
        newsModel.setNewsId(UUID.randomUUID().toString().replaceAll("-", ""));
        newsModel.setHead(head);
        newsModel.setBody(body);

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final Item item = new Item()
                .withPrimaryKey("NewsId", newsModel.getNewsId())
                .withString("Head", newsModel.getHead())
                .withString("Body", newsModel.getBody());

        table.putItem(item);

        return newsModel;
    }

    @Override
    public NewsModel retrieve(final String newsId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final Item item = table.getItem("NewsId", newsId);

        final NewsModel newsModel = new NewsModel();
        newsModel.setNewsId(item.getString("NewsId"));
        newsModel.setHead(item.getString("Head"));
        newsModel.setBody(item.getString("Body"));

        return newsModel;
    }

    @Override
    public List retrieve() {

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final ItemCollection<ScanOutcome> items = table.scan();

        List newsModelList = new LinkedList<NewsModel>();
        final Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            final Item item = iterator.next();

            final NewsModel newsModel = new NewsModel();
            newsModel.setNewsId(item.getString("NewsId"));
            newsModel.setHead(item.getString("Head"));
            newsModel.setBody(item.getString("Body"));

            newsModelList.add(newsModel);
        }

        return newsModelList;
    }
}
