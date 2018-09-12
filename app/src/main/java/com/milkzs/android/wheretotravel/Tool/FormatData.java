package com.milkzs.android.wheretotravel.Tool;

import android.content.Context;
import android.util.Log;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by milkdz on 2018/4/22.
 */

public class FormatData {

    private static String TAG = "FormatData";

    /**
     * format price such as "元/人"
     *
     * @param price
     * @param context
     * @return
     */
    public static String formatPrice(String price, Context context) {
        if (price == null || price.equals("") || price.equals(BaseInfo.ERROR_SHOW)) {
            return context.getResources().getString(R.string.format_price_null);
        }
        return price + context.getResources().getString(R.string.format_price);
    }

    public static String formatTime(String time, Context context) {
        if (time == null || time.equals("") || time.equals(BaseInfo.ERROR_SHOW)) {
            return context.getResources().getString(R.string.NONE_TIME);
        }
        time = time.trim();
        Log.d(TAG,"format time1 is => " + time);
        time = time.replaceAll("&mdash;","-");
        Log.d(TAG,"format time2 is => " + time);
        time.replaceAll(";","\n");
        return time.trim();
    }

    public static String formatNone(String param, Context context) {
        switch (param) {
            case BaseInfo.CONTENT_LIST_DISCOUNT: {
                Log.d(TAG, "go into first");
                return context.getResources().getString(R.string.NONE_DISCOUNT);
            }
            case BaseInfo.CONTENT_LIST_ATTENTION: {
                Log.d(TAG, "go into second");
                return context.getResources().getString(R.string.NONE_ATTENTION);
            }
        }
        Log.d(TAG, "error");
        return BaseInfo.ERROR_SHOW;
    }

    /**
     * format string for tab
     *
     * @param context
     * @return
     */
    public static ArrayList<String> formatTabString(Context context) {
        String s = context.getResources().getString(R.string.tab_string_array_list);
        String[] spits = s.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(spits));
        return arrayList;
    }

    /**
     * format history show
     *
     * @param time1
     * @param time2
     * @param name
     * @return
     */
    public static String formatHistoryShow(String time1, String time2, String name) {
        return time1 + " ------ " + time2 + "\t\t" + name;
    }

}
