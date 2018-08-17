package com.application.hasaker.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.hasaker.Adapter.CategoryAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.Module.Database;
import com.application.hasaker.R;
import com.application.hasaker.RecyclerViewItemTouchHelperCallback;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    SQLiteDatabase db = LitePal.getDatabase();

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
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.Callback callback = new RecyclerViewItemTouchHelperCallback(
                (RecyclerViewItemTouchHelperCallback.ItemTouchHelperCallback) foodAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(foodRecyclerView);
    }

    private ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();

        List<Food> allFood = LitePal.findAll(Food.class);
        for (Food food: allFood) {
            data.add(food.getName());
        }

        return data;
    }

    /**
     * Floating Add Button Configuration
     */
    private void addFoodtoCategory() {
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
                    Food food = new Food();
                    food.setName(etName.getText().toString());
                    food.save();
                    dialog.dismiss();
                    Toast.makeText(CategoryActivity.this, "添加成功", Toast.LENGTH_LONG).show();
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
