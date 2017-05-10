package reco;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by nishantbhardwaj2002 on 4/18/17.
 */
public class testing {

    public static void main(final String[] args) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("b", "c");
        jsonObject2.addProperty("d", "e");

        jsonObject.addProperty("a", new Gson().toJson(jsonObject2));

        System.out.println(new Gson().toJson(jsonObject));
    }
}