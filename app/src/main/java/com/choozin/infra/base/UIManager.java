package com.choozin.infra.base;

import java.lang.ref.WeakReference;

public class UIManager extends BaseManager {

    private static UIManager instance;
    private static WeakReference<BaseActivity> foregroundActivity;

    private UIManager() {
    }

    public static UIManager getInstance() {
        if (instance == null)
            instance = new UIManager();
        return instance;
    }

    // Assigning the current running activity to a var.
    public void setActivity(BaseActivity activity) {
        foregroundActivity = new WeakReference<>(activity);
    }

    // Getting the current running activity.
    public WeakReference<BaseActivity> getCurrentActivity() {
        return foregroundActivity;
    }

    // Calling updateUI on the current running activity.
    public void dispatchUpdateUI() {
        if (foregroundActivity != null && foregroundActivity.get() != null) {
            foregroundActivity.get().updateUI();
        }
    }
}
