package com.milkzs.android.wheretotravel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/22.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyPlaceAdapterHolder> {

    private String TAG = "PlaceAdapter";

    private ArrayList<PlaceListInfo> placeListInfos;
    private Cursor mCursor;
    private Context context;
    private ClickTranform clickTranform;
    private int mode = MODE_LIST;

    public final static int MODE_LIST = 0;
    public final static int MODE_LOG = 1;

    public PlaceAdapter(ClickTranform clickTranform, int mode) {
        this.clickTranform = clickTranform;
        this.mode = mode;
    }

    @Override
    public MyPlaceAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int List = layoutList(mode);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(List, parent, false);
        return new MyPlaceAdapterHolder(view);
    }

    /**
     * Get diff mode by mode
     *
     * @param mode
     * @return
     */
    private int layoutList(int mode) {
        switch (mode) {
            case MODE_LIST: {
                return R.layout.recycler_list_card_view;
            }
            case MODE_LOG: {
                return R.layout.recycler_list_log;
            }
        }
        return R.layout.recycler_list_card_view;
    }

    @Override
    public void onBindViewHolder(MyPlaceAdapterHolder holder, int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            Log.d(TAG, "run here test and position is " + position);
        }
        holder.bindToView(position, context, mCursor);
    }

    @Override
    public int getItemCount() {
        if (mode == MODE_LIST) {
            if (placeListInfos != null) {
                return placeListInfos.size();
            }
        } else if (mode == MODE_LOG) {
            if (mCursor != null) {
                Log.d(TAG, "recycler cursor count is " + mCursor.getCount());
                return mCursor.getCount();
            }
        }
        return 0;
    }

    /**
     * Swap data from main view
     *
     * @param arrayList
     */
    public void swapData(ArrayList<PlaceListInfo> arrayList, Cursor mCursor, int mode) {
        if (mode == MODE_LIST) {
            this.placeListInfos = arrayList;
        } else if (mode == MODE_LOG) {
            this.mCursor = mCursor;
        }
        notifyDataSetChanged();
    }

    class MyPlaceAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mainPic;
        private TextView nameText;
        private TextView summaryText;
        private TextView addressText;
        private TextView timeText;
        private TextView priceText;
        private int position;
        private PlaceListInfo placeListInfo;

        private TextView log_time;
        private TextView log_name;

        public MyPlaceAdapterHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        /**
         * init view by mode
         *
         * @param itemView
         */
        private void initView(View itemView) {
            switch (mode) {
                case MODE_LIST: {
                    itemView.setOnClickListener(this);
                    mainPic = itemView.findViewById(R.id.image_main);
                    nameText = itemView.findViewById(R.id.text_view_name);
                    summaryText = itemView.findViewById(R.id.text_view_summary);
                    addressText = itemView.findViewById(R.id.text_view_address);
                    timeText = itemView.findViewById(R.id.text_view_open_time);
                    priceText = itemView.findViewById(R.id.text_view_price);
                }
                break;
                case MODE_LOG: {
                    log_time = itemView.findViewById(R.id.log_time);
                    log_name = itemView.findViewById(R.id.log_name);
                }
                break;
            }
        }

        /**
         * Bind data to view list
         *
         * @param position data index of array list
         */
        public void bindToView(int position, Context context, Cursor cursor) {
            this.position = position;

            switch (mode) {
                case MODE_LIST: {
                    placeListInfo = placeListInfos.get(position);
                    Picasso.with(context).load(placeListInfo.getMainPicUri()).into(mainPic);
                    nameText.setText(placeListInfo.getPlaceName());
                    summaryText.setText(placeListInfo.getSummary());
                    addressText.setText(placeListInfo.getAddress());
                    timeText.setText(placeListInfo.getOpenTime());
                    priceText.setText(placeListInfo.getPrice());
                }
                break;
                case MODE_LOG: {
                    Log.d(TAG, "================================================");
                    Log.d(TAG, "each cursor time content is " + cursor.getString(
                            cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_NAME)));
                    Log.d(TAG, "cursor content id is " + cursor.getString(
                            cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_ID)));
                    Log.d(TAG, "================================================");
                    log_name.setText(cursor.getString(
                            cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_NAME)));

                    log_time.setText(cursor.getString(
                            cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME)));
                }
                break;
            }
        }

        @Override
        public void onClick(View v) {
            clickTranform.onClick(position, placeListInfo);
        }
    }

    public interface ClickTranform {
        void onClick(int position, PlaceListInfo placeListInfo);
    }
}