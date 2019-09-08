package com.choozin.infra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.RegisterManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    val authManager : AuthenticationManager = AuthenticationManager.getInstance()
    val registerManager : RegisterManager = RegisterManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun backButtonClicked(view : View) {
        super.onBackPressed()
    }

    fun registerButtonClicked(view: View) {

        if(checkValidation()) {
            Thread {
                mAuth.createUserWithEmailAndPassword(emailField.toString(), passwordField.toString()).addOnCompleteListener {task ->

                }
            }

        }
    }

    private fun checkValidation() : Boolean {
        var result = true;
        registerManager.validateFields(emailField.toString(), passwordField.toString())
        when(registerManager.emailState.isValid) {
            false -> {
                email_login.error = registerManager.emailState.message
                email_login.requestFocus()
                result = false
            }
        }
        when(registerManager.passwordState.isValid) {
            false -> {
                password_login.error = registerManager.passwordState.message
                password_login.requestFocus()
                result = false
            }
        }
        return result
    }

}
