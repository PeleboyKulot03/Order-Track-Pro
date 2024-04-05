package com.example.ordertrackpro.utils;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.ui.controller.ILoginPage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginModel {
    private FirebaseAuth auth;
    private final ILoginPage iLoginPage;
    public LoginModel(ILoginPage iLoginPage) {
        auth = FirebaseAuth.getInstance();
        this.iLoginPage = iLoginPage;
    }
    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(task -> iLoginPage.onLogin(true, "Login Success!")).addOnFailureListener(e -> iLoginPage.onLogin(false, e.getMessage()));

//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot user: snapshot.getChildren()) {
//                    if (Objects.equals(user.child("informations").child("username").getValue(String.class), username)) {
//                        String email = user.child("informations").child("email").getValue(String.class);
//                        if (email != null) {
//                        }
//                        return;
//                    }
//                }
//                iLoginPage.onLogin(false, "Username or Password is incorrect!");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                iLoginPage.onLogin(false, error.getMessage());
//            }
//        });
    }
}
