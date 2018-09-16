package com.application.hasaker.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.hasaker.Adapter.CategoryAdapter;
import com.application.hasaker.Adapter.CondimentAdapter;
import com.application.hasaker.Adapter.ToDoAdapter;
import com.application.hasaker.DB.Condiment;
import com.application.hasaker.DB.Food;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.Fragment.AboutFragment;
import com.application.hasaker.Fragment.CategoryFragment;
import com.application.hasaker.Fragment.CondimentFragment;
import com.application.hasaker.Fragment.TodoFragment;
import com.application.hasaker.R;

import org.litepal.LitePal;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String FRAGMENT_TODO = "todo";
    private static final String FRAGMENT_CATEGORY = "category";
    private static final String FRAGMENT_CONDIMENT = "condiment";
    private static final String FRAGMENT_ABOUT = "about";
    
    private ToDoAdapter toDoAdapter;
    private CategoryAdapter categoryAdapter;
    private CondimentAdapter condimentAdapter;

    private DrawerLayout drawerLayout;
    public FloatingActionButton fabBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        LitePal.initialize(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabBtn = findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                String fragmentTag = Objects.requireNonNull(currentFragment).getTag();
                switch (Objects.requireNonNull(fragmentTag)) {
                    case FRAGMENT_TODO:
                        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                        startActivity(intent);
                        break;
                    case FRAGMENT_CATEGORY:
                        addFood();
                        break;
                    case FRAGMENT_CONDIMENT:
                        addCondiment();
                        break;
                    default:
                        break;
                }
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_todo);

        Fragment fragment = new TodoFragment();
        displaySelectedFragment(fragment, FRAGMENT_TODO);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        // check if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
        }
        drawerLayout.closeDrawers();

        // check to see which item was been clicked and preform appropriate action
        switch (menuItem.getItemId()) {
            case R.id.nav_todo:
                TodoFragment todoFragment = new TodoFragment();
                displaySelectedFragment(todoFragment, FRAGMENT_TODO);
                return true;

            case R.id.nav_category:
                CategoryFragment categoryFragment = new CategoryFragment();
                displaySelectedFragment(categoryFragment, FRAGMENT_CATEGORY);
                return true;

            case R.id.nav_condiment:
                CondimentFragment condimentFragment = new CondimentFragment();
                displaySelectedFragment(condimentFragment, FRAGMENT_CONDIMENT);
                return true;

            case R.id.nav_about:
                AboutFragment aboutFragment = new AboutFragment();
                displaySelectedFragment(aboutFragment, FRAGMENT_ABOUT);
                return true;

            default:
                return true;
        }
    }

    private void displaySelectedFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    private void addFood() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(MainActivity.this, R.layout.category_add_dialog, null);
        dialog.setView(dialogView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = dialogView.findViewById(R.id.category_name);
        Button btnSave = dialogView.findViewById(R.id.category_btn_save);
        Button btnCancel = dialogView.findViewById(R.id.category_btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "菜名不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Food food = new Food();
                    food.setName(etName.getText().toString());
                    food.save();
                    dialog.dismiss();
                    categoryAdapter.addItem(food, 0);
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_LONG).show();
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

    private void addCondiment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(MainActivity.this, R.layout.condiment_add_dialog, null);
        dialog.setView(dialogView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = dialogView.findViewById(R.id.condiment_name);
        Button btnSave = dialogView.findViewById(R.id.condiment_btn_save);
        Button btnCancel = dialogView.findViewById(R.id.condiment_btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "配料名不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Condiment condiment = new Condiment();
                    condiment.setName(etName.getText().toString());
                    condiment.save();
                    dialog.dismiss();
                    condimentAdapter.addItem(condiment, 0);
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_LONG).show();
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

    public void showFloatingActionButton() {
        fabBtn.show();
    }

    public void hideFloatingActionButton() {
        fabBtn.hide();
    }
}
