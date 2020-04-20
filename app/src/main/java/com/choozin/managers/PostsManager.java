package com.choozin.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.PostItem;
import com.choozin.utils.BitmapManipulation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsManager extends BaseManager {

    private static PostsManager instance = null;
    public ArrayList<PostItem> profilePosts;
    public boolean firstLoadProfilePosts = true;

    public static PostsManager getInstance() {
        if (instance == null) {
            instance = new PostsManager();
        }
        return instance;
    }

    public void firstLoadProfilePostsToFalse() {
        firstLoadProfilePosts = false;
    }

    public void createPost(Bitmap right, Bitmap left, String title) {
        String limage = BitmapManipulation.BitMapToString(left);
        String rimage = BitmapManipulation.BitMapToString(right);
        Log.v("Avassd", rimage);
        PostItem post = new PostItem(title, rimage, limage);
        String postJson = gson.toJson(post);
        Log.v("fa", postJson);
        RequestBody requestBody = RequestBody.create(JSON, postJson);

        Request request = createRequestBuilder("posts", "post", requestBody).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.i("fbfabafvafas", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getProfilePosts(String id, int alreadyPassed) {
        Request request = createRequestBuilder("posts/profile/" + id, "get", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).header("alreadyPassed", String.valueOf(alreadyPassed)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("e", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    ArrayList<PostItem> postItemArrayList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.body().string());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject entry = jsonArray.getJSONObject(i);
                        postItemArrayList.add(gson.fromJson(entry.toString(), PostItem.class));
                    }
                    profilePosts = postItemArrayList;
                    FragmentUIManager.getInstance().dispatchUpdateUI();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
