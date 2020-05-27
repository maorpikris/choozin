package com.choozin.managers;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;
import com.choozin.ui.fragments.RandomFragment;

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
    public ArrayList<PostItem> explorePosts = new ArrayList<>();
    public boolean refresh = false;
    public boolean loadMore = false;

    public ExploreManager getInstance() {
        if (instance == null) {
            instance = new ExploreManager();
        }
        return instance;
    }

    //Getting posts for the explore from the server.
    public void getExplorePosts() {
        // calling a request from the server.
        Request request = createRequestBuilder("posts/search/", "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
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
                        // If response if successful turning all the posts from json to PostItem model
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        // if the current state of load more is true adding all the new post to the old list.
                        if (loadMore) {
                            explorePosts.addAll(postItemArrayList);
                        } else {
                            // else clearing the old list and adding all the new posts.
                            explorePosts.clear();
                            explorePosts.addAll(postItemArrayList);
                        }
                        // setting load more back to false if it is true.
                        if (loadMore) {
                            loadMore = false;
                        }
                        // updating UI if the current foreground fragment is RandomFragment.
                        if (new FragmentUIManager().getInstance().getForegroundFragment().get() instanceof RandomFragment) {
                            new FragmentUIManager().getInstance().dispatchUpdateUI();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
