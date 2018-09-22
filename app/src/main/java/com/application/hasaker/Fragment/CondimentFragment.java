package com.application.hasaker.Fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.hasaker.Activity.MainActivity;
import com.application.hasaker.Adapter.CondimentAdapter;
import com.application.hasaker.DB.Condiment;
import com.application.hasaker.ItemSwipeController;
import com.application.hasaker.R;
import com.application.hasaker.SwipeControllerActions;
import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;


public class CondimentFragment extends Fragment {

    private CondimentAdapter condimentAdapter;
    private ItemSwipeController itemSwipeController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("配料管理");
        Objects.requireNonNull((MainActivity) getActivity()).showFloatingActionButton();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        List<Condiment> mCondimentList = LitePal.findAll(Condiment.class);
        condimentAdapter = new CondimentAdapter(mCondimentList);

        final View rootView = Objects.requireNonNull(layoutInflater).inflate(
                R.layout.condiment_fragment, container, false);
        Context context = rootView.getContext();
        RecyclerView condimentRecyclerView = rootView.findViewById(R.id.condiment_list);
        condimentRecyclerView.setAdapter(condimentAdapter);
        condimentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        condimentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemSwipeController = new ItemSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Condiment condiment = condimentAdapter.condimentList.get(position);
                condiment.delete();
                condimentAdapter.condimentList.remove(position);
                condimentAdapter.notifyItemRemoved(position);
                condimentAdapter.notifyItemRangeChanged(position, condimentAdapter.getItemCount());
            }
        });

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemSwipeController);
        itemTouchHelper.attachToRecyclerView(condimentRecyclerView);

        condimentRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                itemSwipeController.onDraw(c);
            }
        });

        return rootView;
    }
}
