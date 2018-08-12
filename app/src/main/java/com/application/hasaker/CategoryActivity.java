package com.application.hasaker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class CategoryActivity extends AppCompatActivity {

    private Database dbHelper;

    private RecyclerView.Adapter foodAdapter;
    private RecyclerView.LayoutManager foodLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        FloatingActionButton add_button = findViewById(R.id.add_category);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodtoCategory();
            }
        });

        initData();
        initView();
    }

    private void initData(){
        foodAdapter = new CategoryAdapter(getData());
        foodLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.category_list);
        foodRecyclerView.setLayoutManager(foodLayoutManager);
        foodRecyclerView.setAdapter(foodAdapter);
    }

    private ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        // Get data from database
        dbHelper = new Database(CategoryActivity.this, "Category", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Food", null,null,null,null,null, null);
        if (cursor.moveToNext()) {
            do {
                String food_name = cursor.getString(cursor.getColumnIndex("name"));
                data.add(food_name);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return data;
    }

    private void addFoodtoCategory() {
        dbHelper = new Database(CategoryActivity.this, "Category", null, 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(CategoryActivity.this, R.layout.category_add_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = dialogView.findViewById(R.id.et_food_name);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        // Save Button configuration
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    Toast.makeText(CategoryActivity.this, "菜名不能为空", Toast.LENGTH_LONG).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", etName.getText().toString());
                    db.insert("Food", null, values);
                    dialog.dismiss();
                }
            }
        });
        // Cancel Button configuration
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
