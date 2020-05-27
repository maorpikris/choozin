package com.choozin.managers;

import android.util.Log;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;
import com.choozin.models.User;
import com.choozin.ui.fragments.ProfileFragment;

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
    public static User currentProfileUser = new User();
    public ArrayList<PostItem> profilePosts = new ArrayList<>();
    public String idCurrentProfileUser;


    public ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    // Getting the profile posts
    public void getProfilePosts(String id) {
        // Asking the server for the profile posts of a user.
        Request request = createRequestBuilder("posts/profile/" + idCurrentProfileUser, "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
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
                        // If response if successful turning all the json to PostItem.
                        ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                        JSONObject root = new JSONObject(response.body().string());
                        JSONArray jsonArray = root.getJSONArray("posts");
                        // setting the currentprofileuser to the creator of the post.
                        currentProfileUser = gson.fromJson(root.getJSONObject("creator").toString(), User.class);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                        }
                        // Setting the old post list to the new one that contains the json data.
                        profilePosts = postItemArrayList;
                        // Updating UI if the current fragment is profile fragment.
                        if (new FragmentUIManager().getInstance().getForegroundFragment().get() instanceof ProfileFragment) {
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
