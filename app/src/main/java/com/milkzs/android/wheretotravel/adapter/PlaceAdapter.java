package com.milkzs.android.wheretotravel.adapter;

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
 * Created by milkdz on 2018/4/22.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyPlaceAdapterHolder> {

    private ArrayList<PlaceListInfo> placeListInfos;
    private Context context;

    @Override
    public MyPlaceAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int recyclerList = R.layout.recycler_list_card_view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(recyclerList,parent,false);
        return new MyPlaceAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPlaceAdapterHolder holder, int position) {
        holder.bindToView(position,context);
    }

    @Override
    public int getItemCount() {
        if(placeListInfos == null){
            return 0;
        }
        return placeListInfos.size();
    }

    /**
     * Swap data from main view
     *
     * @param arrayList
     */
    public void swapData(ArrayList<PlaceListInfo> arrayList){
        this.placeListInfos = arrayList;
        notifyDataSetChanged();
    }

    class MyPlaceAdapterHolder extends RecyclerView.ViewHolder{

        private ImageView mainPic;
        private TextView nameText;
        private TextView summaryText;
        private TextView addressText;
        private TextView timeText;
        private TextView priceText;

        public MyPlaceAdapterHolder(View itemView) {
            super(itemView);

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
        public void bindToView(int position,Context context){
            PlaceListInfo placeListInfo = placeListInfos.get(position);
            Picasso.with(context).load(placeListInfo.getMainPicUri()).into(mainPic);
            nameText.setText(placeListInfo.getPlaceName());
            summaryText.setText(placeListInfo.getSummary());
            addressText.setText(placeListInfo.getAddress());
            timeText.setText(placeListInfo.getOpenTime());
            priceText.setText(placeListInfo.getPrice());
        }
    }
}
