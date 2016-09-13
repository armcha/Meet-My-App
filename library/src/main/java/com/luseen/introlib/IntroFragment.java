package com.luseen.introlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroFragment extends Fragment {

    private static final String LAYOUT_ID = "layoutId";

    private int layoutId;

    public static IntroFragment newInstance(int layoutResId) {
        IntroFragment introFragment = new IntroFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_ID, layoutResId);
        introFragment.setArguments(arguments);
        return introFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(LAYOUT_ID))
            layoutId = getArguments().getInt(LAYOUT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutId, container, false);
    }
}
