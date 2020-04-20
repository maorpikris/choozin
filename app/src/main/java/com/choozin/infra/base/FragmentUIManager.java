package com.choozin.infra.base;

import com.choozin.managers.ThreadsManager;

import java.lang.ref.WeakReference;

public class FragmentUIManager extends BaseManager {

    private static FragmentUIManager instance;
    private static WeakReference<BaseFragment> foregroundFragment;

    public static FragmentUIManager getInstance() {
        if (instance == null)
            instance = new FragmentUIManager();
        return instance;
    }

    public void setFragment(BaseFragment fragment) {
        foregroundFragment = new WeakReference<>(fragment);
    }

    public WeakReference<BaseFragment> getForegroundFragment() {
        return foregroundFragment;
    }

    public void dispatchUpdateUI() {
        if (foregroundFragment != null && foregroundFragment.get() != null) {
            ThreadsManager.getInstance().getDefaultHandler(ThreadsManager.MainThread).post(new Runnable() {
                @Override
                public void run() {
                    foregroundFragment.get().updateUI();
                }
            });

        }
    }

}
