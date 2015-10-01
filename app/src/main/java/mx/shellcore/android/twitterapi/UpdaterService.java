package mx.shellcore.android.twitterapi;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.database.DBHelper;
import mx.shellcore.android.twitterapi.database.DBOperations;
import mx.shellcore.android.twitterapi.models.Tweet;
import mx.shellcore.android.twitterapi.utils.ConstantUtils;
import mx.shellcore.android.twitterapi.utils.TwitterUtils;

public class UpdaterService extends Service {

    public static final String TAG = UpdaterService.class.getSimpleName();

    static final int DELAY = 60000;
    private boolean runFlag = false;

    private Updater updater;
    private TwitterApiApplication application;
    private DBOperations dbOperations;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
        updater = new Updater();
        application = (TwitterApiApplication) getApplication();
        dbOperations = new DBOperations(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
        runFlag = false;
        application.setServiceRunningFlag(false);
        updater.interrupt();
        updater = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand");
        runFlag = true;
        application.setServiceRunningFlag(true);
        updater.start();
        return START_STICKY;
    }

    private class Updater extends Thread {

        private ArrayList<Tweet> timeline = new ArrayList<>();
        private Intent intent;

        public Updater() {
            super("UpdaterService-UpdaterThread");
        }

        @Override
        public void run() {
            UpdaterService updaterService = UpdaterService.this;
            while (updaterService.runFlag) {
                Log.d(TAG, "UpdaterThread running");
                try {
                    timeline = TwitterUtils.getTimelineForSearchTerm(ConstantUtils.MEJORANDROID_TERM);

                    ContentValues values = new ContentValues();
                    for (Tweet tweet : timeline) {
                        values.clear();
                        values.put(DBHelper.C_ID, tweet.getId());
                        values.put(DBHelper.C_NAME, tweet.getUserName());
                        values.put(DBHelper.C_SCREEN_NAME, tweet.getUserTwitter());
                        values.put(DBHelper.C_IMAGE_PROFILE_URL, tweet.getUrlImage());
                        values.put(DBHelper.C_TEXT, tweet.getUserTweet());
                        values.put(DBHelper.C_CREATED_AT, tweet.getTweetDate());

                        dbOperations.insertOrIgnore(values);
                    }

                    intent = new Intent(ConstantUtils.NEW_TWEETS_INTENT_FILTER);
                    updaterService.sendBroadcast(intent);

                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    updaterService.runFlag = false;
                    application.setServiceRunningFlag(false);
                }

            }
        }
    }
}
