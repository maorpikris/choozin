package com.choozin.managers;

import com.choozin.infra.base.BaseManager;
import com.choozin.infra.base.FragmentUIManager;
import com.choozin.models.User;
import com.choozin.ui.fragments.SearchFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SearchManager extends BaseManager {

    private static SearchManager instance = null;
    public ArrayList<User> users = new ArrayList<>();

    public SearchManager getInstance() {
        if (instance == null) {
            instance = new SearchManager();
        }
        return instance;
    }

    // Searching users by word
    public void getSearchUsers(String searchWord) {
        // Asking the server for a users that contains the word in the search field.
        Request request = createRequestBuilder("users/search", "get", null).build()
                .newBuilder().header("Authorization", AuthenticationManager.getInstance().currentUserToken)
                .header("searchWord", searchWord)
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
                    // Turning all the json data to User model.
                    try {
                        ArrayList<User> usersArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject entry = jsonArray.getJSONObject(i);
                            usersArrayList.add(gson.fromJson(entry.toString(), User.class));
                        }
                        // Setting the users list to the new list that contains the json data.
                        users = usersArrayList;
                        // Updating ui if the current fragment is the search fragment.
                        if (new FragmentUIManager().getInstance().getForegroundFragment().get() instanceof SearchFragment) {
                            new FragmentUIManager().getInstance().dispatchUpdateUI();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
