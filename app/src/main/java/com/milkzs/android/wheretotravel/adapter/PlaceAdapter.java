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

    private Cursor mCursor;
    private Context context;
    private ClickTranform clickTranform;

    public PlaceAdapter(ClickTranform clickTranform) {
        this.clickTranform = clickTranform;
    }

    @Override
    public MyPlaceAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int List = R.layout.recycler_list_card_view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(List, parent, false);
        return new MyPlaceAdapterHolder(view);
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
        if (mCursor == null || mCursor.getCount() == 0) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * Swap data from main view
     */
    public void swapData(Cursor mCursor) {
        this.mCursor = mCursor;
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
            itemView.setOnClickListener(this);
            mainPic = itemView.findViewById(R.id.image_main);
            nameText = itemView.findViewById(R.id.text_view_name);
            summaryText = itemView.findViewById(R.id.text_view_summary);
            addressText = itemView.findViewById(R.id.text_view_address);
            timeText = itemView.findViewById(R.id.text_view_open_time);
            priceText = itemView.findViewById(R.id.text_view_price);
        }

        /**
         * Bind data to view list
         *
         * @param position data index of array list
         */
        public void bindToView(int position, Context context, Cursor cursor) {
            this.position = position;
//            placeListInfo = placeListInfos.get(position);
            // Picasso.with(context).load(placeListInfo.getMainPicUri()).into(mainPic);
            nameText.setText(
                    cursor.getString(
                            cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_NAME)));
            summaryText.setText(
                    cursor.getString(
                            cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_SUMMERY)));
            addressText.setText(
                    cursor.getString(
                            cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_ADDRESS)));
            timeText.setText(
                    cursor.getString(
                            cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_OPEN_TIME)));
            priceText.setText(
                    cursor.getString(
                            cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_PRICE)));

        }

        @Override
        public void onClick(View v) {
            clickTranform.onClick(position);
        }
    }

    public interface ClickTranform {
        void onClick(int position);
    }
}