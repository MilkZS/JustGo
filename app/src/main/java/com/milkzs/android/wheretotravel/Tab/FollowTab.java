package com.milkzs.android.wheretotravel.Tab;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by milkdz on 2018/4/29.
 */

public class FollowTab extends LinearLayout {
    public FollowTab(Context context) {
        super(context);
    }

    public FollowTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

    }


}
