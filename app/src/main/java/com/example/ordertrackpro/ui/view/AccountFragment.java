package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        ImageView profilePicture = view.findViewById(R.id.profilePicture);
        AccountModel accountModel = new AccountModel();
        TextView displayName = view.findViewById(R.id.displayName);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.key), Context.MODE_PRIVATE);
        String name = sharedPref.getString(getString(R.string.get_user), "Cashier's Name");
        String photoString = sharedPref.getString(getString(R.string.get_photo), "none");
        if (!photoString.equals("none")) {
            Uri uri = Uri.parse(photoString);
            Glide.with(getContext()).load(uri).centerCrop().circleCrop().into(profilePicture);
        }

        displayName.setText(name);

        logout.setOnClickListener(v -> {
            accountModel.SignOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            activity.finish();
        });
        return view;
    }
}