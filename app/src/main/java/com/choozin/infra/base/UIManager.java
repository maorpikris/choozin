package com.choozin.infra.base;

import com.choozin.infra.base.BaseManager;

import java.lang.ref.WeakReference;

public class UIManager extends BaseManager {


    public static UIManager instance;
    public static UIManager getInstance(){
        if (instance == null)
            instance = new UIManager();
        return instance;
    }
    private UIManager(){}

    private static WeakReference<BaseActivity> foregroundActivity;

    public static void setActivity(BaseActivity activity) {
        foregroundActivity = new WeakReference<>(activity);
    }

    protected void dispatchUpdateUI() {
        if (foregroundActivity != null && foregroundActivity.get() != null) {
            foregroundActivity.get().updateUI();
        }
    }


}
