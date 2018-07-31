package com.milkzs.android.wheretotravel.MainFragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by alan on 2018/7/31.
 */

public class MainViewPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayList;

    public MainViewPageAdapter(FragmentManager fm,ArrayList<Fragment> arrayList) {
        super(fm);
        this.arrayList =arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        if(arrayList == null){
            return 0;
        }
        return arrayList.size();
    }
}
