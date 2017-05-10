package reco.utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import reco.model.newsFromSource.NewsFromSourceModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nishantbhardwaj2002 on 9/5/17.
 */
@Component
public class NewsFromSources {

    private static final Gson gson = new Gson();
    private static String apiKey = "test";
    private static String endpoint = "https://content.guardianapis.com/search";

    private final HttpClient client = HttpClientBuilder.create().build();


    public String get(final int pageNumber) throws IOException {

        final String url = endpoint + "?" + apiKey + "&order-by=newest&order-date=published&show-fields=thumbnail,body&page-size=50&page=" + pageNumber;
        final HttpGet req =  new HttpGet(url);

        final HttpResponse response = client.execute(req);

        if(response.getStatusLine().getStatusCode() != 200) {
            return null;
        }

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    public NewsFromSourceModel unmarshalling(final String response){

        return gson.fromJson(response, NewsFromSourceModel.class);
    }
}
