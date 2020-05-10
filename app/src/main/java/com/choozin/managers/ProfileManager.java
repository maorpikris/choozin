package com.choozin.managers;

import android.util.Log;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;
import com.choozin.models.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileManager extends BaseManager {
    private static ProfileManager instance = null;
    public ArrayList<PostItem> profilePosts;
    public User currentProfileUser;
    public String idCurrentProfileUser;


    public ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public void getProfilePosts(String id) {
        Request request = createRequestBuilder("posts/profile/" + idCurrentProfileUser, "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("e", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONObject root = new JSONObject(response.body().string());
                        Log.v("a", root.toString());
                        JSONArray jsonArray = root.getJSONArray("posts");
                        currentProfileUser = gson.fromJson(root.getJSONObject("creator").toString(), User.class);
                        Log.v("Dab", currentProfileUser.get_id());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            Log.v("shit", entry.toString());
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        profilePosts = postItemArrayList;
                        FragmentUIManager.getInstance().dispatchUpdateUI();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}
