package com.milkzs.android.wheretotravel.DetailFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.adapter.MorePicturesAdapter;

import java.util.ArrayList;


public class PicturesDetailFragment extends Fragment {

    private PlaceListInfo placeListInfo;
    private ArrayList<Uri> uriArrayList;

    public PicturesDetailFragment() {
    }

    public static PicturesDetailFragment newInstance(PlaceListInfo placeListInfo) {
        PicturesDetailFragment fragment = new PicturesDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_PICTURES,placeListInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeListInfo = getArguments().getParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_PICTURES);
            uriArrayList = placeListInfo.getPicListSmallUrl();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_view_page_picture,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_detail_picture);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                view.getContext(), getResources().getInteger(R.integer.grid_layout_picture_detail));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        MorePicturesAdapter morePicturesAdapter = new MorePicturesAdapter();
        recyclerView.setAdapter(morePicturesAdapter);
        morePicturesAdapter.swapData(uriArrayList);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
