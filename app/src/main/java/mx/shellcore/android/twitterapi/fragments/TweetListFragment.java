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

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.R;
import mx.shellcore.android.twitterapi.adapters.TweetAdapter;
import mx.shellcore.android.twitterapi.database.DBOperations;
import mx.shellcore.android.twitterapi.models.Tweet;

public class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets = new ArrayList<>();


    // Components
    private RecyclerView recTweets;
    private TweetAdapter tweetAdapter;
    private DBOperations dbOperations;

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

        dbOperations = new DBOperations(getActivity().getApplicationContext());

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
        tweetAdapter.notifyDataSetChanged();
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
//                tweets = TwitterUtils.getTimelineForSearchTerm(ConstantUtils.MEJORANDROID_TERM);
                tweets = dbOperations.getStatusUpdates();
            } catch (Exception e) {
                Log.e("Error JSON exception ", Log.getStackTraceString(e));
            }

            return tweets;
        }
    }
}
