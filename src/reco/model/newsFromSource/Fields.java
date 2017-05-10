
package reco.model.newsFromSource;

import java.util.HashMap;
import java.util.Map;

public class Fields {

    private String body;
    private String thumbnail;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
