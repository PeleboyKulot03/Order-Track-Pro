package com.example.ordertrackpro.utils;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.ordertrackpro.ui.controller.IEditItem;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class EditItemModel {
    private String name, id, imageUrl;
    private double price;
    private int qty;
    private DatabaseReference reference;
    private StorageReference storageReference;

    public EditItemModel(String imageUrl, String name, double price, int qty, String id) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EditItemModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void updateItem(EditItemModel model, IEditItem iEditItem, String classification, Uri uri) {
        if (uri == null) {
            reference.child(classification).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot meal: snapshot.getChildren()) {
                        if (Objects.equals(meal.child("id").getValue(String.class), model.getId())) {
                            meal.getRef().setValue(model).addOnSuccessListener(unused -> iEditItem.onUpdateItem(true, "Updating complete!")).addOnFailureListener(e -> iEditItem.onUpdateItem(false, e.getLocalizedMessage()));
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    iEditItem.onUpdateItem(false, error.getMessage());
                }
            });
            return;
        }
        storageReference.child("product_images/" + classification + "/" + model.getId() + ".jpg").delete().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                storageReference = storageReference.child("product_images/" + classification).child(model.getId() + ".jpg");
                UploadTask uploadTask = storageReference.putFile(uri);
                Task<Uri> urlTask = uploadTask.continueWithTask(task1 -> {
                    if (!task1.isSuccessful()) {
                        throw Objects.requireNonNull(task1.getException());
                    }

                    return storageReference.getDownloadUrl();
                }).addOnCompleteListener(task12 -> {
                    if (task12.isSuccessful()) {
                        Uri downloadUri = task12.getResult();
                        model.setImageUrl(downloadUri.toString());
                        reference.child(classification).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot meal: snapshot.getChildren()) {
                                    if (Objects.equals(meal.child("id").getValue(String.class), model.getId())) {
                                        meal.getRef().setValue(model).addOnSuccessListener(unused -> iEditItem.onUpdateItem(true, "Updating complete!")).addOnFailureListener(e -> iEditItem.onUpdateItem(false, e.getLocalizedMessage()));
                                        return;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                iEditItem.onUpdateItem(false, error.getMessage());
                            }
                        });
                    } else {
                        Log.i("TAGELE", "updateItem: failed");
                        // Handle failures
                        // ...
                    }
                });
            }
            if (task.isCanceled()) {
                iEditItem.onUpdateItem(false, "Updating Cancelled");
            }
        }).addOnFailureListener(e -> iEditItem.onUpdateItem(false, e.getLocalizedMessage()));
    }
}
