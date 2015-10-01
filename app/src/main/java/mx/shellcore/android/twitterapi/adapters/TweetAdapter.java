package mx.shellcore.android.twitterapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.R;
import mx.shellcore.android.twitterapi.models.Tweet;
import mx.shellcore.android.twitterapi.utils.DateUtils;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private Context context;
    private ArrayList<Tweet> tweets;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_tweet, parent, false);

        return new TweetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        if (tweet.getUrlImage() != null) {
            holder.setUserAvatar(tweet.getUrlImage());
        } else {
            holder.setDefaultUserAvatar();
        }

        holder.setUserName(tweet.getUserName());
        holder.setUserTwitter("@" + tweet.getUserTwitter());
        holder.setUserTweet(tweet.getUserTweet());
        holder.setTweetDate(DateUtils.setDateFormat(tweet.getTweetDate()));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addAll(@NonNull ArrayList<Tweet> tweets) {
        this.tweets = new ArrayList<>();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {

        private ImageView userAvatar;
        private TextView userName;
        private TextView userTwitter;
        private TextView userTweet;
        private TextView tweetDate;

        public TweetViewHolder(View itemView) {
            super(itemView);

            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userTwitter = (TextView) itemView.findViewById(R.id.user_twitter);
            userTweet = (TextView) itemView.findViewById(R.id.user_tweet);
            tweetDate = (TextView) itemView.findViewById(R.id.tweet_date);
        }

        public void setTweetDate(String tweetDate) {
            this.tweetDate.setText(tweetDate);
        }

        public void setUserAvatar(String imageUrl) {
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.user_avatar_default)
                    .into(userAvatar);
        }

        public void setDefaultUserAvatar() {
            Picasso.with(context)
                    .load(R.drawable.user_avatar_default)
                    .into(userAvatar);
        }

        public void setUserName(String userName) {
            this.userName.setText(userName);
        }

        public void setUserTweet(String userTweet) {
            this.userTweet.setText(userTweet);
        }

        public void setUserTwitter(String userTwitter) {
            this.userTwitter.setText(userTwitter);
        }
    }
}
