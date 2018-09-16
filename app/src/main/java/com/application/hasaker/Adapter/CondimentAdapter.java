package com.application.hasaker.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hasaker.DB.Condiment;
import com.application.hasaker.DB.Food;
import com.application.hasaker.R;

import java.util.List;


public class CondimentAdapter extends RecyclerView.Adapter<CondimentAdapter.CondimentViewHolder> {

    public List<Condiment> condimentList;

    public CondimentAdapter(List<Condiment> data)  {
        this.condimentList = data;
    }

    class CondimentViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        CondimentViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.condiment_item);
        }
    }

    @NonNull
    @Override
    public CondimentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.condiment_item, parent, false);
        return new CondimentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CondimentViewHolder holder, int position) {
        Condiment condiment = condimentList.get(position);
        holder.name.setText(condiment.getName());
    }

    @Override
    public int getItemCount() {
        return condimentList == null ? 0 : condimentList.size();
    }

    public void addItem(Condiment item, int position) {
        this.condimentList.add(position, item);
        notifyItemInserted(position);
    }
}
