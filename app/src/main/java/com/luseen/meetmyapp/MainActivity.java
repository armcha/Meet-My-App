package com.luseen.meetmyapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.luseen.introlib.BaseIntroActivity;
import com.luseen.introlib.IntroFragment;
import com.luseen.introlib.FragmentItem;

public class MainActivity extends BaseIntroActivity {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addFragment(new FragmentItem(IntroFragment.newInstance(R.layout.first_layout), ContextCompat.getColor(this, R.color.color1)));
        addFragment(new FragmentItem(IntroFragment.newInstance(R.layout.second_layout), ContextCompat.getColor(this, R.color.color2)));
        addFragment(new FragmentItem(IntroFragment.newInstance(R.layout.first_layout), ContextCompat.getColor(this, R.color.color3)));
        addFragment(new FragmentItem(IntroFragment.newInstance(R.layout.four_layout), ContextCompat.getColor(this, R.color.color4)));

        setVibrate(true);
        setNextImage(R.drawable.next_icon);
        setSkipText("SKIP");
        setDoneText("DONE");
    }

    @Override
    public void onDonePressed() {
        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
    }
}
