package com.choozin.managers;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;
import com.choozin.ui.fragments.HomeFragment;

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
    public ArrayList<PostItem> homePosts = new ArrayList<>();
    private boolean pullToRefreshActive = false;

    public HomeManager getInstance() {
        if (instance == null) {
            instance = new HomeManager();
        }
        return instance;
    }

    // Setting the state of pull to refresh to true;
    public void setPullToRefreshActiveTrue() {
        pullToRefreshActive = true;
    }

    // Getting the home posts from the server.
    public void getHomePosts() {
        String time;
        // if there are no posts setting the time to init, else setting the time to the createdat of the first post.
        if (homePosts != null) {
            time = homePosts.size() == 0 || pullToRefreshActive ? "init" : homePosts.get(0).getCreatedAt();
        } else {
            time = "init";
        }
        // getting the size of the posts list.
        String size = homePosts == null ? "0" : String.valueOf(homePosts.size());
        if (pullToRefreshActive) {
            pullToRefreshActive = false;
        }

        // calling a request to get the home posts from the server.
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
                    // if success, turning all the json posts to PostItem model.
                    try {
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        // Setting the old list to the new list that contains the posts from the json response.
                        homePosts = postItemArrayList;
                        // Updating UI if the current fragment is the HomeFragment.
                        if (new FragmentUIManager().getInstance().getForegroundFragment().get() instanceof HomeFragment) {
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
