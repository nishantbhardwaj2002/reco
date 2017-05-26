package reco.model;


public class UserActivityModel {

    private String userId;
    private String newsClicked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsClicked() {
        return newsClicked;
    }

    public void setNewsClicked(String newsClicked) {
        this.newsClicked = newsClicked;
    }

    @Override
    public String toString() {
        return "UserActivityModel{" +
                "userId='" + userId + '\'' +
                ", newsClicked='" + newsClicked + '\'' +
                '}';
    }
}
