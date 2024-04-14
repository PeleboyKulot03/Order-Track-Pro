package com.example.ordertrackpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IShowReceipt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowReceiptModel {
    private String name;
    private double price;
    private int qtyNumber;

    private DatabaseReference reference;
    public ShowReceiptModel(String name, double price, int qtyNumber) {
        this.name = name;
        this.price = price;
        this.qtyNumber = qtyNumber;
    }
    public ShowReceiptModel() {
        reference = FirebaseDatabase.getInstance().getReference("Employees");
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQtyNumber() {
        return qtyNumber;
    }

    public void getReceipt(Activity activity, String referenceNum, IShowReceipt iShowReceipt) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.key), Context.MODE_PRIVATE);
        String uid = sharedPref.getString(activity.getString(R.string.get_uid), "");
        reference.child(uid).child("transactions").child(referenceNum).child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShowReceiptModel> models = new ArrayList<>();
                for (DataSnapshot order: snapshot.getChildren()) {
                    models.add(order.getValue(ShowReceiptModel.class));
                }
                reference.getRoot().child("Employees").child(uid).child("transactions").child(referenceNum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Double money = snapshot.child("money").getValue(Double.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String time = snapshot.child("time").getValue(String.class);
                        iShowReceipt.getReceipt(models, money, date, time);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iShowReceipt.getReceipt(null, 0, "", "");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iShowReceipt.getReceipt(null, 0, "", "");
            }
        });
    }
}
