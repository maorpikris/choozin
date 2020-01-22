package com.choozin.managers;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.UIManager;
import com.choozin.models.User;
import com.choozin.utils.FieldValidationState;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AuthenticationManager extends BaseManager {

    public FieldValidationState emailState;

    private static AuthenticationManager instance = null;
    public static AuthenticationManager getInstance() {
        if(instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public FieldValidationState passwordState;
    public LoginScreenState loginScreenState;
    private String token = null;

    public boolean isLoggedIn()
    {
        return getSharedPrefs().getString("token", null) != null;
    }

    public void logInWithEmailAndPassword(String email, String password) {
        loginScreenState = AuthenticationManager.LoginScreenState.LOADING;
        UIManager.getInstance().dispatchUpdateUI();
        User user = new User(email, password);
        String userJson = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(JSON, userJson);
        Request request = createRequestBuilder("auth/login", "post", requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                loginScreenState = LoginScreenState.FAILED_CONNECT;
                UIManager.getInstance().dispatchUpdateUI();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // TODO: Save token to sharedPrefs
                    loginScreenState = LoginScreenState.AUTH;
                    UIManager.getInstance().dispatchUpdateUI();
                    return;
                }
                loginScreenState = LoginScreenState.FAILED_AUTH;
                UIManager.getInstance().dispatchUpdateUI();
            }
        });
    }

    public void setBackToInit() {
        loginScreenState = LoginScreenState.INIT;
        UIManager.getInstance().dispatchUpdateUI();
    }

    public void validateLoginFields(String email, String password) {
        if (email.equals("")) {
            emailState = new FieldValidationState(false, "Please enter an email");
        } else if (!isEmailValid(email)) {
            emailState = new FieldValidationState(false, "Email is unvalid");
        } else {
            emailState = new FieldValidationState(true, "");
        }
        if (password.equals("")) {
            passwordState = new FieldValidationState(false, "Please enter a password");
        } else if (password.length() < 6) {
            passwordState = new FieldValidationState(false, "Password is unvalid");
        } else {
            passwordState = new FieldValidationState(true, "");
        }
    }

    public void setToken(String token) {
        getSharedPrefs().edit().putString("token", token);
    }

    public enum LoginScreenState {
        INIT,
        LOADING,
        AUTH,
        FAILED_AUTH,
        FAILED_CONNECT
    }
}
