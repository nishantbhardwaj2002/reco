package reco.model;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsModel {

    private String id;
    private String head;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(final String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "id='" + id + '\'' +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
