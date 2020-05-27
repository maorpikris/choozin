package com.choozin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.choozin.infra.base.BaseActivity;
import com.choozin.infra.base.UIManager;

// Setting the default behaviour of the activities in the app.
public class ChoozinApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static Context context;

    public static Context getAppContext() {
        return ChoozinApplication.context;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity instanceof BaseActivity) {
            // Setting the current activity on the ui manager when activity resumed.
            UIManager.getInstance().setActivity((BaseActivity) activity);
        } else {
            Log.w(getClass().getSimpleName(), "Resumed non-base activity : " + activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        // Saving the application context in a static variable.
        ChoozinApplication.context = getApplicationContext();
    }
}