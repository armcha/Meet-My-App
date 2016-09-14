package com.luseen.meetmyapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.luseen.introlib.BaseIntroActivity;
import com.luseen.introlib.FragmentChangeListener;
import com.luseen.introlib.FragmentItem;

public class MainActivity extends BaseIntroActivity implements FragmentChangeListener {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addFragment(new FragmentItem(R.layout.intro_first, ContextCompat.getColor(this, R.color.introFirstColor)));
        addFragment(new FragmentItem(R.layout.intro_second, ContextCompat.getColor(this, R.color.introSecondColor)));
        addFragment(new FragmentItem(R.layout.intro_third, ContextCompat.getColor(this, R.color.introThirdColor)));
        addFragment(new FragmentItem(R.layout.intro_fourth, ContextCompat.getColor(this, R.color.introFourthColor)));

        setVibrate(true);
        setNextImage(R.drawable.next_icon);
        setSkipText("SKIP");
        setDoneText("DONE");
        showSkipButton(true);
        setFragmentChangeListener(this);
    }

    @Override
    public void onDonePressed() {
        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlideChange(int position) {
        Log.e("onSlideChange ", " " + position);
    }
}
