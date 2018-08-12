package com.application.hasaker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AddToDoAdapter extends RecyclerView.Adapter<AddToDoAdapter.ViewHolder> {
    private ArrayList<String> mData;

    AddToDoAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addtodo_item, parent, false);
        return new AddToDoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToDoAdapter.ViewHolder holder, int position) {
        holder.mItem.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mItem;
        ViewHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.addtodo_food_item);
        }
    }
}
