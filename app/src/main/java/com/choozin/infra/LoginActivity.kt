package com.choozin.infra

import android.os.Bundle
import android.util.Log
import android.view.View
import com.choozin.R
import com.choozin.managers.AuthenticationManager
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.choozin.infra.base.BaseActivity
import com.choozin.utils.LoginState
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent



class LoginActivity : BaseActivity() {

    private val authManager :AuthenticationManager = AuthenticationManager.getInstance()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(authManager.isLoggedIn) {
//            if(authManager.haveFinishedRegister(this)) {
//            } else {
//                val intent = Intent(this, AfterRegisterActivity::class.java)
//                startActivity(intent)
//                this.finish()
//            }
//        } else {
//            setContentView(R.layout.activity_login)
//        }
        setContentView(R.layout.activity_main);

    }

    fun loginButtonClicked(view : View) {
        if (checkValidation(email_login.text.toString(), password_login.text.toString())){
            loading.visibility = View.VISIBLE
            login_button.isClickable = false
            // TODO: create thread manager
            Thread{
                mAuth.signInWithEmailAndPassword(email_login.text.toString(), password_login.text.toString()).addOnCompleteListener{ task ->
                    authManager.logInWithEmailAndPassword(task, context)
                    postOnUI(Runnable {
                        Thread.sleep(2000)
                        loading.visibility = View.GONE
                        login_button.isClickable = true
                        when(authManager.loginState) {
                            LoginState.LOG_IN_FAILED ->{
                            showToast("Email or password are not correct")
                        }
                            LoginState.NOT_FINISHED_REGISTER -> {
                                // TODO: Pop up the after register activity
                                logVerbose("not finished register")
                                val intent = Intent(this, AfterRegisterActivity::class.java)
                                startActivity(intent)
                                this.finish()
                            }
                            LoginState.LOGGED_IN -> {
                                // TODO: Pop up the main activity
                            }
                            else -> {}
                        }
                    })
                }
            }.start()
        }
    }

    private fun checkValidation(email : String, password: String) : Boolean{
        var result = true
        authManager.validateLoginFields(email, password)
        Log.v("dab", authManager.passwordState.message)
        when(authManager.emailState.isValid) {
            false -> {
                email_login.error = authManager.emailState.message
                email_login.requestFocus()
                result = false
            }
        }
        when(authManager.passwordState.isValid) {
            false -> {
                password_login.error = authManager.passwordState.message
                password_login.requestFocus()
                result = false
            }
        }
        return result
    }

    fun signUpButtonClicked(view : View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}