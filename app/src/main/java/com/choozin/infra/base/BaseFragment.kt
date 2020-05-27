package com.choozin.infra.base

import androidx.fragment.app.Fragment
import com.choozin.managers.ThreadsManager

open class BaseFragment : Fragment() {
    open fun updateUI() {}

    private var fragmentTag = ""
    // Setting up functions that can be accessed by all the fragments that exports BaseFragment

    fun postOnUI(runnable: Runnable) {
        ThreadsManager.getInstance().getDefaultHandler("main").post(runnable)
    }

    fun postOnSecondary(runnable: () -> Unit) {
        ThreadsManager.getInstance().getDefaultHandler("secondary").post(runnable)
    }
}