package com.choozin.managers;

import android.content.Context;

import com.choozin.managers.base.BaseManager;


public class AuthenticationManager extends BaseManager {

    private static AuthenticationManager instance = null;
    public static AuthenticationManager getInstance() {
        if(instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public boolean isLoggedIn(Context context)
    {
        return getSharedPrefs(context).getString("token", null) != null;
    }


//    public void logInWithEmailAndPassword(Task<AuthResult> task, Context context){
//        if(task.isSuccessful()) {
//            user = mAuth.getCurrentUser();
//            loginState = haveFinishedRegister(context) ?
//                    LoginState.LOGGED_IN :
//                    LoginState.NOT_FINISHED_REGISTER;
//
//        } else {
//            loginState = LoginState.LOG_IN_FAILED;
//        }
//    }
//
//    public boolean haveFinishedRegister(Context context) {
//        return getSharedPrefs(context).getBoolean(user.getUid() + "finished register", false);
//    }


//    public void validateLoginFields(String email, String password) {
//        if(email.equals("")) {
//            emailState = new FieldValidationState(false, "Please enter an email");
//        } else if(!isEmailValid(email)) {
//            emailState = new FieldValidationState(false, "Email is unvalid");
//        } else {
//            emailState = new FieldValidationState(true, "");
//        }
//        if(password.equals("")) {
//            passwordState = new FieldValidationState(false, "Please enter a password");
//        } else if(password.length() < 6) {
//            passwordState = new FieldValidationState(false, "Password is unvalid");
//        } else {
//            passwordState = new FieldValidationState(true, "");
//        }
//    }
}
