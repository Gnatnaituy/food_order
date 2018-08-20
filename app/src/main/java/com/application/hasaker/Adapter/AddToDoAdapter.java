package com.application.hasaker.Adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hasaker.DB.Food;
import com.application.hasaker.R;

import java.util.List;

public class AddToDoAdapter extends RecyclerView.Adapter<AddToDoAdapter.AddToDoViewHolder> {

    public List<Food> foodList;
    private onRecyclerViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public AddToDoAdapter(List<Food> data)  {
        this.foodList = data;
    }

    class AddToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private FloatingActionButton button;

        AddToDoViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.addtodo_food_item);
            button = view.findViewById(R.id.add);

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClickListener(v, getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public AddToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addtodo_item, parent, false);

        return new AddToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddToDoViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.name.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return foodList == null ? 0 : foodList.size();
    }
}
