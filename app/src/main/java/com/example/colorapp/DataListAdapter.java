package com.example.colorapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private List<DataModel> data = new ArrayList<>();

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel item = data.get(position);
        holder.colorTextView.setText(item.getColorHashCode());
        holder.dateTextView.setText(item.getDate());
        holder.cardView.setCardBackgroundColor(Color.parseColor(item.getColorHashCode()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView colorTextView;
        private TextView dateTextView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorTextView = itemView.findViewById(R.id.colorTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
