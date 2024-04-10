package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.customViews.ShowAddItem;
import com.example.ordertrackpro.utils.CartModel;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivityAdapter extends RecyclerView.Adapter<CartActivityAdapter.ViewHolder> {
    private final ArrayList<CartModel> models;
    private final Context context;
    private final CartModel cartModel;
    private String totalString;
    private Activity activity;
    public CartActivityAdapter(ArrayList<CartModel> models, Context context, CartModel cartModel, Activity activity) {
        this.models = models;
        this.context = context;
        this.cartModel = cartModel;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CartActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartActivityAdapter.ViewHolder holder, int position) {
        CartModel model = models.get(holder.getAdapterPosition());
        holder.foodName.setText(model.getName());
        String qtyString = "x" + model.getQty();
        String priceString = "₱ " + model.getPrice();
        totalString = "Total: ₱ " + model.getTotal();
        holder.qty.setText(qtyString);
        holder.price.setText(priceString);
        holder.total.setText(totalString);
        holder.qtyNumber.setText(String.valueOf(model.getQtyNumber()));
        holder.add.setOnClickListener(v -> {
            int curQty = Integer.parseInt(holder.qtyNumber.getText().toString()) + 1;
            double total = model.getPrice() * curQty;
            cartModel.updateItem(model.getName(), curQty, total, activity);
        });

        holder.minus.setOnClickListener(v -> {
            int curQty = Integer.parseInt(holder.qtyNumber.getText().toString()) - 1;
            if (curQty == 0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete Caution");
                alertDialogBuilder.setMessage("Are you sure that you want to delete this item?");
                alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                    cartModel.deleteItem(model.getName(), activity);
                   dialog.dismiss();
                });
                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
                alertDialogBuilder.create().show();
                return;
            }
            double total = model.getPrice() * curQty;
            cartModel.updateItem(model.getName(), curQty, total, activity);
        });
        holder.remove.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Delete Caution");
            alertDialogBuilder.setMessage("Are you sure that you want to delete this item?");
            alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                cartModel.deleteItem(model.getName(), activity);
                dialog.dismiss();
            });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialogBuilder.create().show();
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
        private final TextView qtyNumber;
        private final TextView add;
        private final TextView minus;
        private final TextView total;
        private final Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            qtyNumber = itemView.findViewById(R.id.qtyNumber);
            add = itemView.findViewById(R.id.add);
            minus = itemView.findViewById(R.id.minus);
            total = itemView.findViewById(R.id.total);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
