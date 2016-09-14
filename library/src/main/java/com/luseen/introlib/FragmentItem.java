package com.luseen.introlib;

import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;

/**
 * Created by Chatikyan on 13.09.2016-10:48.
 */

public class FragmentItem {

    @IntegerRes
    private int fragmentLayout;

    @ColorInt
    private int backgroundColor;


    public FragmentItem(int fragmentLayout, @ColorInt int backgroundColor) {
        this.fragmentLayout = fragmentLayout;
        this.backgroundColor = backgroundColor;
    }

    int getFragmentLayout() {
        return fragmentLayout;
    }

    int getBackgroundColor() {
        return backgroundColor;
    }
}
