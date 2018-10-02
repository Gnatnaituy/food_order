package com.application.hasaker.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.application.hasaker.Adapter.AddToDoAdapter;
import com.application.hasaker.DB.Condiment;
import com.application.hasaker.DB.Food;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.R;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class AddTodoActivity extends AppCompatActivity {

    private AddToDoAdapter addToDoAdapter;
    private List<Food> mAddToDoList;
    private List<Condiment> mCondimentList;

    private LinearLayout mColumnsLinearLayout;
    private LayoutInflater inflater;

    private int column = 5;
    private int index = 0;

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
        mAddToDoList = LitePal.findAll(Food.class);
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
                final RadioGroup radioGroupCondiment = dialogView.findViewById(R.id.condiment_radio_group);
                RadioButton count_1 = dialogView.findViewById(R.id.count_1);
                Button btnSave = dialogView.findViewById(R.id.addtodo_dialog_add);
                Button btnCancel = dialogView.findViewById(R.id.addtodo_dialog_cancel);

                // dynamically adding condiment buttons ot radioGroupCondiment
                for (int i = 0; i < mCondimentList.size(); i++) {
                    RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    // radioButton margins
                    lp.setMargins(0, 0, 10, 0);
                    // Create a button for a condiment
                    RadioButton radioButton = new RadioButton(AddTodoActivity.this);
                    radioButton.setId(i);
                    radioButton.setButtonDrawable(R.drawable.radio_bg);
                    radioButton.setPadding(80, 0, 0, 0);
                    radioButton.setText(mCondimentList.get(i).getName());
                    // add radioButton ot radioGroupCondiment
                    radioGroupCondiment.addView(radioButton);
                }

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
                        StringBuilder condimentAdded = new StringBuilder();

                        // Get the count selected
                        for (int i = 0; i < radioGroupCount.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) radioGroupCount.getChildAt(i);
                            if (radioButton.isChecked()) {
                                count = radioButton.getText().toString();
                            }
                        }

                        // Get the condiments selected
                        for (int i = 0; i < radioGroupCondiment.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) radioGroupCondiment.getChildAt(i);
                            if (radioButton.isChecked()) {
                                condimentAdded.append(radioButton.getText()).append(" ");
                            }
                        }

                        todo.setName(foodNameFromItem);
                        todo.setCount(count);
                        todo.setCondiment(condimentAdded.toString());
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

//    private void displayColumns(int displayNumber) {
//
//        if (displayNumber <= 0) {
//            return;
//        }
//
//        // The rows should be created
//        int rows = displayNumber / column;
//
//        // Judgement if all rows are full filled
//        if (displayNumber % column == 0) {
//            for (int i = 0; i < rows; i++) {
//                createLinear(column);
//            }
//        } else {
//            for (int i = 0; i < rows; i++) {
//                createLinear(i);
//            }
//            // use % to get the last rows that not enough to full filled
//            rows = displayNumber % column;
//            createLinear(rows);
//        }
//
//    }
//
//    private void createLinear(int i) {
//        // Create LinearLayout
//        LinearLayout linearLayout = new LinearLayout(this);
//        // Config LayoutParams
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        // Config horizontal layout
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        // Set LayoutParams for the LinearLayout
//        linearLayout.setLayoutParams(lp);
//        // Circularly adding
//        for (int j = 0; j < i; j++) {
//            // Get the target layout xml file
//            View view = inflater.inflate(R.layout.condiment_add_dialog, null, false);
//            // Get component
//            RadioButton condimentButton = view.findViewById(R.id.)
//        }
//    }
}