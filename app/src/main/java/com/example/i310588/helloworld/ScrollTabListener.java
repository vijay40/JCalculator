package com.example.i310588.helloworld;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * Created by I310588 on 3/22/2015.
 */
public class ScrollTabListener implements ActionBar.TabListener {

    Activity activity;
    private ViewPager viewPager;

    public ScrollTabListener(Activity activity){
        this.activity = activity;
        viewPager = (ViewPager) this.activity.findViewById(R.id.pad);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
