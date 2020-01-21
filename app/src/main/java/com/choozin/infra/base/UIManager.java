package com.choozin.infra.base;

import java.lang.ref.WeakReference;

public class UIManager extends BaseManager {

    private static UIManager instance;

    public static UIManager getInstance(){
        if (instance == null)
            instance = new UIManager();
        return instance;
    }
    private UIManager(){}

    private static WeakReference<BaseActivity> foregroundActivity;

    public void setActivity(BaseActivity activity) {
        foregroundActivity = new WeakReference<>(activity);
    }

    public void dispatchUpdateUI() {
        if (foregroundActivity != null && foregroundActivity.get() != null) {
            foregroundActivity.get().updateUI();
        }
    }
}
