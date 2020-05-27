package com.choozin.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.ui.fragments.CreatePostFragment;
import com.choozin.utils.BitmapManipulation;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsManager extends BaseManager {

    private static PostsManager instance = null;



    public static PostsManager getInstance() {
        if (instance == null) {
            instance = new PostsManager();
        }
        return instance;
    }


    public void createPost(Bitmap right, Bitmap left, String title) {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("images", "r.jpg", RequestBody.create(MediaType.parse("image/*jpg"), BitmapManipulation.BitMapToString(right)))
                .addFormDataPart("images", "l.jpg", RequestBody.create(MediaType.parse("image/*jpg"), BitmapManipulation.BitMapToString(left))).build();

        Request request = createRequestBuilder("posts", "post", requestBody).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                if (response.isSuccessful()) {
                    if (new FragmentUIManager().getInstance().getForegroundFragment().get() instanceof CreatePostFragment) {
                        new FragmentUIManager().getInstance().dispatchUpdateUI();
                    }

                }


            }
        });
    }

    public void removePost(String postId) {
        Request request = createRequestBuilder("posts/" + postId, "delete", null).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                new FragmentUIManager().getInstance().dispatchUpdateUI();
            }
        });
    }

    public void postsActions(String postId, String action) {
        final String jsonAction = "{\"action\":\"" + action + "\"}";
        final RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-16"), jsonAction);
        Request request = createRequestBuilder("posts/" + postId, "put", body).build().newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("e", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.v("Dab", "daf");

                if (response.isSuccessful()) {
                    // TODO think what to do.
                }
            }
        });
    }
}
