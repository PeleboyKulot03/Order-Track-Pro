package com.example.ordertrackpro.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.ILoginPage;
import com.example.ordertrackpro.utils.LoginModel;

public class LoginActivity extends AppCompatActivity implements ILoginPage {

    private ProgressBar progressBar;
    private EditText usernameET, passwordET;
    private LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        model = new LoginModel(this);
        Button login = findViewById(R.id.logIn);
        login.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            model.signIn(username, password);
        });
//        TextView forgotPassword = findViewById(R.id.forgotPassword);

    }

    @Override
    public void onLogin(boolean verdict, String message) {
        progressBar.setVisibility(View.GONE);
        if (verdict) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
            return;
        }
        Toast.makeText(this, "Login Failed, " + message, Toast.LENGTH_SHORT).show();
    }
}