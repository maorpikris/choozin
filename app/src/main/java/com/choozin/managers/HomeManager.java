package com.choozin.managers;

import android.util.Log;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class HomeManager extends BaseManager {
    private static HomeManager instance = null;
    public ArrayList<PostItem> homePosts;
    private boolean pullToRefreshActive = false;

    public HomeManager getInstance() {
        if (instance == null) {
            instance = new HomeManager();
        }
        return instance;
    }

    public void setPullToRefreshActiveTrue() {
        pullToRefreshActive = true;
    }

    public void getHomePosts() {
        String time;
        if (homePosts != null) {
            time = homePosts.size() == 0 || pullToRefreshActive ? "init" : homePosts.get(0).getCreatedAt();
        } else {
            time = "init";
        }
        String size = homePosts == null ? "0" : String.valueOf(homePosts.size());
        if (pullToRefreshActive) {
            pullToRefreshActive = false;
        }

        //TODO why time is undefined in the server.
        Request request = createRequestBuilder("posts/home", "get", null).build()
                .newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken)
                .header("startTime", time)
                .header("amount", size)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            Log.v("shit", entry.toString());
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        homePosts = postItemArrayList;
                        FragmentUIManager.getInstance().dispatchUpdateUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
