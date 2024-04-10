package com.example.ordertrackpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IMenuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
        reference = database.getReference();
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
        reference.child("Products").addValueEventListener(new ValueEventListener() {
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

    public void addToCart(IMenuFragment iMenuFragment, CartModel model, Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String name = sharedPref.getString(activity.getString(R.string.get_user), "Cashier's Name");
        reference.child("Employees").child(name).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(model.getName())) {
                    int qty = snapshot.child(model.getName()).child("qtyNumber").getValue(Integer.class) + model.getQtyNumber();
                    snapshot.child(model.getName()).child("qtyNumber").getRef().setValue(qty);
                    snapshot.child(model.getName()).child("total").getRef().setValue(qty * model.getPrice());
                    return;
                }
                reference.child("Employees").child(name).child("cart").child(model.getName()).setValue(model).addOnSuccessListener(unused -> iMenuFragment.onAddToCart(true, "Adding item to cart successfully!")).addOnFailureListener(e -> iMenuFragment.onAddToCart(false, e.getLocalizedMessage()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iMenuFragment.onAddToCart(false, error.getMessage());
            }
        });
    }

    public void getNumOrders(IMenuFragment iMenuFragment, Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String name = sharedPref.getString(activity.getString(R.string.get_user), "Cashier's Name");
        reference.child("Employees").child(name).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                iMenuFragment.onGetItemCount((int) snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iMenuFragment.onGetItemCount(0);
            }
        });
    }
}
