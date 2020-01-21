package com.choozin.infra.base

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast


open class BaseActivity : Activity() {

    open fun updateUI() {}

    fun postOnUI(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
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