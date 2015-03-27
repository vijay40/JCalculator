package com.example.i310588.helloworld;

import android.app.Activity;
import android.support.v4.view.ViewPager;

/**
 * Created by I310588 on 3/22/2015.
 */
public class ViewPageListener implements ViewPager.OnPageChangeListener {
    Activity activity;

    public ViewPageListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        this.activity.getActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
