package reco.model;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsModel {

    private String newsId;
    private String head;
    private String body;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(final String newsId) {
        this.newsId = newsId;
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
                "newsId='" + newsId + '\'' +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
