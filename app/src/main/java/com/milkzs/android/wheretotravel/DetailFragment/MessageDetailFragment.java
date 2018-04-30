package com.milkzs.android.wheretotravel.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.squareup.picasso.Picasso;

/**
 * Created by milkdz on 2018/4/29.
 */

public class MessageDetailFragment extends Fragment {

    private PlaceListInfo placeListInfo;
    private ImageView mainPicImage;
    private TextView priceTextView;
    private TextView discountTextView;
    private TextView timeTextView;
    private TextView addressTextView;
    private TextView attentionTextView;

    public MessageDetailFragment() {
    }

    public static MessageDetailFragment newInstance(PlaceListInfo placeListInfo) {

        Bundle args = new Bundle();
        args.putParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_MESSAGE,placeListInfo);
        MessageDetailFragment fragment = new MessageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeListInfo = getArguments().getParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_MESSAGE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view_page_message,container,false);

        mainPicImage = view.findViewById(R.id.detail_image_title);
        priceTextView = view.findViewById(R.id.detail_text_view_price);
        discountTextView = view.findViewById(R.id.detail_text_view_discount);
        timeTextView = view.findViewById(R.id.detail_text_view_open_time);
        addressTextView = view.findViewById(R.id.detail_text_view_address);
        attentionTextView = view.findViewById(R.id.detail_text_view_attention);

        Picasso.with(view.getContext()).load(placeListInfo.getMainPicUri()).into(mainPicImage);
        priceTextView.setText(placeListInfo.getPrice());
        discountTextView.setText(placeListInfo.getDiscount());
        timeTextView.setText(placeListInfo.getOpenTime());
        addressTextView.setText(placeListInfo.getAddress());
        attentionTextView.setText(placeListInfo.getAttention());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
