package com.application.hasaker.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.Module.Database;
import com.application.hasaker.R;
import com.application.hasaker.RecyclerViewItemTouchHelperCallback;

import java.util.ArrayList;

public class AddTodoActivity extends AppCompatActivity {

    private RecyclerView.Adapter foodAdapter;
    private RecyclerView.LayoutManager foodLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtodo_activity);

        initData();
        initView();
    }

    private void initData() {
        foodAdapter = new AddToDoAdapter(getData());
        foodLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.addtodo_list);
        foodRecyclerView.setAdapter(foodAdapter);
        foodRecyclerView.setLayoutManager(foodLayoutManager);

        ItemTouchHelper.Callback callback = new RecyclerViewItemTouchHelperCallback(
                (RecyclerViewItemTouchHelperCallback.ItemTouchHelperCallback) foodAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(foodRecyclerView);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        // Get data from database
        Database dbHelper = new Database(AddTodoActivity.this, "Category", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Food", null, null,
                null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                String food_name = cursor.getString(cursor.getColumnIndex("name"));
                data.add(food_name);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return data;
    }
}
