package com.application.hasaker.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.R;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class AddTodoActivity extends AppCompatActivity {

    private AddToDoAdapter addToDoAdapter;

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
        addToDoAdapter = new AddToDoAdapter(mAddToDoList);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.addtodo_list);
        foodRecyclerView.setAdapter(addToDoAdapter);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

        addToDoAdapter.setOnItemClickListener(new AddToDoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTodoActivity.this);
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(AddTodoActivity.this, R.layout.addtodo_add_dialog, null);
                dialog.setView(dialogView);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                final String foodNameFromItem = addToDoAdapter.foodList.get(position).getName();
                final TextView foodNameOnDialog = dialogView.findViewById(R.id.dialog_food_name);
                final RadioGroup radioGroupCount = dialogView.findViewById(R.id.radiogroup_count);
                final RadioGroup radioGroupPeppery = dialogView.findViewById(R.id.radiogroup_peppery);

                RadioButton count_1 = dialogView.findViewById(R.id.count_1);
                RadioButton peppery_1 = dialogView.findViewById(R.id.peppery_1);
                Button btnSave = dialogView.findViewById(R.id.addtodo_dialog_add);
                Button btnCancel = dialogView.findViewById(R.id.addtodo_dialog_cancel);

                foodNameOnDialog.setText(foodNameFromItem);
                count_1.setChecked(true);
                peppery_1.setChecked(true);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Todo todo = new Todo();
                        String count = null;
                        String peppery = null;

                        for (int i = 0; i < radioGroupCount.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) radioGroupCount.getChildAt(i);
                            if (radioButton.isChecked()) {
                                count = radioButton.getText().toString();
                            }
                        }

                        for (int i = 0; i < radioGroupPeppery.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) radioGroupPeppery.getChildAt(i);
                            if (radioButton.isChecked()) {
                                peppery = radioButton.getText().toString();
                            }
                        }

                        todo.setName(foodNameFromItem);
                        todo.setCount(count);
                        todo.setPeppery(peppery);
                        todo.save();
                        dialog.dismiss();
                    }
                });

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