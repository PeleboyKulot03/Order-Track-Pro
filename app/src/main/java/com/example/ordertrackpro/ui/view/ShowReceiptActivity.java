package com.example.ordertrackpro.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IShowReceipt;
import com.example.ordertrackpro.utils.ShowReceiptModel;

import java.util.ArrayList;

public class ShowReceiptActivity extends AppCompatActivity implements IShowReceipt {
    private RecyclerView recyclerView;
    private String reference;
    private ShowReceiptModel model;
    private TextView receivedMoney;
    private TextView total;
    private TextView change;
    private TextView referenceNum;
    private TextView date;
    private TextView time;
    private TextView cashiersName;
    double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_receipt);
        Intent intent = getIntent();
        if (intent.hasExtra("reference")) {
            reference = intent.getStringExtra("reference");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        receivedMoney = findViewById(R.id.receivedMoney);
        total = findViewById(R.id.total);
        change = findViewById(R.id.change);
        referenceNum = findViewById(R.id.reference);
        cashiersName = findViewById(R.id.cashiersName);
        model = new ShowReceiptModel();
        model.getReceipt(this, reference, this);
        recyclerView = findViewById(R.id.recyclerView);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });
    }
    @Override
    public void getReceipt(ArrayList<ShowReceiptModel> models, double money, String date, String time) {
        Log.i("TAGELE", "getReceipt: " + models);
        if (!models.isEmpty()) {
            ShowReceiptAdapter adapter = new ShowReceiptAdapter(models, getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            for (ShowReceiptModel model: models) {
                totalPrice += (model.getPrice() * model.getQtyNumber());
            }
            String totalText = "Total: ₱ " + totalPrice;
            String cash = "Received money: ₱ " + money;
            String changeText = "Change ₱ " + (money - totalPrice);
            total.setText(totalText);
            receivedMoney.setText(cash);
            change.setText(changeText);
            referenceNum.setText(reference);
            this.date.setText(date);
            this.time.setText(time);
            SharedPreferences sharedPref = ShowReceiptActivity.this.getSharedPreferences(getString(R.string.key), Context.MODE_PRIVATE);
            String cashierName = "Cashier's Name: " + sharedPref.getString(getString(R.string.get_user), "Cashier's Name");
            cashiersName.setText(cashierName);
        }

    }
}