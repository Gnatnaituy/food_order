package com.application.hasaker.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.DB.Condiment;
import com.application.hasaker.DB.Food;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.R;

import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AddTodoActivity extends AppCompatActivity {

    private AddToDoAdapter addToDoAdapter;
    private List<Condiment> mCondimentList;
    private List<String> mCondiment = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加");
        setContentView(R.layout.addtodo_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_add_todo);
        setSupportActionBar(toolbar);

        initData();
        initView();
    }

    private void initData() {
        List<Food> mAddToDoList = LitePal.findAll(Food.class);
        mCondimentList = LitePal.findAll(Condiment.class);
        addToDoAdapter = new AddToDoAdapter(mAddToDoList);
    }

    private void initView() {
        // AddToDo food list RecyclerView conf
        RecyclerView foodRecyclerView = findViewById(R.id.addtodo_list);
        foodRecyclerView.setAdapter(addToDoAdapter);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

        addToDoAdapter.setOnItemClickListener(new AddToDoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                // Dialog configuration
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTodoActivity.this);
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(AddTodoActivity.this, R.layout.addtodo_add_dialog, null);
                dialog.setView(dialogView);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                // Set foodName, count, condiment selectors and buttons on dialog
                final String foodNameFromItem = addToDoAdapter.foodList.get(position).getName();
                final TextView foodNameOnDialog = dialogView.findViewById(R.id.dialog_food_name);
                final RadioGroup radioGroupCount = dialogView.findViewById(R.id.radiogroup_count);
                final MultiLineChooseLayout flowLayout = dialogView.findViewById(R.id.condiment_checkbox);
                RadioButton count_1 = dialogView.findViewById(R.id.count_1);
                Button btnSave = dialogView.findViewById(R.id.addtodo_dialog_add);
                Button btnCancel = dialogView.findViewById(R.id.addtodo_dialog_cancel);

                // Clear ArrayList first
                mCondiment.clear();
                // dynamically adding condiment buttons ot radioGroupCondiment
                for (Condiment aMCondimentList : mCondimentList) {
                    mCondiment.add(aMCondimentList.getName());
                }

                flowLayout.setList(mCondiment);
                // foodName conf
                foodNameOnDialog.setText(foodNameFromItem);
                // Count conf
                count_1.setChecked(true);
                // btnSave conf
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Todo todo = new Todo();
                        String count = null;
                        String condimentAdded;

                        // Get the count selected
                        for (int i = 0; i < radioGroupCount.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) radioGroupCount.getChildAt(i);
                            if (radioButton.isChecked()) {
                                count = radioButton.getText().toString();
                            }
                        }

                        // Get the condiments selected
                        condimentAdded = Arrays.toString(flowLayout.getAllItemSelectedTextWithStringArray());

                        todo.setName(foodNameFromItem);
                        todo.setCount(count);
                        todo.setCondiment(condimentAdded.replaceAll("\\[|]|,", ""));
                        todo.save();
                        dialog.dismiss();
                    }
                });
                // btnCancel conf
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}