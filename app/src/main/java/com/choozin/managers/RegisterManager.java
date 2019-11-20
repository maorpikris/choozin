package com.choozin.managers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.choozin.managers.base.BaseManager;
import com.choozin.utils.FieldValidationState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterManager extends BaseManager {
    private static RegisterManager instance = null;
    public static RegisterManager getInstance() {
        if(instance == null) {
            instance = new RegisterManager();
        }
        return instance;
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private AuthenticationManager authenticationManager = AuthenticationManager.getInstance();
    public FieldValidationState usernameState;
    public FieldValidationState emailState;
    public FieldValidationState passwordState;

    public void signUp(Task<AuthResult> task, Context context) {
        if(task.isSuccessful()) {
            authenticationManager.user = task.getResult().getUser();
            //FirebaseFirestore.getInstance().collection("Users").document(authenticationManager.user.getUid()).set();
        }else {
            Log.v("dab", task.getException().toString());
        }
    }

    public void validateFields(String email, String password) {
            if(email.equals("")) {
                emailState = new FieldValidationState(false, "Please enter an email");
            } else if(!isEmailValid(email)) {
                emailState = new FieldValidationState(false, "Email is unvalid");
            } else {
                emailState = new FieldValidationState(true, "");
            }
            if(password.equals("")) {
                passwordState = new FieldValidationState(false, "Please enter a password");
            } else if(password.length() < 6) {
                passwordState = new FieldValidationState(false, "Password is unvalid");
            } else {
                passwordState = new FieldValidationState(true, "");
            }
    }

    private void checkIfUsernameIsTaken(String username) {
        getmUsersList();
    }
}
