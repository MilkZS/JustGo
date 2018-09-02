package com.milkzs.android.wheretotravel.DetailFragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.Tool.APKTools;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.squareup.picasso.Picasso;

/**
 * Created by milkdz on 2018/4/29.
 */

public class MessageDetailFragment extends Fragment {

    private int sceneId;

    public MessageDetailFragment() {
    }

    public static MessageDetailFragment newInstance(int sceneId) {

        Bundle args = new Bundle();
        args.putInt(BaseInfo.IntentFlag.FLAG_FRAGMENT_MESSAGE, sceneId);
        MessageDetailFragment fragment = new MessageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            sceneId = getArguments().getInt(BaseInfo.IntentFlag.FLAG_FRAGMENT_MESSAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_view_page_message, container, false);

        ImageView mainPicImage = view.findViewById(R.id.detail_image_title);
        TextView priceTextView = view.findViewById(R.id.detail_text_view_price);
        TextView discountTextView = view.findViewById(R.id.detail_text_view_discount);
        TextView timeTextView = view.findViewById(R.id.detail_text_view_open_time);
        TextView addressTextView = view.findViewById(R.id.detail_text_view_address);
        TextView attentionTextView = view.findViewById(R.id.detail_text_view_attention);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.detail_message_fba);

        Uri uri = PlaceContract.SceneBase.CONTENT_BASE;
        String sel = PlaceContract.SceneBase._ID + "=" + sceneId;
        final Cursor cursor =
                getActivity().getContentResolver().query(uri, new String[]{"*"}, sel, null, null);

        cursor.moveToLast();

        if (cursor.getCount() == 0){
            return view;
        }

        String picUri =
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_MAIN_PIC));
        Picasso.with(view.getContext()).load(picUri).into(mainPicImage);
        priceTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_PRICE)));
        discountTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_DISCOUNT)));
        timeTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_OPEN_TIME)));
        addressTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_ADDRESS)));
        attentionTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_ATTENTION)));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseLocationMap(view.getContext(),
                        cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LON)),
                        cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_LOCATION_LAT)));
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
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.package_no_activity),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
