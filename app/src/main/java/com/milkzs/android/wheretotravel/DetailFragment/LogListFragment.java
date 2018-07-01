package com.milkzs.android.wheretotravel.DetailFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.Base.DateTime;
import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.Tool.FormatData;
import com.milkzs.android.wheretotravel.db.PlaceContract;

import java.util.Calendar;


public class LogListFragment extends Fragment {

    private String TAG = "LogListFragment";

    private PlaceListInfo placeListInfo;

    private EditText arriveTime;
    private EditText leaveTime;
    private TextView historyTime;
    private Boolean ifFirst = false;
    private Cursor cursor;

    public LogListFragment() {
    }

    public static LogListFragment newInstance(PlaceListInfo placeListInfo) {
        LogListFragment fragment = new LogListFragment();
        Bundle args = new Bundle();
        args.putParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_LOG, placeListInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            placeListInfo = getArguments().getParcelable(BaseInfo.IntentFlag.FLAG_FRAGMENT_LOG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_view_page_log_list, container, false);

        arriveTime = view.findViewById(R.id.log_time_edit);
        leaveTime = view.findViewById(R.id.log_time_edit_go);
        historyTime = view.findViewById(R.id.textView_show_history);
        Button button_save = view.findViewById(R.id.button_save);
        Button button_leave = view.findViewById(R.id.button_leave);


        final String name = placeListInfo.getPlaceName();
        final String sId = placeListInfo.getsId();

        Uri uri = PlaceContract.PlaceBase.CONTENT_BASE;
        Log.d(TAG, "query place id is " + sId);
        cursor = getContext().getContentResolver().query(
                uri,
                PlaceContract.PlaceBase.QUERY_ENTRY,
                null,
                null,
                null);
        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null) {
                Log.d(TAG, "cursor length is " + cursor.getCount());
            }
            historyTime.setText(view.getContext().getResources().getString(R.string.log_none));
            ifFirst = true;
        } else {
            ifFirst = false;
            cursor.moveToPosition(cursor.getCount() - 1);
            String timeArr = cursor.getString(
                    cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME));
            String timeGo = cursor.getString(
                    cursor.getColumnIndex(PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO));

            arriveTime.setText(timeArr);
            leaveTime.setText("");
            historyTime.setText(FormatData.formatHistoryShow(timeArr, timeGo, name));
        }

        final DateTime[] dateTime = new DateTime[2];
        arriveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "click", Toast.LENGTH_SHORT).show();
                arriveTime.setText("");
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                dateTime[0] = new DateTime.Builder()
                                        .setYear(year)
                                        .setMonth(month)
                                        .setDay(dayOfMonth)
                                        .build();
                                arriveTime.setText(DateTime.formatDate(dateTime[0]));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        leaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveTime.setText("");
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateTime[1] = new DateTime.Builder()
                                        .setYear(year)
                                        .setMonth(month)
                                        .setDay(dayOfMonth).build();
                                leaveTime.setText(DateTime.formatDate(dateTime[1]));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sArrTime = arriveTime.getText().toString();
                String sLeaveTime = leaveTime.getText().toString();
                if (sArrTime == null || sArrTime.equals("")) {
                    Toast.makeText(view.getContext(), "必须输入到来的时间！", Toast.LENGTH_SHORT).show();
                } else if (sLeaveTime != null && !sLeaveTime.equals("") && DateTime.judge(dateTime[0], dateTime[1])) {
                    cleanEdit();
                    Toast.makeText(view.getContext(), "离开时间必须比到达时间晚",Toast.LENGTH_SHORT).show();
                } else {
                    String sGoTime = leaveTime.getText().toString();
                    if (sGoTime.equals("")) {
                        sGoTime = "          ";
                    }
                    historyTime.setText(FormatData.formatHistoryShow(sArrTime, sGoTime, name));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_ID, sId);
                    Log.d(TAG, "input id is " + sId);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_TIME, sArrTime);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_TIME_GO, sGoTime);
                    contentValues.put(PlaceContract.PlaceBase.COLUMN_PLACE_NAME, name);

                    Log.d(TAG, "if first is " + ifFirst.toString());
                    view.getContext().getContentResolver().bulkInsert(
                            PlaceContract.PlaceBase.CONTENT_BASE,
                            new ContentValues[]{contentValues});
                }
            }
        });

        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanEdit();
            }
        });

        return view;
    }

    private void cleanEdit(){
        arriveTime.setText("");
        leaveTime.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
    }
}
