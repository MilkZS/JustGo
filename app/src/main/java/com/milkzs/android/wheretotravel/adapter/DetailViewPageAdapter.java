package com.milkzs.android.wheretotravel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/28.
 */

public class DetailViewPageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> viewArrayList;

    public DetailViewPageAdapter(FragmentManager fm,ArrayList<Fragment> viewArrayList) {
        super(fm);
        this.viewArrayList = viewArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return viewArrayList.get(position);
    }

    @Override
    public int getCount() {
        if(viewArrayList == null){
            return 0;
        }
        return viewArrayList.size();
    }
}
