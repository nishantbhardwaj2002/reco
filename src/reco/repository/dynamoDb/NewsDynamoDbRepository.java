package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reco.model.NewsModel;
import reco.repository.NewsRepository;

import java.util.*;

import static reco.repository.dynamoDb.Constants.newsTableName;


@Repository
public class NewsDynamoDbRepository implements NewsRepository {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public NewsDynamoDbRepository(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public NewsModel create(final String head,
                            final String body,
                            final String thumbnailUrl,
                            final String url,
                            final double[] featureVector) {

        final NewsModel newsModel = new NewsModel();
        newsModel.setNewsId(UUID.randomUUID().toString().replaceAll("-", ""));
        newsModel.setNewsHead(head);
        newsModel.setNewsBody(body);
        newsModel.setThumbnailUrl(thumbnailUrl);
        newsModel.setUrl(url);
        newsModel.setNewsFeatureVector(featureVector);

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final Item item = new Item()
                .withPrimaryKey("NewsId", newsModel.getNewsId())
                .withString("NewsHead", newsModel.getNewsHead())
                .withString("NewsBody", newsModel.getNewsBody())
                .withString("ThumbnailUrl", newsModel.getThumbnailUrl())
                .withString("Url", newsModel.getUrl())
                .withString("NewsFeatureVector", Arrays.toString(newsModel.getNewsFeatureVector()));

        table.putItem(item);

        return newsModel;
    }

    @Override
    public NewsModel retrieve(final String newsId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final Item item = table.getItem("NewsId", newsId);

        final NewsModel newsModel = new NewsModel();
        newsModel.setNewsId(item.getString("NewsId"));
        newsModel.setNewsHead(item.getString("NewsHead"));
        newsModel.setNewsBody(item.getString("NewsBody"));
        newsModel.setThumbnailUrl(item.getString("ThumbnailUrl"));
        newsModel.setUrl(item.getString("Url"));

        final String[] featureVectorStringArray = item.getString("NewsFeatureVector").replaceAll("[\\[\\] ]", "").split(",");

        final double[] featureVector = new double[featureVectorStringArray.length];
        for(int i = 0; i < featureVector.length; i++)
            featureVector[i] = Double.parseDouble(featureVectorStringArray[i]);
        newsModel.setNewsFeatureVector(featureVector);

        return newsModel;
    }

    @Override
    public List retrieveAll() {

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);

        final ItemCollection<ScanOutcome> items = table.scan();

        final List newsModelList = new LinkedList<NewsModel>();
        final Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            final Item item = iterator.next();

            final NewsModel newsModel = new NewsModel();
            newsModel.setNewsId(item.getString("NewsId"));
            newsModel.setNewsHead(item.getString("NewsHead"));
            newsModel.setNewsBody(item.getString("NewsBody"));
            newsModel.setThumbnailUrl(item.getString("ThumbnailUrl"));
            newsModel.setUrl(item.getString("Url"));

            final String[] featureVectorStringArray = item.getString("NewsFeatureVector").replaceAll("[\\[\\] ]", "").split(",");

            final double[] featureVector = new double[featureVectorStringArray.length];
            for(int i = 0; i < featureVector.length; i++)
                featureVector[i] = Double.parseDouble(featureVectorStringArray[i]);
            newsModel.setNewsFeatureVector(featureVector);

            newsModelList.add(newsModel);
        }

        return newsModelList;
    }
}
