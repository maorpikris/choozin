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

    public void setActivity(BaseActivity activity) {
        foregroundActivity = new WeakReference<>(activity);
    }

    public void dispatchUpdateUI() {
        if (foregroundActivity != null && foregroundActivity.get() != null) {
            foregroundActivity.get().updateUI();
        }
    }
}
