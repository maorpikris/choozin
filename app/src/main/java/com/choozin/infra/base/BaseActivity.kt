package com.choozin.infra.base

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.choozin.managers.ThreadsManager


open class BaseActivity : Activity() {

    open fun updateUI() {}

    fun postOnUI(runnable: Runnable) {
        ThreadsManager.getInstance().getDefaultHandler("main").post(runnable)
    }

    fun postOnSecondary(runnable: Runnable) {
        ThreadsManager.getInstance().getDefaultHandler("secondary").post(runnable)
    }

    fun showToast(text : CharSequence, duration:Int) {
        Toast.makeText(applicationContext, text, duration).show()
    }
    fun showToast(text : CharSequence) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    fun logVerbose(message: String) {
        Log.v("dab", message)
    }

}