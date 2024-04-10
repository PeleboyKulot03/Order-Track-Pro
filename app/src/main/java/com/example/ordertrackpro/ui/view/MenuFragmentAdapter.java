package com.example.ordertrackpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.utils.MenuModel;

import java.util.ArrayList;

public class MenuFragmentAdapter extends RecyclerView.Adapter<MenuFragmentAdapter.ViewHolder> {
    private final ArrayList<MenuRecyclerviewAdapter> adapters;
    private final Context context;

    public MenuFragmentAdapter(ArrayList<MenuRecyclerviewAdapter> adapters, Context context) {
        this.adapters = adapters;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuFragmentAdapter.ViewHolder holder, int position) {
        MenuRecyclerviewAdapter adapter = adapters.get(holder.getAdapterPosition());
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

    @Override
    public int getItemCount() {
        return adapters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
