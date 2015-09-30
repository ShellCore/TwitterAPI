package mx.shellcore.android.twitterapi;

import android.app.Application;
import android.util.Log;

public class TwitterApiApplication extends Application {

    public static final String TAG = TwitterApiApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminated");
    }
}
