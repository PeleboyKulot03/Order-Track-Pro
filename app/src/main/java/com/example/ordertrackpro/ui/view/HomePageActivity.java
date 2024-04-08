package com.example.ordertrackpro.ui.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.ordertrackpro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static final int MENU = R.id.menu;
    public static final int EDIT = R.id.edit;
    public static final int ACCOUNT = R.id.account;
    private AccountFragment accountFragment;
    private MenuFragment menuFragment;
    private EditFragment editFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setSelectedItemId(MENU);
        accountFragment = new AccountFragment();
        menuFragment = new MenuFragment();
        editFragment = new EditFragment();

        switchFragment(menuFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == EDIT) {
                switchFragment(editFragment);
            }

            if (item.getItemId() == MENU) {
                switchFragment(menuFragment);
            }

            if (item.getItemId() == ACCOUNT) {
                switchFragment(accountFragment);
            }
            return true;
        });

    }

    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();
    }


}