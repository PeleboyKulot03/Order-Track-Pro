package com.example.ordertrackpro.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ordertrackpro.ui.controller.ICartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartModel {
    private String name;
    private int qty;
    private int qtyNumber;
    private double total;
    private double price;
    private DatabaseReference reference;

    public CartModel() {
        reference = FirebaseDatabase.getInstance().getReference("Employees").child("fsahkjfaksjhfsa").child("cart");
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

    public void updateItem(String name, int qty) {
        reference.child(name).child("qtyNumber").setValue(qty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("TAG", "onSuccess: ");
            }
        });
    }
    public void getOrders(ICartActivity iCartActivity) {
        reference.addValueEventListener(new ValueEventListener() {
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

    public void deleteItem(String name) {
        reference.child(name).removeValue();
    }
}
