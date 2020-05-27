package com.choozin.managers;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.UIManager;
import com.choozin.models.User;
import com.choozin.utils.FieldValidationState;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AuthenticationManager extends BaseManager {

    private static AuthenticationManager instance = null;
    public String currentUserToken = null;
    public User currentUser;

    public FieldValidationState emailState;
    public FieldValidationState passwordState;
    public LoginScreenState loginScreenState;

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    // Creating glide requests with authentication header.
    public static GlideUrl buildGlideUrlWithAuth(String url) {
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", AuthenticationManager.getInstance().currentUserToken)
                .build());
    }

    public void isLoggedIn() {
        // checking if token exists in the shared prefs.
        if (getSharedPrefs().getString("token", null) != null) {
            currentUserToken = getSharedPrefs().getString("token", null);
            // calling a request that should respond with whether the token in valid or not.
            Request request = createRequestBuilder("auth/userfromtoken", "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // If not valid meaning there is no connection so setting the state to init.
                    loginScreenState = LoginScreenState.INIT;
                    UIManager.getInstance().dispatchUpdateUI();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            // If response is successful setting the current user to the user from the response and setting the state to auth.
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            currentUser = gson.fromJson(jsonObject.get("user").toString(), User.class);
                            loginScreenState = LoginScreenState.AUTH;

                        } catch (JSONException e) {
                            loginScreenState = LoginScreenState.INIT;
                            e.printStackTrace();
                        }
                        UIManager.getInstance().dispatchUpdateUI();
                    }
                }
            });

        } else {
            loginScreenState = LoginScreenState.INIT;
            UIManager.getInstance().dispatchUpdateUI();
        }

    }

    // Setting the state back to init.
    public void setBackToInit() {
        loginScreenState = LoginScreenState.INIT;
    }

    // Validating the state of the fields.
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

    // Saving the new token in the shared prefs.
    private void setToken(String token) {
        getSharedPrefs().edit().putString("token", token).apply();
        currentUserToken = token;
    }

    // Removing the token = Clearing the token from the shared prefs.
    public void clearToken() {
        getSharedPrefs().edit().putString("token", null).apply();
        currentUserToken = null;
    }

    // function the performs a login with calling to the server and receiving response.
    public void logInWithEmailAndPassword(String email, String password) {
        // Setting the current state to loading and calling update ui
        loginScreenState = AuthenticationManager.LoginScreenState.LOADING;
        UIManager.getInstance().dispatchUpdateUI();
        // Creating new request to the server with the user on the body of the request.
        User user = new User(email, password);
        String userJson = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(JSON, userJson);
        Request request = createRequestBuilder("auth/login", "post", requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // if response failed updating the state to failed connection and update ui.
                loginScreenState = LoginScreenState.FAILED_CONNECT;
                UIManager.getInstance().dispatchUpdateUI();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        // Setting the token to the new token from the response.
                        setToken(jsonObject.getString("token"));
                        // Setting the current user to the user from the response
                        currentUser = gson.fromJson(jsonObject.get("user").toString(), User.class);
                        // Setting the current state to auth and updating ui.
                        loginScreenState = LoginScreenState.AUTH;
                        UIManager.getInstance().dispatchUpdateUI();
                        return;
                    } catch (JSONException e) {
                        loginScreenState = LoginScreenState.FAILED_CONNECT;
                        UIManager.getInstance().dispatchUpdateUI();
                        e.printStackTrace();
                    }

                }

                // If response has message meaning it failed setting the state to some failed state and updating ui.
                if (response.message().equalsIgnoreCase("Service Unavailable")) {
                    loginScreenState = LoginScreenState.FAILED_CONNECT;
                } else {
                    loginScreenState = LoginScreenState.FAILED_AUTH;
                }

                UIManager.getInstance().dispatchUpdateUI();
            }
        });
    }

    // enums for the current state of the screen.
    public enum LoginScreenState {
        INIT,
        LOADING,
        AUTH,
        FAILED_AUTH,
        FAILED_CONNECT
    }
}
