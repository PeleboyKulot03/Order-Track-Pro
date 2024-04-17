package com.example.ordertrackpro.ui.customViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.ICartActivity;

import java.util.Random;

public class CompletePayment extends Dialog {

    private double total;
    private Activity activity;
    private EditText cashET;
    private ICartActivity iCartActivity;

    public CompletePayment(Context context) {
        super(context);
    }

    public CompletePayment(@NonNull Context context, double total, Activity activity, ICartActivity iCartActivity) {
        super(context);
        this.total = total;
        this.activity = activity;
        this.iCartActivity = iCartActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cashET = findViewById(R.id.moneyET);
        Button okay = findViewById(R.id.okay);
        okay.setOnClickListener(view -> {
            if (validate()) {
                iCartActivity.onPay(true, "Payment complete", getRandomId(), total, Double.parseDouble(cashET.getText().toString()));
                dismiss();
            }
        });

    }

    private String getRandomId() {
        Random r = new Random();

        String alphabet = "123abcdefghijklmnopqrstuvwxyz";
        StringBuilder finalId = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            finalId.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return finalId.toString();
    }

    private boolean validate() {
        if (cashET.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Input money to continue!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Double.parseDouble(cashET.getText().toString()) < total) {
            Toast.makeText(activity, "Add more money to continue!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}