package com.milkzs.android.wheretotravel.Task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/29.
 */

public class PictureTask extends AsyncTask<Void,Void,Void> {

    private ArrayList<Uri> arrayList;
    private Context context;

    public PictureTask(ArrayList<Uri> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {



        return null;
    }
}
