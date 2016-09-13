package com.luseen.introlib;

import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;

/**
 * Created by Chatikyan on 13.09.2016-10:48.
 */

public class FragmentItem {

    private Fragment fragment;

    @ColorInt
    private int backgroundColor;


    public FragmentItem(Fragment fragment, @ColorInt int backgroundColor) {
        this.fragment = fragment;
        this.backgroundColor = backgroundColor;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
