package com.choozin.managers;


import com.choozin.infra.RegisterActivity;
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


public class RegisterManager extends BaseManager {
    private static RegisterManager instance = null;
    public RegisterState registerState = RegisterState.INIT;

    public static RegisterManager getInstance() {
        if(instance == null) {
            instance = new RegisterManager();

        }
        return instance;
    }

    public FieldValidationState usernameState;
    public FieldValidationState emailState;
    public FieldValidationState passwordState;

    public void signUp(String email, String password, String username, RegisterActivity activity) {
        registerState = RegisterState.LOADING;
        UIManager.getInstance().dispatchUpdateUI();
        User user = new User(email, password, username);
        String userJson = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(JSON, userJson);
        Request request = createRequestBuilder("auth/signup", "put", requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                registerState = RegisterState.FAILED_CONNECTION;
                UIManager.getInstance().dispatchUpdateUI();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body().string().contains("User created")) {
                    registerState = RegisterState.VALID;
                    UIManager.getInstance().dispatchUpdateUI();
                    return;
                }
                registerState = RegisterState.UNVALID;
                UIManager.getInstance().dispatchUpdateUI();
                //TODO: Check whether email, username or both are taken.
            }
        });
    }

    public void setBackToInit() {
        registerState = RegisterState.INIT;
        UIManager.getInstance().dispatchUpdateUI();
    }

    public void validateFields(String email, String password, String username) {
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
        if (username.equals("")) {
            usernameState = new FieldValidationState(false, "Please enter a username");
        } else {
            usernameState = new FieldValidationState(true, "");
        }
    }

    public enum RegisterState {
        INIT,
        LOADING,
        VALID,
        UNVALID,
        FAILED_CONNECTION
    }
}
