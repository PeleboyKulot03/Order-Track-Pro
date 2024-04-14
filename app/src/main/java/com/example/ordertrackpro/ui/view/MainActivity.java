package com.example.ordertrackpro.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IMainActivity;
import com.example.ordertrackpro.utils.MainActivityModel;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    private MainActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        model = new MainActivityModel(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        model.isSignedIn();
    }

    @Override
    public void isSignedIn(boolean verdict) {
        if (verdict) {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}