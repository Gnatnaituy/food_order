package com.application.hasaker.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hasaker.R;
import com.application.hasaker.RecyclerViewItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;

public class AddToDoAdapter extends RecyclerView.Adapter<AddToDoAdapter.ViewHolder>
        implements RecyclerViewItemTouchHelperCallback.ItemTouchHelperCallback {
    private ArrayList<String> mData;

    public AddToDoAdapter(ArrayList<String> data) {
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

    @Override
    public void onItemDelete(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
