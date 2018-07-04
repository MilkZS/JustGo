package com.milkzs.android.wheretotravel.ZooImageImpl;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ZooImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_image);

        Intent intent = getIntent();
        int position = intent.getIntExtra(BaseInfo.IntentFlag.FLAG_PICTURE_POSITION,0);
        ArrayList<Uri> list = intent.getParcelableArrayListExtra(BaseInfo.IntentFlag.FLAG_PICTURE_LIST);

        ZooViewPageAdapter zooViewPageAdapter = new ZooViewPageAdapter(getSupportFragmentManager(),list,position);
        ViewPager viewPager = findViewById(R.id.view_page_zoo_picture);
        viewPager.setAdapter(zooViewPageAdapter);
        viewPager.setPageTransformer(true,new ZooPictureTransform());


        //ImageView imageView = findViewById(R.id.zoo_image_view);
        //Picasso.with(this).load(list.get(position)).into(imageView);
    }
}
