package com.choozin.infra.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import com.choozin.infra.base.BaseActivity;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class BaseManager {
    public BaseManager() { }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    protected Gson gson = new Gson();
    protected OkHttpClient okHttpClient = new OkHttpClient();
    private String baseUrl = "http://192.168.1.32:8080/";

    protected Request.Builder createRequestBuilder(String url, String o, RequestBody requestBody) {
        switch (o) {
            case "post":
                return new Request.Builder().url(baseUrl + url).post(requestBody);
            case "put":
                return new Request.Builder().url(baseUrl + url).put(requestBody);
            default:
                return null;
        }
    }

    protected void dispatchUpdateUI(){
        UIManager.getInstance().dispatchUpdateUI();
    }
    protected SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("main", 0);
    }

    protected boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}