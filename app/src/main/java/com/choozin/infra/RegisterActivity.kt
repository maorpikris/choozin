package com.choozin.infra

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.RegisterManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    val authManager : AuthenticationManager = AuthenticationManager.getInstance()
    val registerManager : RegisterManager = RegisterManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.upadteUI()
    }

    fun backButtonClicked(view : View) {
        super.onBackPressed()
    }

    fun registerButtonClicked(view: View) {
        if(checkValidation()) {
            Thread {
                registerManager.signUp(
                    emailField.text.toString(),
                    passwordField.text.toString(),
                    usernameField.text.toString(),
                    this
                )
            }
        }
    }

    fun upadteUI() {
        postOnUI(Runnable {
            when (registerManager.registerState) {
                RegisterManager.RegisterState.INIT -> {
                    registerProgressBar.visibility = View.INVISIBLE
                }
                RegisterManager.RegisterState.LOADING -> {
                    registerProgressBar.visibility = View.VISIBLE
                }
                RegisterManager.RegisterState.VALID -> {
                    registerManager.setBackToInit(this)
                    // Go to another screen
                }
                RegisterManager.RegisterState.UNVALID -> {
                    registerManager.setBackToInit(this)
                    Toast.makeText(this, "Email or Username are taken.", Toast.LENGTH_LONG)
                }
            }

        })
    }

    private fun checkValidation() : Boolean {
        var result = true
        registerManager.validateFields(emailField.text.toString(), passwordField.text.toString())
        when(registerManager.emailState.isValid) {
            false -> {
                emailField.error = registerManager.emailState.message
                emailField.requestFocus()
                result = false
            }
        }
        when(registerManager.passwordState.isValid) {
            false -> {
                passwordField.error = registerManager.passwordState.message
                passwordField.requestFocus()
                result = false
            }
        }
        return result
    }

}
