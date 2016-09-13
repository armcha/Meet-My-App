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

    private int layoutResId;

    public static IntroFragment newInstance(int layoutResId) {
        IntroFragment introFragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutResId);
        introFragment.setArguments(args);
        return introFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(LAYOUT_ID))
            layoutResId = getArguments().getInt(LAYOUT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }
}
