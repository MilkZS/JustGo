package com.milkzs.android.wheretotravel.Tool;

import android.content.Context;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by milkdz on 2018/4/22.
 */

public class FormatData {
    /**
     * format price such as "元/人"
     *
     * @param price
     * @param context
     * @return
     */
    public static String formatPrice(String price, Context context){
        if(price == null || price.equals("") || price.equals(BaseInfo.ERROR_SHOW)){
            return context.getResources().getString(R.string.format_price_null);
        }
        return price + context.getResources().getString(R.string.format_price);
    }

    /**
     * format string for tab
     *
     * @param context
     * @return
     */
    public static ArrayList<String> formatTabString(Context context){
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
    public static String formatHistoryShow(String time1,String time2,String name){
        return time1 + " ------ " + time2 + "\t\t" + name;
    }

}
