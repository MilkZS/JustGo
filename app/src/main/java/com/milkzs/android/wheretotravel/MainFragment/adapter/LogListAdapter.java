package com.milkzs.android.wheretotravel.MainFragment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.squareup.picasso.Picasso;

/**
 * Created by alan on 2018/8/19.
 */

public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.LogListHolder> {

    private Cursor mCursor;
    private Context context;

    @Override
    public LogListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        int layoutIndex = R.layout.recycler_list_card_view_log;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIndex, null, false);
        return new LogListHolder(view);
    }

    @Override
    public void onBindViewHolder(LogListHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public class LogListHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTextView;
        private TextView arriveTimeTextView;
        private TextView leaveTimeTextView;

        public LogListHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_fragment_log_img);
            titleTextView = itemView.findViewById(R.id.main_fragment_log_title);
            arriveTimeTextView = itemView.findViewById(R.id.main_fragment_log_arrive_time);
            leaveTimeTextView = itemView.findViewById(R.id.main_fragment_log_leave_time);
        }

        public void bindData(int position) {
            if (mCursor == null) {
                return;
            }
            mCursor.moveToPosition(position);

            Uri uri = Uri.parse(
                    mCursor.getString(
                            mCursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_PIC)));
            if (uri.toString().equals("")) {
                Picasso.with(context)
                        .load(R.drawable.add_pic_but)
                        .into(imageView);
            } else {
                Picasso.with(context)
                        .load(uri)
                        .into(imageView);
            }
            titleTextView.setText(
                    mCursor.getString(
                            mCursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_NAME)));
            arriveTimeTextView.setText(
                    mCursor.getString(
                            mCursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME)));
            leaveTimeTextView.setText(
                    mCursor.getString(
                            mCursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO)));
        }
    }

    public void swap(Cursor mCursor) {
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }
}
