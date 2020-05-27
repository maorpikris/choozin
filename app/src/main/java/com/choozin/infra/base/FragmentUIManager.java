package com.choozin.infra.base;

import androidx.fragment.app.FragmentManager;

import com.choozin.managers.ThreadsManager;

import java.lang.ref.WeakReference;

public class FragmentUIManager extends BaseManager {

    private static FragmentUIManager instance;
    private static WeakReference<BaseFragment> foregroundFragment;
    public FragmentManager fragmentManager;

    public FragmentUIManager getInstance() {
        if (instance == null)
            instance = new FragmentUIManager();
        return instance;
    }

    // Assigning the current running fragment to a var.
    public void setFragment(BaseFragment fragment) {
        foregroundFragment = new WeakReference<>(fragment);
    }

    // Getting the current running fragment.
    public WeakReference<BaseFragment> getForegroundFragment() {
        return foregroundFragment;
    }

    // Calling updateUI on the current running fragment.
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
