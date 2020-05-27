package com.choozin.infra

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.managers.AuthenticationManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val authManager: AuthenticationManager = AuthenticationManager.getInstance()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // checking if the user is already logged in.
        authManager.isLoggedIn()
    }

    fun loginButtonClicked(view: View) {
        // if login button clicked checking the validation of the fields, and calling the login method from the authmanager in a secondary thread.
        if (checkValidation(email_login.text.toString(), password_login.text.toString())) {
            postOnSecondary(Runnable {
                authManager.logInWithEmailAndPassword(
                    email_login.text.toString(),
                    password_login.text.toString()
                )
            })
        }
    }

    override fun updateUI() {
        // Checking the state of the screen in the manager and updating the ui according to it.
        postOnUI(Runnable {
            when (authManager.loginScreenState) {
                AuthenticationManager.LoginScreenState.INIT -> {
                    // if state is init setting the loading to invisible.
                    loading.visibility = View.INVISIBLE
                }
                AuthenticationManager.LoginScreenState.LOADING -> {
                    // if sate is loading setting the loading to visible.
                    loading.visibility = View.VISIBLE
                }
                AuthenticationManager.LoginScreenState.AUTH -> {
                    // if user is authenticated opening the main activity.
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                // the other two showing messages according to the error.
                AuthenticationManager.LoginScreenState.FAILED_AUTH -> {
                    authManager.setBackToInit()
                    showToast("Email or password incorrect.")
                }
                AuthenticationManager.LoginScreenState.FAILED_CONNECT -> {
                    authManager.setBackToInit()
                    showToast("Failed to connect.")
                }
            }

        })
    }

    private fun checkValidation(email: String, password: String): Boolean {
        var result = true
        // checking the validation of the fields.
        authManager.validateLoginFields(email, password)
        when (authManager.emailState.isValid) {
            false -> {
                email_login.error = authManager.emailState.message
                email_login.requestFocus()
                result = false
            }
        }
        when (authManager.passwordState.isValid) {
            false -> {
                password_login.error = authManager.passwordState.message
                password_login.requestFocus()
                result = false
            }
        }
        return result
    }

    // if the user clicks the signup button it opwns the register activity.
    fun signUpButtonClicked(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}