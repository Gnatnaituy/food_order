package com.application.hasaker.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.hasaker.R;

import java.util.Objects;


public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return Objects.requireNonNull(inflater).inflate(R.layout.condiment_fragment, container, false);
    }
}
