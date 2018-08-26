package com.milkzs.android.wheretotravel.Log;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.milkzs.android.wheretotravel.R;

/**
 * Created by alan on 2018/8/25.
 */

public class EditCustomDialog extends DialogFragment implements View.OnClickListener {

    private EditCustomDialogClickListener editCustomDialogClickListener;
    private EditText nameEditText;
    private EditText arriveEditText;
    private EditText leaveEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.define_view_edit_log, container);
        }

        ImageView img_but = mView.findViewById(R.id.define_view_log_img);
        nameEditText = mView.findViewById(R.id.define_view_log_name);
        arriveEditText = mView.findViewById(R.id.define_view_log_arrive_time);
        leaveEditText = mView.findViewById(R.id.define_view_log_leave_time);
        Button enterBt = mView.findViewById(R.id.define_view_log_enter_but);
        Button cancelBt = mView.findViewById(R.id.define_view_log_enter_cancel);

        img_but.setOnClickListener(this);
        enterBt.setOnClickListener(this);
        cancelBt.setOnClickListener(this);

        return mView;
    }

    public void setEditCustomDialogClickListener(EditCustomDialogClickListener editCustomDialogClickListener){
        this.editCustomDialogClickListener = editCustomDialogClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.define_view_log_img:{
                editCustomDialogClickListener.clickImg();
            }break;
            case R.id.define_view_log_enter_but:{

                String sName = nameEditText.getText().toString();
                if(sName.equals("")){
                    Toast.makeText(getContext(),"景点名字不可为空！",Toast.LENGTH_SHORT).show();
                    return;
                }

                String sArriveTime = arriveEditText.getText().toString();
                if(sArriveTime.equals("")){
                    Toast.makeText(getContext(),"请填写到达景点的时间！",Toast.LENGTH_SHORT).show();
                    return;
                }

                String sLeaveTime = leaveEditText.getText().toString();
                if(sLeaveTime.equals("")){
                    Toast.makeText(getContext(),"请填写离开景点的时间！",Toast.LENGTH_SHORT).show();
                    return;
                }
                editCustomDialogClickListener.clickButEnter(sName,sArriveTime,sLeaveTime);

            }
            case R.id.define_view_log_enter_cancel:{
                onDestroyView();
            }break;
        }
    }

    public interface EditCustomDialogClickListener {

        void clickImg();

        void clickButEnter(String sName,String sArriveTime,String sLeaveTime);
    }
}
