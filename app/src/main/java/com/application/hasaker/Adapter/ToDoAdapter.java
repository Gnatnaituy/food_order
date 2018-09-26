package com.application.hasaker.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hasaker.DB.Todo;
import com.application.hasaker.R;

import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.FoodViewHolder> {

    public List<Todo> todoList;

    public ToDoAdapter(List<Todo> data)  {
        this.todoList = data;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView count;
        private TextView condiment;

        FoodViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.todo_food_name);
            count = view.findViewById(R.id.todo_food_count);
            condiment = view.findViewById(R.id.todo_food_condiment);
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.name.setText(todo.getName());
        holder.count.setText(todo.getCount());
        holder.condiment.setText(todo.getCondiment());
    }

    @Override
    public int getItemCount() {
        return todoList == null ? 0 : todoList.size();
    }

    public void clear() {
        final int size = todoList.size();
        if (size > 0) {
            todoList.subList(0, size).clear();

            notifyItemRangeRemoved(0, size);
        }
    }
}
