package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.utils.MenuModel;

import java.util.ArrayList;

public class MenuRecyclerviewAdapter extends RecyclerView.Adapter<MenuRecyclerviewAdapter.ViewHolder> {
    private ArrayList<MenuModel> models;
    private Context context;
    private Activity activity;

    public MenuRecyclerviewAdapter(ArrayList<MenuModel> models, Context context, Activity activity) {
        this.models = models;
        this.context = context;
        this.activity = activity;
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
        holder.foodName.setText(model.getFoodName());
        String qtyString = "x" + model.getQty();
        String priceString = "₱ " + model.getPrice();
        holder.qty.setText(qtyString);
        holder.price.setText(priceString);
        holder.foodImage.setImageDrawable(activity.getDrawable(model.getImageUrl()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            foodImage = itemView.findViewById(R.id.foodImage);
        }
    }
}

