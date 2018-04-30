package com.milkzs.android.wheretotravel.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;

/**
 * Created by milkdz on 2018/4/29.
 */

public class ContentDetailFragment extends Fragment {

    private PlaceListInfo placeListInfo;
    private TextView summaryTextView;
    private TextView contentTextView;

    public ContentDetailFragment() {
    }

    public static ContentDetailFragment newInstance(PlaceListInfo placeListInfo) {

        Bundle args = new Bundle();
        args.putParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_CONTENT, placeListInfo);
        ContentDetailFragment fragment = new ContentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeListInfo = getArguments().getParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.detail_view_page_content, container, false);

        summaryTextView = view.findViewById(R.id.detail_text_view_summary);
        contentTextView = view.findViewById(R.id.detail_text_view_content);

        summaryTextView.setText(placeListInfo.getSummary());
        contentTextView.setText(placeListInfo.getDetailContent());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
