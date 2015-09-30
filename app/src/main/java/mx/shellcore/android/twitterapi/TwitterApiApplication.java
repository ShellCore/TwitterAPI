package mx.shellcore.android.twitterapi;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class TwitterApiApplication extends Application {

    public static final String TAG = TwitterApiApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
        startService(new Intent(this, UpdaterService.class));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminated");
        stopService(new Intent(this, UpdaterService.class));
    }
}
