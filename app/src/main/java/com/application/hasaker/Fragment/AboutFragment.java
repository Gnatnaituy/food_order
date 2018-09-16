package com.application.hasaker.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.hasaker.Activity.MainActivity;
import com.application.hasaker.R;

import java.util.Objects;

public class AboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("关于");
        Objects.requireNonNull((MainActivity) getActivity()).hideFloatingActionButton();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return (Objects.requireNonNull(layoutInflater)).inflate(R.layout.todo_fragment, container, false);
    }
}
