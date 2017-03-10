package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reco.model.UserModel;
import reco.repository.UserRepository;

import java.util.Iterator;
import java.util.UUID;

import static reco.repository.dynamoDb.Constants.userTableGsiOnUsernameName;
import static reco.repository.dynamoDb.Constants.userTableName;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Repository
public class UserDynamoDbRepository implements UserRepository {

    private final DynamoDbClient dynamoDbClient;
    private final RebuildAndFillMockDataInDatabase rebuildAndFillMockDataInDatabase;

    @Autowired
    public UserDynamoDbRepository(final DynamoDbClient dynamoDbClient, final RebuildAndFillMockDataInDatabase rebuildAndFillMockDataInDatabase) {
        this.dynamoDbClient = dynamoDbClient;
        this.rebuildAndFillMockDataInDatabase = rebuildAndFillMockDataInDatabase;
    }


    @Override
    public UserModel create(final String username, final String password) {

        final UserModel userModel = new UserModel();
        userModel.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        userModel.setUsername(username);
        userModel.setPassword(password);

        final Table table = dynamoDbClient.getDynamoDb().getTable(userTableName);

        final Item item = new Item()
                .withPrimaryKey("UserId", userModel.getUserId())
                .withString("Username", userModel.getUsername())
                .withString("Password", userModel.getPassword());

        table.putItem(item);

        return userModel;
    }

    @Override
    public UserModel retrieve(final String userId) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userTableName);

        final Item item = table.getItem("UserId", userId);

        final UserModel userModel = new UserModel();
        userModel.setUserId(item.getString("UserId"));
        userModel.setUsername(item.getString("Username"));
        userModel.setPassword(item.getString("Password"));

        return userModel;
    }

    @Override
    public UserModel retrieveUsingUsername(final String username) {

        rebuildAndFillMockDataInDatabase.doIt();

        final Table table = dynamoDbClient.getDynamoDb().getTable(userTableName);
        final Index index = table.getIndex(userTableGsiOnUsernameName);

        final QuerySpec querySpec = new QuerySpec();
        querySpec.withKeyConditionExpression("Username = :Username")
                .withValueMap(new ValueMap().withString(":Username", username));

        final ItemCollection<QueryOutcome> items = index.query(querySpec);

        final Iterator<Item> iterator = items.iterator();

        if(iterator.hasNext()) {
            final Item item = iterator.next();

            final UserModel userModel = new UserModel();
            userModel.setUserId(item.getString("UserId"));
            userModel.setUsername(item.getString("Username"));
            userModel.setPassword(item.getString("Password"));

            return userModel;
        }

        return null;
    }
}
