package mx.shellcore.android.twitterapi.models;

import mx.shellcore.android.twitterapi.utils.DateUtils;

public class Tweet {

    private String id;
    private String userName;
    private String userTwitter;
    private String userTweet;
    private String tweetDate;
    private String urlImage;

    public Tweet() {
    }

    public Tweet(String id,String userName, String userTwitter, String userTweet, String tweetDate, String urlImage) {
        this.id = id;
        this.userName = userName;
        this.userTwitter = userTwitter;
        this.userTweet = userTweet;
        this.urlImage = urlImage;

        setTweetDate(tweetDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(String tweetDate) {
        this.tweetDate = DateUtils.setDateFormat(tweetDate);
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTweet() {
        return userTweet;
    }

    public void setUserTweet(String userTweet) {
        this.userTweet = userTweet;
    }

    public String getUserTwitter() {
        return "@" + userTwitter;
    }

    public void setUserTwitter(String userTwitter) {
        this.userTwitter = userTwitter;
    }
}
