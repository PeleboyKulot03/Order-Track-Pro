package com.example.ordertrackpro.ui.view;

import android.os.Bundle;
import android.widget.TextView;

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
        total = findViewById(R.id.total);
        recyclerView = findViewById(R.id.recyclerView);
        model = new CartModel();
        model.getOrders(this);
    }

    @Override
    public void getOrders(ArrayList<CartModel> models) {
        CartActivityAdapter adapter = new CartActivityAdapter(models, getApplicationContext(), model);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        for (CartModel model: models) {
            totalSum += model.getTotal();
        }
        String totalSumText = "Total: ₱ " + totalSum;
        total.setText(totalSumText);
    }
}