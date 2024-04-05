package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.utils.AccountModel;

public class AccountFragment extends Fragment {
    private Activity activity;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        if (getActivity() != null && getContext() != null) {
            activity = getActivity();
            context = getContext();
        }
        Button logout = view.findViewById(R.id.logout);
        AccountModel accountModel = new AccountModel();

        logout.setOnClickListener(v -> {
            accountModel.SignOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            activity.finish();
        });
        return view;
    }
}