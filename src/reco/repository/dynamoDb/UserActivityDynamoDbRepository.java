package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reco.model.UserActivityModel;
import reco.repository.UserActivityRepository;

import static reco.repository.dynamoDb.Constants.userActivityTableName;


@Repository
public class UserActivityDynamoDbRepository implements UserActivityRepository {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public UserActivityDynamoDbRepository(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public UserActivityModel create(final String userId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userActivityTableName);

        final UserActivityModel userActivityModel = new UserActivityModel();
        userActivityModel.setUserId(userId);
        userActivityModel.setNewsClicked("");


        final Item item = new Item()
                .withPrimaryKey("UserId", userActivityModel.getUserId())
                .withString("NewsClicked", userActivityModel.getNewsClicked());

        table.putItem(item);

        return userActivityModel;
    }

    /**
     * News clicked is simply a String of the form "newsId9,newsId7..."
     */
    @Override
    public UserActivityModel update(final String userId, final String newsId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userActivityTableName);

        final UserActivityModel userActivityModel = new UserActivityModel();
        userActivityModel.setUserId(userId);

        final Item existingItem = table.getItem("UserId", userId);
        if(existingItem != null) {
            userActivityModel.setNewsClicked(existingItem.get("NewsClicked") + "," + newsId);
        } else {
            userActivityModel.setNewsClicked(newsId);
        }

        final Item item = new Item()
                .withPrimaryKey("UserId", userActivityModel.getUserId())
                .withString("NewsClicked", userActivityModel.getNewsClicked());

        table.putItem(item);

        return userActivityModel;
    }

    @Override
    public UserActivityModel retrieve(String userId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userActivityTableName);

        final Item item = table.getItem("UserId", userId);

        if(item == null) {
            return null;
        }

        final UserActivityModel userActivityModel= new UserActivityModel();
        userActivityModel.setUserId(item.getString("UserId"));
        userActivityModel.setNewsClicked(item.getString("NewsClicked"));

        return userActivityModel;
    }
}
