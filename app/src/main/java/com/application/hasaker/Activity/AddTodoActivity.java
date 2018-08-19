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
import android.widget.Toast;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.R;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class AddTodoActivity extends AppCompatActivity implements AddToDoAdapter.AddToDoClick {

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
        addToDoAdapter = new AddToDoAdapter(mAddToDoList, this);
    }

    private void initView() {
        RecyclerView foodRecyclerView = findViewById(R.id.addtodo_list);
        foodRecyclerView.setAdapter(addToDoAdapter);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void click() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTodoActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(AddTodoActivity.this, R.layout.addtodo_add_dialog, null);
        dialog.setView(dialogView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = dialogView.findViewById(R.id.et_food_name);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
    }
}
