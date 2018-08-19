package com.milkzs.android.wheretotravel;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.milkzs.android.wheretotravel.MainFragment.HomeFragment;
import com.milkzs.android.wheretotravel.MainFragment.LogRecordFragment;
import com.milkzs.android.wheretotravel.MainFragment.PictureFragment;
import com.milkzs.android.wheretotravel.MainFragment.SceneMainFragment;
import com.milkzs.android.wheretotravel.MainFragment.adapter.MainViewPageAdapter;
import com.milkzs.android.wheretotravel.Task.SceneSyncThread;

import java.util.ArrayList;


public class PlaceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_main_page);
        SceneSyncThread.initialize(getApplicationContext());
        init();
    }

    private void init(){

        ViewPager viewPager = findViewById(R.id.view_page_main);
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(HomeFragment.newInstance());
        arrayList.add(PictureFragment.newInstance());
        arrayList.add(LogRecordFragment.newInstance());
        arrayList.add(SceneMainFragment.newInstance());
        MainViewPageAdapter adapter = new MainViewPageAdapter(getSupportFragmentManager(),arrayList);
        viewPager.setAdapter(adapter);

        TabLayout tableLayout = findViewById(R.id.tab_layout_main);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setupWithViewPager(viewPager);

        tableLayout.getTabAt(0).setIcon(R.drawable.home);
        tableLayout.getTabAt(1).setIcon(R.drawable.picture);
        tableLayout.getTabAt(2).setIcon(R.drawable.log);
        tableLayout.getTabAt(3).setIcon(R.drawable.person);
    }
}
