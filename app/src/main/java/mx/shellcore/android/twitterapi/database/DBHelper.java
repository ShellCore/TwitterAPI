package mx.shellcore.android.twitterapi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();

    public static final String DB_NAME = "timeline.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE = "timeline";
    public static final String C_ID = BaseColumns._ID;
    public static final String C_NAME = "name";
    public static final String C_SCREEN_NAME = "screen_name";
    public static final String C_IMAGE_PROFILE_URL = "image_profile_url";
    public static final String C_TEXT = "text";
    public static final String C_CREATED_AT = "created_at";

    public static final int C_ID_INDEX = 0;
    public static final int C_NAME_INDEX = 1;
    public static final int C_SCREEN_NAME_INDEX = 2;
    public static final int C_IMAGE_PROFILE_URL_INDEX = 3;
    public static final int C_TEXT_INDEX = 4;
    public static final int C_CREATED_AT_INDEX = 5;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table " + TABLE);
        sql.append(" (");
        sql.append(" " + C_ID + " INT PRIMARY KEY" + ",");
        sql.append(" " + C_NAME + " TEXT" + ",");
        sql.append(" " + C_SCREEN_NAME + "TEXT" + ",");
        sql.append(" " + C_IMAGE_PROFILE_URL + "TEXT" + ",");
        sql.append(" " + C_TEXT + "TEXT" + ",");
        sql.append(" " + C_CREATED_AT + "TEXT");
        sql.append(" )");

        db.execSQL(sql.toString());
        Log.d(TAG, "onCreated sql: " + sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE); // drop the old database
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }
}
