package com.milkzs.android.wheretotravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.ZooImageImpl.ZooImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/29.
 */

public class MorePicturesAdapter extends RecyclerView.Adapter<MorePicturesAdapter.PicturesHolder> {

    private String TAG = "MorePicturesAdapter";
    private boolean DBG = true;
    private ArrayList<Uri> picUriArray;
    private Context context;
    private int position;
    private ClickPicture clickPicture;

    public MorePicturesAdapter() {
    }

    public MorePicturesAdapter(ClickPicture clickPicture) {
        this.clickPicture = clickPicture;
    }

    @Override
    public PicturesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int recyclerIndex = R.layout.recycler_grid_pictures;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(recyclerIndex,parent,false);
        return new PicturesHolder(v);
    }

    @Override
    public void onBindViewHolder(PicturesHolder holder, int position) {
        this.position = position;
        holder.bindUI(position);
    }

    @Override
    public int getItemCount() {
        if(picUriArray == null){
            return 0;
        }
        return picUriArray.size();
    }

    class PicturesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        public PicturesHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_more_pic);
            imageView.setOnClickListener(this);
        }

        /**
         * bind data to UI
         *
         * @param position
         */
        public void bindUI(int position){
            if(picUriArray.get(position).equals("")){
                return;
            }
            Picasso.with(context)
                    .load(picUriArray.get(position))
                    .error(R.drawable.error_pic)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context.getApplicationContext(),getAdapterPosition()+"",Toast.LENGTH_SHORT).show();
           // clickPicture.onClick(position);
            Intent intent = new Intent(context.getApplicationContext(), ZooImageActivity.class);
            intent.putExtra(BaseInfo.IntentFlag.FLAG_PICTURE_POSITION,getAdapterPosition());
            intent.putExtra(BaseInfo.IntentFlag.FLAG_PICTURE_LIST,picUriArray);
            context.startActivity(intent);
        }
    }

    public interface ClickPicture{
        void onClick(int position);
    }

    /**
     * Swap data and notify
     *
     * @param picUriArray
     */
    public void swapData(ArrayList<Uri> picUriArray){
        this.picUriArray = picUriArray;
        notifyDataSetChanged();
    }
}
