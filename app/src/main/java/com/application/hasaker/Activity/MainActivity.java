package com.application.hasaker.Activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.application.hasaker.Adapter.ToDoAdapter;
import com.application.hasaker.DB.Todo;
import com.application.hasaker.Fragment.AboutFragment;
import com.application.hasaker.Fragment.CategoryFragment;
import com.application.hasaker.Fragment.CondimentFragment;
import com.application.hasaker.Fragment.TodoFragment;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.R;
import com.application.hasaker.SwipeControllerActions;

import org.litepal.LitePal;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ToDoAdapter toDoAdapter;
    ItemSwipeController itemSwipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_main);
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


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TodoFragment todoFragment = new TodoFragment();
        fragmentTransaction.replace(R.id.fragment_container, todoFragment);
        fragmentTransaction.commit();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                // check if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                // close drawer on item click
                drawerLayout.closeDrawers();
                // check to see which item was been clicked and preform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.nav_todo:
                        TodoFragment todoFragment = new TodoFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, todoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        return true;

                    case R.id.nav_category:
                        CategoryFragment categoryFragment = new CategoryFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, categoryFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        return true;

                    case R.id.nav_condiment:
                        CondimentFragment condimentFragment = new CondimentFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, condimentFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        return true;

                    case R.id.nav_about:
                        AboutFragment aboutFragment = new AboutFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        return true;

                    default:
                        return true;
                }
            }
        });


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        TodoFragment todoFragment = new TodoFragment();
//        fragmentTransaction.replace(R.id.fragment_container, todoFragment);
//        fragmentTransaction.commit();


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


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if  (id == R.id.nav_todo) {
//
//
//        } else if (id == R.id.nav_category) {
//            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_condiment) {
//
//        } else if (id == R.id.nav_about) {
//
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
