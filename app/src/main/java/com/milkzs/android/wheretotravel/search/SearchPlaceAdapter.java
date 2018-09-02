package com.milkzs.android.wheretotravel.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alan on 2018/9/2.
 */

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.MySearchPlaceHolder> {

    private ArrayList<PlaceListInfo> arrayList;
    private Context context;
    @Override
    public MySearchPlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int List = R.layout.recycler_list_card_view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(List, parent, false);
        return new SearchPlaceAdapter.MySearchPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(MySearchPlaceHolder holder, int position) {
        holder.bindToView(position);
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return  arrayList.size();
        }
        return 0;
    }

    class MySearchPlaceHolder extends RecyclerView.ViewHolder{

        private ImageView mainPic;
        private TextView nameText;
        private TextView summaryText;
        private TextView addressText;
        private TextView timeText;
        private TextView priceText;

        public MySearchPlaceHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        /**
         * init view by mode
         *
         * @param itemView
         */
        private void initView(View itemView) {
            //itemView.setOnClickListener(this);
            mainPic = itemView.findViewById(R.id.image_main);
            nameText = itemView.findViewById(R.id.text_view_name);
            summaryText = itemView.findViewById(R.id.text_view_summary);
            addressText = itemView.findViewById(R.id.text_view_address);
            timeText = itemView.findViewById(R.id.text_view_open_time);
            priceText = itemView.findViewById(R.id.text_view_price);
        }

        public void bindToView(int position){
            PlaceListInfo placeListInfo = arrayList.get(position);
            Picasso.with(context).load(placeListInfo.getMainPicUri()).into(mainPic);
            nameText.setText(placeListInfo.getPlaceName());
            summaryText.setText(placeListInfo.getSummary());
            addressText.setText(placeListInfo.getAddress());
            timeText.setText(placeListInfo.getOpenTime());
            priceText.setText(placeListInfo.getPrice());
        }

    }

    public void swap(ArrayList<PlaceListInfo> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
