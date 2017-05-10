package reco.model;

import java.util.Arrays;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class NewsModel {

    private String newsId;
    private String newsHead;
    private String newsBody;
    private String thumbnailUrl;
    private String url;
    private double[] newsFeatureVector;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double[] getNewsFeatureVector() {
        return newsFeatureVector;
    }

    public void setNewsFeatureVector(final double[] newsFeatureVector) {
        this.newsFeatureVector = newsFeatureVector;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "newsId='" + newsId + '\'' +
                ", newsHead='" + newsHead + '\'' +
                ", newsBody='" + newsBody + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", url='" + url + '\'' +
                ", newsFeatureVector=" + Arrays.toString(newsFeatureVector) +
                '}';
    }
}
