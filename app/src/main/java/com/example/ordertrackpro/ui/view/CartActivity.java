package com.example.ordertrackpro.ui.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.ordertrackpro.ui.customViews.AlertNotice;
import com.example.ordertrackpro.ui.customViews.CompletePayment;
import com.example.ordertrackpro.utils.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements ICartActivity {
    private RecyclerView recyclerView;
    private TextView total;
    private double totalSum = 0.0;
    private CartModel model;
    private LinearLayout totalSection;
    private RelativeLayout emptyStateHolder;
    private ArrayList<CartModel> orders;
    private String reference = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        orders = new ArrayList<>();
        Button cancelOrder = findViewById(R.id.cancelOrder);
        Button checkOut = findViewById(R.id.checkOut);
        totalSection = findViewById(R.id.totalSection);
        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.recyclerView);
        emptyStateHolder = findViewById(R.id.emptyStateHolder);
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

        checkOut.setOnClickListener(v -> {
            CompletePayment completePayment = new CompletePayment(CartActivity.this, totalSum, CartActivity.this, CartActivity.this);
            Objects.requireNonNull(completePayment.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            completePayment.show();
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
        orders.clear();
        Log.i("TAGELE", "getOrders: " + models);
        if (models.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            totalSection.setVisibility(View.GONE);
            emptyStateHolder.setVisibility(View.VISIBLE);
            return;
        }
        CartActivityAdapter adapter = new CartActivityAdapter(models, CartActivity.this, model, CartActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        totalSum = 0;
        for (CartModel model : models) {
            totalSum += model.getTotal();
        }
        String totalSumText = "Total: ₱ " + totalSum;
        total.setText(totalSumText);
        recyclerView.setVisibility(View.VISIBLE);
        totalSection.setVisibility(View.VISIBLE);
        emptyStateHolder.setVisibility(View.GONE);
        orders.addAll(models);
    }

    @Override
    public void onPay(boolean verdict, String message, String reference, double total, double money) {
        if (verdict) {
            this.reference = reference;
            HashMap<String, CartModel> modelHashMap = new HashMap<>();
            for (CartModel model: orders) {
                modelHashMap.put(model.getName(), model);
            }
            model.pay(reference, CartActivity.this, modelHashMap, total, money, CartActivity.this);
        }
    }

    @Override
    public void onSuccess(boolean verdict, String message) {
        if (verdict) {
            Intent intent = new Intent(getApplicationContext(), ShowReceiptActivity.class);
            intent.putExtra("reference", reference);
            startActivity(intent);
            finish();
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}