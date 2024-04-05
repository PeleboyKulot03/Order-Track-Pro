package com.example.ordertrackpro.utils;

import com.example.ordertrackpro.ui.view.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AccountModel {
    private FirebaseAuth auth;
    public AccountModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void SignOut(){
        auth.signOut();
    }


}
