package mx.shellcore.android.twitterapi.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.R;
import mx.shellcore.android.twitterapi.adapters.TweetAdapter;
import mx.shellcore.android.twitterapi.models.Tweet;
import mx.shellcore.android.twitterapi.utils.ConstantUtils;
import mx.shellcore.android.twitterapi.utils.TwitterUtils;

public class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets = new ArrayList<>();


    // Components
    private RecyclerView recTweets;
    private TweetAdapter tweetAdapter;

    public TweetListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tweetAdapter = new TweetAdapter(getActivity().getApplicationContext(), tweets);

        recTweets = (RecyclerView) getActivity().findViewById(R.id.rec_tweets);
        recTweets.setHasFixedSize(true);
        recTweets.setAdapter(tweetAdapter);
        recTweets.setLayoutManager(new LinearLayoutManager(getActivity()));
        recTweets.setItemAnimator(new DefaultItemAnimator());

        new GetTimelineTask().execute();
    }

    private void updateTweets(ArrayList<Tweet> tweets) {
        tweetAdapter.addAll(tweets);
    }

    class GetTimelineTask extends AsyncTask<Object, Void, ArrayList<Tweet>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(getString(R.string.tweet_search_loader));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Tweet> tweets) {
            super.onPostExecute(tweets);
            progressDialog.dismiss();

            if (tweets.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.tweets_not_found, Toast.LENGTH_SHORT)
                        .show();
            } else {
                updateTweets(tweets);
            }
        }

        @Override
        protected ArrayList<Tweet> doInBackground(Object... params) {
            ArrayList<Tweet> tweets = new ArrayList<>();

            try {
                String timeline = TwitterUtils.getTimelineForSearchTerm(ConstantUtils.MEJORANDROID_TERM);

                // Objeto statuses
                JSONObject jsonResponse = new JSONObject(timeline);

                // Arreglo de "statuses"
                JSONArray jsonArray = jsonResponse.getJSONArray("statuses");

                // Objeto
                JSONObject tweetJsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    tweetJsonObject = jsonArray.getJSONObject(i);

                    Tweet tweet = new Tweet();
                    tweet.setUserName(tweetJsonObject.getJSONObject("user").getString("name"));
                    tweet.setUrlImage(tweetJsonObject.getJSONObject("user").getString("profile_image_url"));
                    tweet.setUserTwitter(tweetJsonObject.getJSONObject("user").getString("screen_name"));
                    tweet.setUserTweet(tweetJsonObject.getString("text"));
                    tweet.setTweetDate(tweetJsonObject.getString("created_at"));

                    tweets.add(i, tweet);

                }

            } catch (Exception e) {
                Log.e("Error JSON exception ", Log.getStackTraceString(e));
            }

            return tweets;
        }
    }
}
