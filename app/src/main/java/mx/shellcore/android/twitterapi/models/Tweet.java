package mx.shellcore.android.twitterapi.models;

public class Tweet {

    private String userName;
    private String userTwitter;
    private String userTweet;
    private String tweetDate;
    private String urlImage;

    public Tweet() {
    }

    public Tweet(String userName, String userTwitter, String userTweet, String tweetDate, String urlImage) {
        this.userName = userName;
        this.userTwitter = userTwitter;
        this.userTweet = userTweet;
        this.tweetDate = tweetDate;
        this.urlImage = urlImage;
    }

    public String getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(String tweetDate) {
        this.tweetDate = tweetDate;
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
        return userTwitter;
    }

    public void setUserTwitter(String userTwitter) {
        this.userTwitter = userTwitter;
    }
}
