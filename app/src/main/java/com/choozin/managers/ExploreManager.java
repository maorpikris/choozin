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

public class ExploreManager extends BaseManager {
    private static ExploreManager instance;
    public ArrayList<PostItem> explorePosts;
    public boolean refresh = false;

    public ExploreManager getInstance() {
        if (instance == null) {
            instance = new ExploreManager();
        }
        return instance;
    }

    public void getExplorePosts() {

        Request request = createRequestBuilder("posts/search/", "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        Log.v("Dab", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            Log.v("shit", entry.toString());
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        if (explorePosts != null && !refresh) {
                            explorePosts.addAll(postItemArrayList);
                            Log.v("shit", String.valueOf(explorePosts.size()));
                        } else {
                            explorePosts = postItemArrayList;
                        }

                        if (refresh) {
                            refresh = false;
                        }
                        FragmentUIManager.getInstance().dispatchUpdateUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
