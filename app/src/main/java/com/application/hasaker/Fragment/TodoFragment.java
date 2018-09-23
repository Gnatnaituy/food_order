package com.application.hasaker.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.hasaker.Activity.AddTodoActivity;
import com.application.hasaker.Activity.MainActivity;
import com.application.hasaker.Adapter.ToDoAdapter;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.R;
import com.application.hasaker.SwipeControllerActions;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;

public class TodoFragment extends Fragment {

    private ToDoAdapter toDoAdapter;
    private ItemSwipeController itemSwipeController;

//    private boolean isFirstLoading = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("待做订单");
        Objects.requireNonNull((MainActivity) getActivity()).showFloatingActionButton();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        List<Todo> mToDoList = LitePal.findAll(Todo.class);
        toDoAdapter = new ToDoAdapter(mToDoList);

        final View rootView = Objects.requireNonNull(layoutInflater).inflate(
                R.layout.todo_fragment, container, false);
        Context context = rootView.getContext();
        RecyclerView todoRecyclerView = rootView.findViewById(R.id.todo_list);
        todoRecyclerView.setAdapter(toDoAdapter);
        todoRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        todoRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemSwipeController = new ItemSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Todo todo = toDoAdapter.todoList.get(position);
                todo.delete();
                toDoAdapter.todoList.remove(position);
                toDoAdapter.notifyItemRemoved(position);
                toDoAdapter.notifyItemRangeChanged(position, toDoAdapter.getItemCount());
            }
        });

        final ItemTouchHelper itemTouchhelper = new ItemTouchHelper(itemSwipeController);
        itemTouchhelper.attachToRecyclerView(todoRecyclerView);

        todoRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                itemSwipeController.onDraw(c);
            }
        });


        return rootView;
    }
}
