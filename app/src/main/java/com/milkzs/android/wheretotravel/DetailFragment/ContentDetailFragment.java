package com.milkzs.android.wheretotravel.DetailFragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.db.PlaceContract;

/**
 * Created by milkdz on 2018/4/29.
 */

public class ContentDetailFragment extends Fragment{

    private int sceneId;

    public ContentDetailFragment() {
    }

    public static ContentDetailFragment newInstance(int sceneId) {

        Bundle args = new Bundle();
        args.putInt(BaseInfo.IntentFlag.FLAG_FRAGMENT_CONTENT, sceneId);
        ContentDetailFragment fragment = new ContentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sceneId = getArguments().getInt(BaseInfo.IntentFlag.FLAG_FRAGMENT_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.detail_view_page_content, container, false);

        TextView summaryTextView = view.findViewById(R.id.detail_text_view_summary);
        TextView contentTextView = view.findViewById(R.id.detail_text_view_content);

        Uri uri = PlaceContract.SceneBase.CONTENT_BASE;
        String sel = PlaceContract.SceneBase._ID + "=" + sceneId;
        Cursor cursor = getActivity().getContentResolver().query(uri,new String[]{"*"},sel,null,null);

        if (cursor == null || cursor.getCount() == 0){
            return view;
        }
        cursor.moveToLast();


        summaryTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_SUMMERY)));
        contentTextView.setText(
                cursor.getString(cursor.getColumnIndex(PlaceContract.SceneBase.COLUMN_SCENE_CONTENT)));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
