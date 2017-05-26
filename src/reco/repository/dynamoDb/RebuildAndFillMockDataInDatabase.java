package reco.repository.dynamoDb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.jsoup.Jsoup;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reco.model.newsFromSource.NewsFromSourceModel;
import reco.model.newsFromSource.Result;
import reco.utils.LatentDirichletAllocation;
import reco.utils.NewsFromSources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static reco.repository.dynamoDb.Constants.*;


@Component
public class RebuildAndFillMockDataInDatabase {

    private final DynamoDbClient dynamoDbClient;
    private final LatentDirichletAllocation latentDirichletAllocation;

    @Autowired
    public RebuildAndFillMockDataInDatabase(final DynamoDbClient dynamoDbClient,
                                            final LatentDirichletAllocation latentDirichletAllocation) {

        this.dynamoDbClient = dynamoDbClient;
        this.latentDirichletAllocation = latentDirichletAllocation;
    }

    public void doIt() {

        deleteTable(userTableName);
        deleteTable(newsTableName);
        deleteTable(userActivityTableName);

        createTable(userTableName, 10L, 5L, "UserId", "S");
        createTable(userActivityTableName, 10L, 5L, "UserId", "S");
        createTable(newsTableName, 10L, 5L, "NewsId", "S");

        fillMockUsers();
        fillMockUserActivity();
        fillMockNews();
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
        for(int i = 0; i < 5; i++) {
            try {
                System.out.println("Adding data to " + newsTableName);

                final NewsFromSources nfs = new NewsFromSources();
                final NewsFromSourceModel nfsModel = nfs.unmarshalling(nfs.get(i+1));
                final List<Result> newsList = nfsModel.getResponse().getResults();

                for (Result result : newsList) {

                    final String title = result.getWebTitle();
                    final String thumbnail = result.getFields().getThumbnail();
                    final String url = result.getWebUrl();
                    final String newsBodyToText = Jsoup.parse(result.getFields().getBody()).text();
                    final String nfv = (newsBodyToText != null)? Arrays.toString(latentDirichletAllocation.infer(newsBodyToText)): null;

                    if(title != null && thumbnail != null && url != null && nfv != null) {

                        final Item item = new Item()
                                .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                                .withString("NewsHead", title)
                                .withString("NewsBody", newsBodyToText)
                                .withString("ThumbnailUrl", thumbnail)
                                .withString("Url", url)
                                .withString("NewsFeatureVector", nfv);

                        table.putItem(item);
                    }
                }

            /*
            final CSVReader csvReader = new CSVReader(new FileReader("/home/nishantbhardwaj2002/workspace/reco/1/reco/src/reco/resources/news.csv"));
            final String[] headers = csvReader.readNext();

            String[] nextLine;
            while ( (nextLine = csvReader.readNext()) != null) {

                final Item item = new Item()
                        .withPrimaryKey("NewsId", UUID.randomUUID().toString().replaceAll("-", ""))
                        .withString("NewsHead", nextLine[1])
                        .withString("NewsBody", nextLine[2])
                        .withString("NewsFeatureVector", Arrays.toString(latentDirichletAllocation.infer(nextLine[2])));

                table.putItem(item);
            }
            */


            } catch (final Exception e) {
                System.err.println("Failed to create item in " + newsTableName);
                e.printStackTrace();
                System.err.println(e.getStackTrace());
            }
        }
    }

    private void fillMockUserActivity() {

    }
}
