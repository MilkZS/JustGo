package com.milkzs.android.wheretotravel.zooImageImpl;

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
    private int tPosition = -1;

    public ZooViewPageAdapter(FragmentManager fm,ArrayList<Uri> arrayList,int position ) {
        super(fm);
        this.arrayList = arrayList;
        this.tPosition = position;
    }


    @Override
    public Fragment getItem(int position) {
        int po = (tPosition == -1)?position:tPosition;
        tPosition = -1;
        return  BigPictureFragment.newInstance(arrayList,po);
    }

    @Override
    public int getCount() {
        if(arrayList == null){
            return 0;
        }
        return arrayList.size();
    }
}
