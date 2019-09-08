package com.choozin.managers.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import javax.annotation.Nullable;


public class BaseManager {
    public BaseManager() { }

    private List<String> mUsernamesList;

    protected CollectionReference mUsers = FirebaseFirestore.
            getInstance().collection("Users");

    protected SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("main", 0);
    }

    protected List<String> getmUsersList() {
        FirebaseFirestore.getInstance().collection("Lists").document("usernamelist").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    Log.i("dab", task.getResult().get("list").toString());
                }else{
                    Log.i("dab", "scas");
                }
            }
        });

        return mUsernamesList;
    }

    protected boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
