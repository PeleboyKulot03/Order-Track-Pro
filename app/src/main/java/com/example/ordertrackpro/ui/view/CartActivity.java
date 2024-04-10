package com.example.ordertrackpro.ui.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.ICartActivity;
import com.example.ordertrackpro.utils.CartModel;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements ICartActivity {
    private RecyclerView recyclerView;
    private TextView total;
    private double totalSum = 0.0;
    private CartModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button cancelOrder = findViewById(R.id.cancelOrder);
        Button checkOut = findViewById(R.id.checkOut);

        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.recyclerView);
        model = new CartModel();
        model.getOrders(this, CartActivity.this);
        cancelOrder.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CartActivity.this);
            alertDialogBuilder.setTitle("Cancellation Caution");
            alertDialogBuilder.setMessage("Are you sure that you want to cancel this order");
            alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                model.deleteAll(CartActivity.this);
                dialog.dismiss();
            });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            alertDialogBuilder.create().show();
        });


        // TODO: CREATE A PRINTABLE FILE USING CANVAS AND PDF DOCUMENT
        checkOut.setOnClickListener(v -> {

        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });
    }

    @Override
    public void getOrders(ArrayList<CartModel> models) {
        CartActivityAdapter adapter = new CartActivityAdapter(models, CartActivity.this, model, CartActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        totalSum = 0;
        for (CartModel model : models) {
            totalSum += model.getTotal();
        }
        String totalSumText = "Total: ₱ " + totalSum;
        total.setText(totalSumText);
    }
}