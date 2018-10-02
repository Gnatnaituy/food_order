package com.application.hasaker.Fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.hasaker.Activity.MainActivity;
import com.application.hasaker.Adapter.CategoryAdapter;
import com.application.hasaker.DB.Food;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.R;
import com.application.hasaker.SwipeControllerActions;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class CategoryFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private ItemSwipeController itemSwipeController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("菜品管理");
        Objects.requireNonNull((MainActivity) getActivity()).showFloatingActionButton();
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        List<Food> mFoodList = LitePal.findAll(Food.class);
        categoryAdapter = new CategoryAdapter(mFoodList);

        View rootView = Objects.requireNonNull(layoutInflater).inflate(R.layout.category_fragment, container, false);
        Context context = rootView.getContext();
        RecyclerView foodRecyclerView = rootView.findViewById(R.id.category_list);
        foodRecyclerView.setAdapter(categoryAdapter);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemSwipeController = new ItemSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Food food = categoryAdapter.foodList.get(position);
                food.delete();
                categoryAdapter.foodList.remove(position);
                categoryAdapter.notifyItemRemoved(position);
                categoryAdapter.notifyItemRangeChanged(position, categoryAdapter.getItemCount());
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

        return rootView;
    }
}
