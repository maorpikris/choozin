package com.choozin.infra.base

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.choozin.managers.ThreadsManager


open class BaseActivity : AppCompatActivity() {

    open fun updateUI() {}
    // Setting up functions that can be accessed by all the activities that exports BaseActivity
    fun postOnUI(runnable: Runnable) {
        ThreadsManager.getInstance().getDefaultHandler("main").post(runnable)
    }

    fun postOnSecondary(runnable: Runnable) {
        ThreadsManager.getInstance().getDefaultHandler("secondary").post(runnable)
    }

    fun showToast(text: CharSequence, duration: Int) {
        Toast.makeText(applicationContext, text, duration).show()
    }

    fun showToast(text: CharSequence) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    fun logVerbose(message: String) {
        Log.v("dab", message)
    }

}