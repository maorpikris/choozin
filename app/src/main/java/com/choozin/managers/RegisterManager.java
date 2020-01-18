package com.choozin.managers;

import android.util.Log;

import com.choozin.infra.RegisterActivity;
import com.choozin.managers.base.BaseManager;
import com.choozin.models.User;
import com.choozin.utils.FieldValidationState;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterManager extends BaseManager {
    private static RegisterManager instance = null;
    public RegisterState registerState = RegisterState.INIT;

    public static RegisterManager getInstance() {
        if(instance == null) {
            instance = new RegisterManager();

        }
        return instance;
    }

    private AuthenticationManager authenticationManager = AuthenticationManager.getInstance();
    public FieldValidationState usernameState;
    public FieldValidationState emailState;
    public FieldValidationState passwordState;

    public void signUp(String email, String password, String username, RegisterActivity activity) {
        registerState = RegisterState.LOADING;
        activity.upadteUI();
        User user = new User(email, password, username);
        String userJson = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(JSON, userJson);
        Request request = createRequestBuilder("auth/signup", "put", requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                registerState = RegisterState.UNVALID;
                activity.upadteUI();
                Log.v("dab", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (gson.fromJson(response.body().string(), JsonObject.class).has("message")) {
                    registerState = RegisterState.UNVALID;
                    activity.upadteUI();
                    return;
                }
                Log.v("dab", response.body().string());
                registerState = RegisterState.VALID;
                activity.upadteUI();

            }
        });
    }

    public void setBackToInit(RegisterActivity activity) {

        registerState = RegisterState.INIT;
        activity.upadteUI();

    }

    public enum RegisterState {
        INIT,
        LOADING,
        VALID,
        UNVALID
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

}
