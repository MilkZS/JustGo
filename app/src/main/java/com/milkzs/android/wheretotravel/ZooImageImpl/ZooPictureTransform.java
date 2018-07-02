package com.milkzs.android.wheretotravel.ZooImageImpl;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by alan on 2018/7/2.
 */

public class ZooPictureTransform implements ViewPager.PageTransformer {

    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        int height = page.getHeight();
        int width = page.getWidth();

        if(position < -1){
            page.setAlpha(0);
        }else if(position <= 1){
            float scalefactor = Math.max(MIN_SCALE,1-Math.abs(position));
            float VScale = height*(1-scalefactor)/2;
            float Hwidth = width*(1-scalefactor)/2;
            if(position < 0){
                page.setTranslationX(Hwidth - VScale/2);
            }else{
                page.setTranslationX(-Hwidth + VScale/2);
            }

            page.setScaleX(scalefactor);
            page.setScaleY(scalefactor);

            page.setAlpha(MIN_ALPHA + (scalefactor - MIN_ALPHA)/(1-MIN_ALPHA)*(1-MIN_SCALE));
        }else{
            page.setAlpha(0);
        }
    }
}
