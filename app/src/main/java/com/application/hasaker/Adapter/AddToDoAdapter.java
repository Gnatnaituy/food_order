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

    public interface AddToDoClick {
        void click();
    }

    private AddToDoClick mListener;

    public AddToDoAdapter(List<Food> data, AddToDoClick listener)  {
        this.foodList = data;
        mListener = listener;
    }

    class AddToDoViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        AddToDoViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.addtodo_food_item);
        }
    }

    @NonNull
    @Override
    public AddToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addtodo_item, parent, false);
        FloatingActionButton add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.click();
            }
        });

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
