package mx.shellcore.android.twitterapi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import mx.shellcore.android.twitterapi.models.Tweet;

public class DBOperations {

    public static final String TAG = DBOperations.class.getSimpleName();

    private DBHelper dbHelper;

    public DBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertOrIgnore(ContentValues values) {
        Log.d(TAG, "insertOrIgnore on: " + values);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.insertWithOnConflict(DBHelper.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            database.close();
        }
    }

    public ArrayList<Tweet> getStatusUpdates() {
        ArrayList<Tweet> tweets = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Tweet tweet = new Tweet();

                tweet.setId(String.valueOf(cursor.getInt(DBHelper.C_ID_INDEX)));
                tweet.setUserName(cursor.getString(DBHelper.C_NAME_INDEX));
                tweet.setUserTwitter(cursor.getString(DBHelper.C_SCREEN_NAME_INDEX));
                tweet.setUrlImage(cursor.getString(DBHelper.C_IMAGE_PROFILE_URL_INDEX));
                tweet.setUserTweet(cursor.getString(DBHelper.C_TEXT_INDEX));
                tweet.setTweetDate(cursor.getString(DBHelper.C_CREATED_AT_INDEX));

                tweets.add(tweet);
                cursor.moveToNext();
            }
        }

        return tweets;
    }
}
