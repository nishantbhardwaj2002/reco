package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reco.model.UserActivityModel;
import reco.repository.UserActivityRepository;

import static reco.repository.dynamoDb.Constants.userActivityTableName;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
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

    @Override
    public UserActivityModel update(final String userId, final String newsId) {

        System.out.print("update user act tbl : " + userId + " " + newsId);
        final Table table = dynamoDbClient.getDynamoDb().getTable(userActivityTableName);
        if(table != null) {
            System.out.println("table not null");
        } else {
            System.out.println("table null");
        }
        final UserActivityModel userActivityModel = new UserActivityModel();
        userActivityModel.setUserId(userId);

        final Item existingItem = table.getItem("UserId", userId);
        if(existingItem != null) {
            System.out.println("item exists");
            userActivityModel.setNewsClicked(existingItem.get("NewsClicked") + "," + newsId);
        } else {
            System.out.println("item doesnt exists");
            userActivityModel.setNewsClicked(newsId);
        }

        final Item item = new Item()
                .withPrimaryKey("UserId", userActivityModel.getUserId())
                .withString("NewsClicked", userActivityModel.getNewsClicked());

        System.out.println("putting item");
        table.putItem(item);
        System.out.println("done");
        return userActivityModel;
    }

    @Override
    public UserActivityModel retrieve(String userId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userActivityTableName);

        final Item item = table.getItem("UserId", userId);

        // TODO
        if(item == null) return null;
        final UserActivityModel userActivityModel= new UserActivityModel();
        userActivityModel.setUserId(item.getString("UserId"));
        userActivityModel.setNewsClicked(item.getString("NewsClicked"));

        return userActivityModel;
    }
}
