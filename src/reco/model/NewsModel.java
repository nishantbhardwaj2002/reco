package reco.model;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsModel {

    private String newsId;
    private String newsHead;
    private String newsBody;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(final String newsId) {
        this.newsId = newsId;
    }

    public String getNewsHead() {
        return newsHead;
    }

    public void setNewsHead(final String newsHead) {
        this.newsHead = newsHead;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(final String newsBody) {
        this.newsBody = newsBody;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "newsId='" + newsId + '\'' +
                ", newsHead='" + newsHead + '\'' +
                ", newsBody='" + newsBody + '\'' +
                '}';
    }
}
