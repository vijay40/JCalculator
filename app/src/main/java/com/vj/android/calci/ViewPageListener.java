package com.vj.android.calci;

import android.app.Activity;
import android.support.v4.view.ViewPager;

class ViewPageListener implements ViewPager.OnPageChangeListener {
    private Activity activity;

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
