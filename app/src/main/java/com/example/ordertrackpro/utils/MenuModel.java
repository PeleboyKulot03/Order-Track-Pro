package com.example.ordertrackpro.utils;

import android.view.Menu;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.ui.controller.IMenuFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuModel {
    private String name, imageUrl;
    private double price;
    private int qty;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    public MenuModel(String imageUrl, String name, double price, int qty) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
    public MenuModel() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public void getProducts(IMenuFragment iMenuFragment) {
        ArrayList<ArrayList<MenuModel>> models = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot meal: snapshot.getChildren()) {
                    ArrayList<MenuModel> tempModel = new ArrayList<>();
                    for (DataSnapshot product: meal.getChildren()) {
                        MenuModel model = product.getValue(MenuModel.class);
                        tempModel.add(model);
                    }
                    models.add(tempModel);
                }
                iMenuFragment.getProducts(models);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iMenuFragment.getProducts(null);
            }
        });

    }
}
