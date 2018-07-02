package com.milkzs.android.wheretotravel.ZooImageImpl;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by alan on 2018/7/1.
 */

public class ZooViewPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Uri> arrayList;

    public ZooViewPageAdapter(FragmentManager fm,ArrayList<Uri> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }


    @Override
    public Fragment getItem(int position) {
        return  BigPictureFragment.newInstance(arrayList,position);
    }

    @Override
    public int getCount() {
        if(arrayList == null){
            return 0;
        }
        return arrayList.size();
    }
}
