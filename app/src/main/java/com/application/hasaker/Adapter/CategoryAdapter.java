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

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
         FoodViewHolder(View view) {
             super(view);
             name = view.findViewById(R.id.category_food_item);
         }
    }

    public CategoryAdapter(List<Food> data)  {
        this.foodList = data;
    }

//    public void updateData(List<Food> data) {
//        this.foodList = data;
//        notifyDataSetChanged();
//    }

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

//    @Override
//    public void onItemDelete(int position) {
//        foodList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    @Override
//    public void onMove(int fromPosition, int toPosition) {
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(foodList, i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(foodList, i, i - 1);
//            }
//        }
//        Collections.swap(foodList, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//    }

//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.category_food_item);
//        }
//    }
}
