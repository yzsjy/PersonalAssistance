package com.sjy.personalassistance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class TimePickerDialog {

    private Context mContext;
    private AlertDialog.Builder mAlertDialog;
    private int mHour, mMinute;
    private TimePickerDialogInterface timePickerDialogInterface;
    private TimePicker mTimePicker;
    private int mYear, mDay, mMonth;

    public TimePickerDialog(Context context){
        super();
        mContext = context;
        timePickerDialogInterface = (TimePickerDialogInterface) context;
    }

    private View initTimePicker(){
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dateandtimepicker_layout, null);
        mTimePicker = (TimePicker) inflate.findViewById(R.id.timepicker1);
        mTimePicker.setIs24HourView(true);
        resizePicker(mTimePicker);
        return inflate;
    }

    private void initDialog(View view, final View view1){
        mAlertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getTimePickerValue();
                timePickerDialogInterface.positiveListener(view1);
            }
        });

        mAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timePickerDialogInterface.negativeListener();
                dialog.dismiss();
            }
        });

        mAlertDialog.setView(view);
    }

    public void showTimePickerDialog(View view1){
        View view = initTimePicker();
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view, view1);
        mAlertDialog.show();
    }

    private void resizeNumberPicker(NumberPicker np){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        np.setLayoutParams(params);
    }

    private void resizePicker(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np : npList){
            resizeNumberPicker(np);
        }
    }

    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for (int i=0; i<viewGroup.getChildCount(); i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker) child);
                }else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }


    public int getMinute() {
        return mMinute;
    }

    public int getHour() {
        return mHour;
    }



    private void getTimePickerValue(){
        mHour = mTimePicker.getCurrentHour();
        mMinute = mTimePicker.getCurrentMinute();
    }


    public interface TimePickerDialogInterface{
        public void positiveListener(View view);
        public void negativeListener();
    }
}
