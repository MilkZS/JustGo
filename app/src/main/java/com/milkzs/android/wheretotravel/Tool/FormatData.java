package com.milkzs.android.wheretotravel.Tool;

import android.content.Context;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;


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
        if(price.equals("") || price == null || price.equals(BaseInfo.ERROR_SHOW)){
            return context.getResources().getString(R.string.format_price_null);
        }
        return price + context.getResources().getString(R.string.format_price);
    }

}
