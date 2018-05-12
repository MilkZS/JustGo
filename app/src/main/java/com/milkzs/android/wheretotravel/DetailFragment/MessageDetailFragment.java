package com.milkzs.android.wheretotravel.DetailFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.Tool.APKTools;
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
    private FloatingActionButton floatingActionButton;

    public MessageDetailFragment() {
    }

    public static MessageDetailFragment newInstance(PlaceListInfo placeListInfo) {

        Bundle args = new Bundle();
        args.putParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_MESSAGE, placeListInfo);
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
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_view_page_message, container, false);

        mainPicImage = view.findViewById(R.id.detail_image_title);
        priceTextView = view.findViewById(R.id.detail_text_view_price);
        discountTextView = view.findViewById(R.id.detail_text_view_discount);
        timeTextView = view.findViewById(R.id.detail_text_view_open_time);
        addressTextView = view.findViewById(R.id.detail_text_view_address);
        attentionTextView = view.findViewById(R.id.detail_text_view_attention);
        floatingActionButton = view.findViewById(R.id.detail_message_fba);

        Picasso.with(view.getContext()).load(placeListInfo.getMainPicUri()).into(mainPicImage);
        priceTextView.setText(placeListInfo.getPrice());
        discountTextView.setText(placeListInfo.getDiscount());
        timeTextView.setText(placeListInfo.getOpenTime());
        addressTextView.setText(placeListInfo.getAddress());
        attentionTextView.setText(placeListInfo.getAttention());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseLocationMap(view.getContext(),
                        placeListInfo.getLocation_lon(), placeListInfo.getLocation_lat());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Open location map APP
     *
     * @param context
     * @param x
     * @param y
     */
    private void choseLocationMap(Context context, String x, String y) {
        if (APKTools.checkApkExist(context, context.getResources()
                .getString(R.string.package_baidu_map))) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(BaseInfo.OpenLocationMap.BAI_DU_MAP + x + "," + y
                    + BaseInfo.OpenLocationMap.BAI_DU_MAP_INFO));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
                return;
            }
        }

        if (APKTools.checkApkExist(context, context.getResources()
                .getString(R.string.package_gaode_map))) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(BaseInfo.OpenLocationMap.GAO_DE_MAP
                    + BaseInfo.OpenLocationMap.GAO_DE_MAP_LON + x
                    + BaseInfo.OpenLocationMap.GAO_DE_MAP_LAT + y
                    + BaseInfo.OpenLocationMap.GAO_DE_MAP_INFO));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
                return;
            }
        }

        Intent intent = new Intent();
        intent.setData(Uri.parse(BaseInfo.OpenLocationMap.GOOGLE_MAP + x + "," + y));
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(intent);
        }else {
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.package_no_activity),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
