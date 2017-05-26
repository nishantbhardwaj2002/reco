package reco.repository.dynamoDb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.stereotype.Component;


@Component
public class DynamoDbClient {

    final private DynamoDB dynamoDb;

    public DynamoDB getDynamoDb() {
        return dynamoDb;
    }

    public DynamoDbClient() {

        final AmazonDynamoDBClientBuilder amazonDynamoDBClientBuilder =
                AmazonDynamoDBClientBuilder
                        .standard()
                        .withEndpointConfiguration(
                                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"));

        dynamoDb = new DynamoDB(amazonDynamoDBClientBuilder.build());
    }
}
