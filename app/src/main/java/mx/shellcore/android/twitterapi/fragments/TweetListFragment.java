package mx.shellcore.android.twitterapi.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.R;
import mx.shellcore.android.twitterapi.adapters.TweetAdapter;
import mx.shellcore.android.twitterapi.models.Tweet;

public class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets = new ArrayList<>();


    // Components
    private RecyclerView recTweets;

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

        final TweetAdapter tweetAdapter = new TweetAdapter(getActivity().getApplicationContext(), tweets);

        recTweets = (RecyclerView) getActivity().findViewById(R.id.rec_tweets);
        recTweets.setHasFixedSize(true);
        recTweets.setAdapter(tweetAdapter);
        recTweets.setLayoutManager(new LinearLayoutManager(getActivity()));
        recTweets.setItemAnimator(new DefaultItemAnimator());
    }
}
