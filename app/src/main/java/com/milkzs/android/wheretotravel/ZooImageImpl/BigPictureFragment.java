package com.milkzs.android.wheretotravel.ZooImageImpl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.milkzs.android.wheretotravel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BigPictureFragment extends Fragment {

    private static final String ARG_PARAM1 = "param_list";
    private static final String ARG_PARAM2 = "param_position";

    private ArrayList<Uri> arrayList;
    private int position;

    public BigPictureFragment() {
    }

    /**
     *
     * @param arrayList
     * @param position
     * @return
     */
    public static BigPictureFragment newInstance(ArrayList<Uri> arrayList,int position) {
        BigPictureFragment fragment = new BigPictureFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,arrayList);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_big_picture, container, false);

        ImageView imageView = view.findViewById(R.id.big_picture_fragment_image_view);
        Picasso.with(view.getContext()).load(arrayList.get(position)).into(imageView);
        return view;
    }

}
