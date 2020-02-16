package com.choozin.managers;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.choozin.infra.base.BaseManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
        ByteArrayOutputStream streamLeft = new ByteArrayOutputStream();
        ByteArrayOutputStream streamRight = new ByteArrayOutputStream();

        left.compress(Bitmap.CompressFormat.JPEG, 100, streamLeft);
        right.compress(Bitmap.CompressFormat.JPEG, 100, streamRight);

        byte[] byteArrayLeft = streamLeft.toByteArray();
        byte[] byteArrayRight = streamRight.toByteArray();

        String encodedImageLeft = Base64.encodeToString(byteArrayLeft, Base64.DEFAULT);
        String encodedImageRight = Base64.encodeToString(byteArrayRight, Base64.DEFAULT);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("images", "left.jpeg", RequestBody.create(MediaType.parse("image/*"), encodedImageLeft))
                .addFormDataPart("images", "right.jpeg", RequestBody.create(MediaType.parse("image/*"), encodedImageRight)).build();

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
}
