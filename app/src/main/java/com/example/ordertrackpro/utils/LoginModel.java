package com.example.ordertrackpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.ILoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class LoginModel {
    private FirebaseAuth auth;
    private final ILoginPage iLoginPage;
    private final Activity activity;
    public LoginModel(ILoginPage iLoginPage, Activity activity) {
        auth = FirebaseAuth.getInstance();
        this.iLoginPage = iLoginPage;
        this.activity = activity;
    }
    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(task -> {
            SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Log.i("TAGELE", "signIn: " + task.getUser().getDisplayName());
            editor.putString(activity.getString(R.string.get_user), Objects.requireNonNull(task.getUser()).getDisplayName());
            editor.putString(activity.getString(R.string.get_photo), Objects.requireNonNull(task.getUser().getPhotoUrl().toString()));
            editor.apply();
            iLoginPage.onLogin(true, "Login Successfully");
        }).addOnFailureListener(e -> iLoginPage.onLogin(false, e.getMessage()));

    }
}
