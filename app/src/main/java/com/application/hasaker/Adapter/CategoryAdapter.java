package com.application.hasaker.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hasaker.DB.Food;
import com.application.hasaker.R;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.FoodViewHolder> {

    public List<Food> foodList;

    public CategoryAdapter(List<Food> data)  {
        this.foodList = data;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
         FoodViewHolder(View view) {
             super(view);
             name = view.findViewById(R.id.category_food_item);
         }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.name.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return foodList == null ? 0 : foodList.size();
    }

    public void addItem(Food item, int position) {
        this.foodList.add(position, item);
        notifyItemInserted(position);
    }
}
