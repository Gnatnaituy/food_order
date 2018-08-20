package com.application.hasaker.Activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.Adapter.ToDoAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.R;
import com.application.hasaker.SwipeControllerActions;

import org.litepal.LitePal;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ToDoAdapter toDoAdapter;

    ItemSwipeController itemSwipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("等待完成的订单");
        setContentView(R.layout.main_activity);

        LitePal.initialize(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_to_do);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();
    }

    private void initData() {
        List<Todo> mToDoList = LitePal.findAll(Todo.class);
        toDoAdapter = new ToDoAdapter(mToDoList);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.todo_list);
        foodRecyclerView.setAdapter(toDoAdapter);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

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
        itemTouchhelper.attachToRecyclerView(foodRecyclerView);

        foodRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                itemSwipeController.onDraw(c);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_all_clear) {
            LitePal.deleteAll(Todo.class);
            toDoAdapter.clear();
        } else if (id == R.id.go_to_category) {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
