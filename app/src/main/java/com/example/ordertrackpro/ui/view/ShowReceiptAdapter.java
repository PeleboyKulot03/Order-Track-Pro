package com.example.ordertrackpro.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.utils.ShowReceiptModel;

import org.checkerframework.common.value.qual.StringVal;

import java.util.ArrayList;

public class ShowReceiptAdapter extends RecyclerView.Adapter<ShowReceiptAdapter.ViewHolder> {
    private ArrayList<ShowReceiptModel> models;
    private Context context;

    public ShowReceiptAdapter(ArrayList<ShowReceiptModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowReceiptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowReceiptAdapter.ViewHolder holder, int position) {
        ShowReceiptModel model = models.get(position);
        holder.price.setText(String.valueOf(model.getPrice()));
        holder.qty.setText(String.valueOf(model.getQtyNumber()));
        holder.foodName.setText(model.getName());
        holder.total.setText(String.valueOf(model.getQtyNumber() * model.getPrice()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodName;
        private final TextView qty;
        private final TextView price;
        private final TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);
        }
    }
}
