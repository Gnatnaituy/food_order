package com.application.hasaker.Activity;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.hasaker.Adapter.CategoryAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.SwipeControllerActions;
import com.application.hasaker.R;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class CategoryActivity extends AppCompatActivity {

    private CategoryAdapter foodAdapter;

    ItemSwipeController itemSwipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("菜品管理");
        setContentView(R.layout.category_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_category);
        setSupportActionBar(toolbar);

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
        List<Food> mFoodList = LitePal.findAll(Food.class);
        foodAdapter = new CategoryAdapter(mFoodList);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.category_list);
        foodRecyclerView.setAdapter(foodAdapter);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemSwipeController = new ItemSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Food food = foodAdapter.foodList.get(position);
                food.delete();
                foodAdapter.foodList.remove(position);
                foodAdapter.notifyItemRemoved(position);
                foodAdapter.notifyItemRangeChanged(position, foodAdapter.getItemCount());
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

    /**
     * Floating Add Button Configuration
     */
    private void addFoodtoCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(CategoryActivity.this, R.layout.category_add_dialog, null);
        dialog.setView(dialogView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = dialogView.findViewById(R.id.et_food_name);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

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
                    foodAdapter.addItem(food, 0);
                    Toast.makeText(CategoryActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
