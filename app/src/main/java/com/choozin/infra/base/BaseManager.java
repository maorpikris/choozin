package com.choozin.infra.base;

import android.content.SharedPreferences;
import android.util.Patterns;

import com.choozin.ChoozinApplication;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class BaseManager {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static Gson gson = new Gson();
    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static String baseUrl = "https://choozin.herokuapp.com/";

    public BaseManager() {
    }

    public static Request.Builder createRequestBuilder(String url, String o, RequestBody requestBody) {
        switch (o) {
            case "post":
                return new Request.Builder().url(baseUrl + url).post(requestBody);
            case "put":
                return new Request.Builder().url(baseUrl + url).put(requestBody);
            case "get":
                return new Request.Builder().url(baseUrl + url).get();
            case "delete":
                return new Request.Builder().url(baseUrl + url).delete();
            default:
                return null;
        }
    }

    protected SharedPreferences getSharedPrefs() {
        return ChoozinApplication.getAppContext().getSharedPreferences("main", 0);
    }

    protected boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
