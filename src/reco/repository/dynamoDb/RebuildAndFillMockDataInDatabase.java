package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

import static reco.repository.dynamoDb.Constants.*;

/**
 * Created by nishantbhardwaj2002 on 3/10/17.
 */
@Component
public class RebuildAndFillMockDataInDatabase {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public RebuildAndFillMockDataInDatabase(final DynamoDbClient dynamoDbClient) {

        this.dynamoDbClient = dynamoDbClient;
    }

    public void doIt() {

        deleteTable(userTableName);
        deleteTable(newsTableName);

        createTable(userTableName, 10L, 5L, "UserId", "S");
        createTable(newsTableName, 10L, 5L, "NewsId", "S");

        fillMockNews();
        fillMockUsers();
    }

    private void deleteTable(final String tableName) {

        final Table table = dynamoDbClient.getDynamoDb().getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();
            System.out.println("Waiting for " + tableName + " to be deleted... this may take a while...");
            table.waitForDelete();

        } catch (final Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    private void createTable(final String tableName,
                             final long readCapacityUnits,
                             final long writeCapacityUnits,
                             final String partitionKeyName,
                             final String partitionKeyType) {

        try {
            final ArrayList keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement()
                    .withAttributeName(partitionKeyName)
                    .withKeyType(KeyType.HASH)); //Partition key

            // Initial provisioned throughput settings for the indexes
            final ProvisionedThroughput provisionedThroughputForUserTableGsiOnUsername = new ProvisionedThroughput()
                    .withReadCapacityUnits(10L)
                    .withWriteCapacityUnits(5L);

            CreateTableRequest request;

            if(tableName.equals(userTableName)) {
                final ArrayList attributeDefinitions = new ArrayList<AttributeDefinition>();
                attributeDefinitions.add(new AttributeDefinition()
                        .withAttributeName(partitionKeyName)
                        .withAttributeType(partitionKeyType));
                attributeDefinitions.add(new AttributeDefinition()
                        .withAttributeName("Username")
                        .withAttributeType("S"));

                // CreateDateIndex
                final GlobalSecondaryIndex userTableGsiOnUsername = new GlobalSecondaryIndex()
                        .withIndexName(userTableGsiOnUsernameName)
                        .withProvisionedThroughput(provisionedThroughputForUserTableGsiOnUsername)
                        .withKeySchema(new KeySchemaElement()
                                .withAttributeName("Username")
                                .withKeyType(KeyType.HASH)) //Partition key
                        .withProjection(new Projection().withProjectionType("ALL"));

                request = new CreateTableRequest()
                        .withTableName(tableName)
                        .withKeySchema(keySchema)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withProvisionedThroughput( new ProvisionedThroughput()
                                .withReadCapacityUnits(readCapacityUnits)
                                .withWriteCapacityUnits(writeCapacityUnits))
                        .withGlobalSecondaryIndexes(userTableGsiOnUsername);
            } else {
                final ArrayList attributeDefinitions = new ArrayList<AttributeDefinition>();
                attributeDefinitions.add(new AttributeDefinition()
                        .withAttributeName(partitionKeyName)
                        .withAttributeType(partitionKeyType));

                request = new CreateTableRequest()
                        .withTableName(tableName)
                        .withKeySchema(keySchema)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withProvisionedThroughput(new ProvisionedThroughput()
                                .withReadCapacityUnits(readCapacityUnits)
                                .withWriteCapacityUnits(writeCapacityUnits));
            }

            System.out.println("Issuing CreateTable request for " + tableName);
            final Table table = dynamoDbClient.getDynamoDb().createTable(request);
            System.out.println("Waiting for " + tableName + " to be created");
            table.waitForActive();

        } catch (final Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    private void fillMockUsers() {

        final Table table = dynamoDbClient.getDynamoDb().getTable(userTableName);
        try {
            System.out.println("Adding data to " + userTableName);

            Item item = new Item()
                    .withPrimaryKey("UserId", UUID.randomUUID().toString().replaceAll("-", ""))
                    .withString("Username", "nishant")
                    .withString("Password", BCrypt.hashpw("chiku", BCrypt.gensalt()));
            table.putItem(item);

        } catch (final Exception e) {
            System.err.println("Failed to create item in " + userTableName);
            System.err.println(e.getMessage());
        }
    }

    private void fillMockNews() {

        final Table table = dynamoDbClient.getDynamoDb().getTable(newsTableName);
        try {
            System.out.println("Adding data to " + newsTableName);

            Item item;

            item = new Item()
                    .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                    .withString("NewsHead", "Human walks on mars!")
                    .withString("NewsBody", "First human to walk on mars! Big moment for humans!");

            table.putItem(item);

            item = new Item()
                    .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                    .withString("NewsHead", "Bill Morison turns 1000 today!")
                    .withString("NewsBody", "Bill Morison becomes first known human to live this long!");

            table.putItem(item);

            item = new Item()
                    .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                    .withString("NewsHead", "Dogs start talking to humans!")
                    .withString("NewsBody", "Researchers from India genetically modified a labrador which can now talk to humans!");

            table.putItem(item);

            item = new Item()
                    .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                    .withString("NewsHead", "15 year old from NYC becomes the youngest self-made trillionaire!")
                    .withString("NewsBody", "CEO of AI-based-toys maker startup AiTo, becomes youngest trillionaire at the age of 15 years and 4 months! Pledges to donate 99% to Bill and Melinda Gates foundation!");

            table.putItem(item);

        } catch (final Exception e) {
            System.err.println("Failed to create item in " + newsTableName);
            System.err.println(e.getMessage());
        }
    }
}
