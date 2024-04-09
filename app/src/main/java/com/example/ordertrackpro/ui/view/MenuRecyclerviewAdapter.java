package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IMenuFragment;
import com.example.ordertrackpro.ui.customViews.ShowAddItem;
import com.example.ordertrackpro.utils.MenuModel;

import java.util.ArrayList;
import java.util.Objects;

public class MenuRecyclerviewAdapter extends RecyclerView.Adapter<MenuRecyclerviewAdapter.ViewHolder> {
    private ArrayList<MenuModel> models;
    private Context context;
    private Activity activity;
    private IMenuFragment iMenuFragment;

    public MenuRecyclerviewAdapter(ArrayList<MenuModel> models, Context context, Activity activity, IMenuFragment iMenuFragment) {
        this.models = models;
        this.context = context;
        this.activity = activity;
        this.iMenuFragment = iMenuFragment;
    }

    @NonNull
    @Override
    public MenuRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerviewAdapter.ViewHolder holder, int position) {
        MenuModel model = models.get(holder.getAdapterPosition());
        holder.foodName.setText(model.getName());
        String qtyString = "x" + model.getQty();
        String priceString = "₱ " + model.getPrice();
        holder.qty.setText(qtyString);
        holder.price.setText(priceString);
        Glide.with(context).load(model.getImageUrl()).into(holder.foodImage);
        holder.addMeal.setOnClickListener(v -> {
            ShowAddItem showAddItem = new ShowAddItem(activity, model.getName(), model.getPrice(), model.getQty(), iMenuFragment);
            Objects.requireNonNull(showAddItem.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            showAddItem.show();
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodName;
        private final TextView price;
        private final TextView qty;
        private final ImageView foodImage;
        private final Button addMeal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            foodImage = itemView.findViewById(R.id.foodImage);
            addMeal = itemView.findViewById(R.id.addMeal);
        }
    }
}

