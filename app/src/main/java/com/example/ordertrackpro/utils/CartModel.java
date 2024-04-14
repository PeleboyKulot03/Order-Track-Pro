package com.example.ordertrackpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.ICartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CartModel {
    private String name;
    private int qty;
    private int qtyNumber;
    private double total;
    private double price;
    private DatabaseReference reference;

    public CartModel() {
        reference = FirebaseDatabase.getInstance().getReference("Employees");
    }

    public CartModel(DatabaseReference reference) {
        this.reference = reference;
    }

    public CartModel(String name, int qty, int qtyNumber, double total, double price) {
        this.name = name;
        this.qty = qty;
        this.qtyNumber = qtyNumber;
        this.total = total;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public int getQtyNumber() {
        return qtyNumber;
    }

    public double getTotal() {
        return total;
    }

    public double getPrice() {
        return price;
    }

    public void updateItem(String name, int qty, double total, Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("cart").child(name).child("qtyNumber").setValue(qty);
        reference.child(uid).child("cart").child(name).child("total").setValue(total);
    }
    public void getOrders(ICartActivity iCartActivity, Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CartModel> models = new ArrayList<>();
                for (DataSnapshot order: snapshot.getChildren()) {
                    CartModel model = order.getValue(CartModel.class);
                    models.add(model);
                }
                iCartActivity.getOrders(models);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iCartActivity.getOrders(null);
            }
        });
    }

    public void deleteItem(String name, Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("cart").child(name).removeValue();
    }
    public void deleteAll(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("cart").removeValue();
    }

    public void pay(String referenceNum, Activity activity, HashMap<String, CartModel> models, double total, double money, ICartActivity iCartActivity) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(" HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dateText = formatter.format(date);
        String timeText = timeFormatter.format(date);

        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("transactions").child(referenceNum).child("orders").setValue(models)
                .addOnSuccessListener(unused -> reference.child(uid).child("transactions").child(referenceNum).child("money").setValue(money)
                        .addOnSuccessListener(unused14 -> reference.child(uid).child("transactions").child(referenceNum).child("date").setValue(dateText)
                                .addOnSuccessListener(unused13 -> reference.child(uid).child("transactions").child(referenceNum).child("time").setValue(timeText)
                                        .addOnSuccessListener(unused12 -> reference.child(uid).child("cart").removeValue()
                                                .addOnSuccessListener(unused1 -> iCartActivity.onSuccess(true, "Complete!"))
                                                .addOnFailureListener(e -> iCartActivity.onSuccess(false, e.getLocalizedMessage())))
                                        .addOnFailureListener(e -> iCartActivity.onSuccess(false, e.getLocalizedMessage())))
                                .addOnFailureListener(e -> iCartActivity.onSuccess(false, e.getLocalizedMessage())))
                        .addOnFailureListener(e -> iCartActivity.onSuccess(false, e.getLocalizedMessage())))
                .addOnFailureListener(e -> iCartActivity.onSuccess(false, e.getLocalizedMessage()));
    }
}
