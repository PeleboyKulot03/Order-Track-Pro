package com.example.ordertrackpro.ui.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IMenuFragment;
import com.example.ordertrackpro.utils.CartModel;

public class ShowAddItem extends Dialog {
    private String name;
    private double price;
    private int qty;
    private String totalString;
    private IMenuFragment iMenuFragment;

    public ShowAddItem(@NonNull Context context) {
        super(context);
    }

    public ShowAddItem(@NonNull Context context, String name, double price, int qty, IMenuFragment iMenuFragment) {
        super(context);
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.iMenuFragment = iMenuFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_add_item);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addMeal = findViewById(R.id.addMeal);
        TextView foodNameTV = findViewById(R.id.foodName);
        TextView qtyTV = findViewById(R.id.qty);
        TextView priceTV = findViewById(R.id.price);
        TextView add = findViewById(R.id.add);
        TextView minus = findViewById(R.id.minus);
        TextView qtyNumber = findViewById(R.id.qtyNumber);
        TextView total = findViewById(R.id.total);

        totalString = "Total: ₱ " + price;
        total.setText(totalString);
        add.setOnClickListener(v -> {
            int curQty = Integer.parseInt(qtyNumber.getText().toString()) + 1;
            qtyNumber.setText(String.valueOf(curQty));
            totalString = "Total: ₱ " + (Integer.parseInt(qtyNumber.getText().toString()) * price);
            total.setText(totalString);
        });

        minus.setOnClickListener(v -> {
            int curQty = Integer.parseInt(qtyNumber.getText().toString()) - 1;
            qtyNumber.setText((curQty > 0 ? String.valueOf(curQty): "1"));
            totalString = "Total: ₱ " + (Integer.parseInt(qtyNumber.getText().toString()) * price);
            total.setText(totalString);
        });

        addMeal.setOnClickListener(view -> {
            CartModel model = new CartModel(name, qty, Integer.parseInt(qtyNumber.getText().toString()), (price * Integer.parseInt(qtyNumber.getText().toString())), price);
            iMenuFragment.addToCart(model);
            hide();
        });
        foodNameTV.setText(name);
        String qtyString = "x" + qty;
        String priceString = "₱ " + price;
        qtyTV.setText(qtyString);
        priceTV.setText(priceString);

    }
}