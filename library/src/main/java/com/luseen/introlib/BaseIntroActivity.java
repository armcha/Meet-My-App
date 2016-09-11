package com.luseen.introlib;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Chatikyan on 05.01.2016.
 */
public abstract class BaseIntroActivity extends AppCompatActivity {

    public static final int DEFAULT_ANIMATION_DURATION = 200;

    private final float SELECTED_SCALE = 1.3f;

    private final float UN_SELECTED_SCALE = 0.9f;

    private PagerAdapter mPagerAdapter;

    private ViewPager viewPager;

    private List<Fragment> fragments = new Vector<>();

    private List<ImageView> dots = new ArrayList<>();

    private List<Integer> backgroundColors = new ArrayList<>();

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private Vibrator mVibrator;

    private LinearLayout dotContainer;

    private TextView skipTextView, doneTextView;

    private ImageView nextImageView;

    private int currentSelectedItem;

    private int selectedDotColor;

    private int unSelectedDotColor;

    private int slidesNumber;

    private int vibrateIntensity = 20;

    private boolean isVibrateOn = false;

    private boolean isSkipShown = true;


    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.default_layout);

        initColors();

        setUpViews();

        setUpViewPager();

        init(savedInstanceState);

        slidesNumber = fragments.size();

        addDots(slidesNumber);

        if (slidesNumber == 1) {
            Utils.hideView(nextImageView, skipTextView);
            Utils.showView(doneTextView);
        }
    }

    @Override
    public boolean onKeyDown(int code, KeyEvent keyEvent) {
        if (code == KeyEvent.KEYCODE_ENTER || code == KeyEvent.KEYCODE_BUTTON_A) {
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
                onDonePressed();
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            return false;
        }
        return super.onKeyDown(code, keyEvent);
    }

    private void initColors() {
        selectedDotColor = ContextCompat.getColor(this, R.color.selected_dot_color);
        unSelectedDotColor = ContextCompat.getColor(this, R.color.un_selected_dot_color);
    }

    private void setUpViews() {
        nextImageView = (ImageView) findViewById(R.id.next_image_view);
        doneTextView = (TextView) findViewById(R.id.done);
        skipTextView = (TextView) findViewById(R.id.skip);
        dotContainer = (LinearLayout) findViewById(R.id.dot_container);
        RelativeLayout bottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
            params.setMargins(0, 0, 0, Utils.getNavBarWidth(this));
            bottomView.setLayoutParams(params);
        }

        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVibrateOn) {
                    mVibrator.vibrate(vibrateIntensity);
                }
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVibrateOn) {
                    mVibrator.vibrate(vibrateIntensity);
                }
                viewPager.setCurrentItem(slidesNumber);
            }
        });

        doneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if (isVibrateOn) {
                    mVibrator.vibrate(vibrateIntensity);
                }
                onDonePressed();
            }
        });
    }

    private void setUpViewPager() {
        mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(this.mPagerAdapter);
        viewPager.setPageTransformer(false,new ParallaxPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == slidesNumber - 1) {
                    Utils.hideView(skipTextView, nextImageView);
                    Utils.showView(doneTextView);
                } else {
                    Utils.showView(skipTextView, nextImageView);
                    Utils.hideView(doneTextView);
                }
                if (!isSkipShown) {
                    Utils.showView(skipTextView);
                }

                selectDot(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (mPagerAdapter.getCount() - 1) && position < (backgroundColors.size() - 1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(
                            positionOffset, backgroundColors.get(position), backgroundColors.get(position + 1)));
                } else {
                    viewPager.setBackgroundColor(backgroundColors.get(backgroundColors.size() - 1));
                }
            }
        });
    }

    /**
     * add dots according count
     *
     * @param count dots count
     */
    @SuppressLint("InflateParams")
    private void addDots(int count) {
        dots = new ArrayList<>();
        dotContainer.removeAllViews();
        dots.clear();
        for (int i = 0; i < count; i++) {
            ImageView dot = (ImageView) getLayoutInflater().inflate(R.layout.dot_layout, null);
            if (i == 0) {
                dot.setImageResource(R.drawable.dot);
                dot.setColorFilter(selectedDotColor, PorterDuff.Mode.SRC_IN);
                Utils.changeViewScale(dot, SELECTED_SCALE);
            } else {
                dot.setImageResource(R.drawable.dot);
                dot.setColorFilter(unSelectedDotColor, PorterDuff.Mode.SRC_IN);
                Utils.changeViewScale(dot, UN_SELECTED_SCALE);
            }
            dotContainer.addView(dot);
            dots.add(dot);
        }
    }

    /**
     * select dot by index
     *
     * @param index select dot
     */
    private void selectDot(int index) {
        for (int i = 0; i < dots.size(); i++) {
            if (i == index) {
                final ImageView currentDot = dots.get(i);
                currentDot.clearColorFilter();
                Utils.animateViewWithScale(currentDot, SELECTED_SCALE);
                Utils.changeImageViewTintWithAnimation(currentDot, unSelectedDotColor, selectedDotColor);
            } else if (i == currentSelectedItem) {
                final ImageView currentDot = dots.get(i);
                currentDot.clearColorFilter();
                Utils.animateViewWithScale(currentDot, UN_SELECTED_SCALE);
                Utils.changeImageViewTintWithAnimation(currentDot, selectedDotColor, unSelectedDotColor);
            }
        }

        currentSelectedItem = index;
    }

    public abstract void init(@Nullable Bundle savedInstanceState);

    public abstract void onDonePressed();

    public void addFragment(@NonNull Fragment fragment, @ColorInt int backgroundColor) {
        fragments.add(fragment);
        backgroundColors.add(backgroundColor);
        mPagerAdapter.notifyDataSetChanged();
    }

    public void showSkipButton(boolean showButton) {
        this.isSkipShown = showButton;
        if (!showButton) {
            TextView skip = (TextView) findViewById(R.id.skip);
            skip.setVisibility(View.INVISIBLE);
        }
    }

    public void setVibrate(boolean vibrate) {
        this.isVibrateOn = vibrate;
    }

    public void setVibrateIntensity(int intensity) {
        this.vibrateIntensity = intensity;
    }

    public void setSkipText(String skipText) {
        Utils.showView(skipTextView);
        skipTextView.setText(skipText);
    }

    public void setNextImage(int nextImage) {
        Utils.showView(nextImageView);
        nextImageView.setImageResource(nextImage);
    }

    public void setDoneText(String doneText) {
        doneTextView.setText(doneText);
    }
}
