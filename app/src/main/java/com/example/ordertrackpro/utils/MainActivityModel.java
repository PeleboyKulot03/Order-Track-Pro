package com.example.ordertrackpro.utils;

import com.example.ordertrackpro.ui.controller.IMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityModel {
    private final FirebaseUser user;
    private final IMainActivity iMainActivity;
    public MainActivityModel(IMainActivity iMainActivity) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.iMainActivity = iMainActivity;
    }

    public void isSignedIn() {
        if (user == null) {
            iMainActivity.isSignedIn(false);
            return;
        }
        iMainActivity.isSignedIn(true);
    }
}
