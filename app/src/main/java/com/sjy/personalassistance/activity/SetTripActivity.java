package com.sjy.personalassistance.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sjy.personalassistance.R;
import com.sjy.personalassistance.TimePickerDialog;
import com.sjy.personalassistance.database.MyDatabaseHelper;

public class SetTripActivity extends AppCompatActivity implements TimePickerDialog.TimePickerDialogInterface {

    private MyDatabaseHelper dbHelper;
    private TimePickerDialog mTimePickerDialog;
    private EditText title, location, pretime;
    private TextView startTime, endTime, date;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private int hour1, minute1;
    private int hour2, minute2;
    private String year, month, day;
    private String tripTitle, tripLocation;
    private int remind, isremind;
    private int[] Month = new int[25];
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);

        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        year = String.valueOf(intent.getIntExtra("year", 2019));
        month = String.valueOf(intent.getIntExtra("month", 1) + 1);
        day = String.valueOf(intent.getIntExtra("dayOfMonth", 1));
        date = (TextView) findViewById(R.id.date);
        date.setText(year + "年" + month + "月" + day + "日");

        title = (EditText) findViewById(R.id.trip_title);
        location = (EditText) findViewById(R.id.location);
        pretime = (EditText) findViewById(R.id.remind);
        radioGroup = (RadioGroup) findViewById(R.id.is_cancel);

        mTimePickerDialog = new TimePickerDialog(SetTripActivity.this);
        startTime = (TextView) findViewById(R.id.selected_date1);
        endTime = (TextView) findViewById(R.id.selected_date2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.showTimePickerDialog(startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.showTimePickerDialog(endTime);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if(rb.getText() == "是"){
                    isremind = 1;
                }else if (rb.getText() == "否"){
                    isremind = 0;
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    tripTitle = title.getText().toString();
                    tripLocation = location.getText().toString();
                    remind = Integer.valueOf(pretime.getText().toString());
                    int state = isClash(Month, hour1, hour2);

                    if(state == 1) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("title", tripTitle);
                        values.put("location", tripLocation);
                        values.put("year", year);
                        values.put("month", month);
                        values.put("day", day);
                        values.put("starthour", hour1);
                        values.put("startminute", minute1);
                        values.put("endhour", hour2);
                        values.put("endminute", minute2);
                        values.put("iscancel", isremind);
                        values.put("preminute", remind);
                        db.insert("Trip", null, values);
                        Toast.makeText(SetTripActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent();
                        success.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        success.setClass(SetTripActivity.this, MainActivity.class);
                        startActivity(success);

                    } else if(state == 0){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(SetTripActivity.this);
                        dialog.setTitle("注意");
                        dialog.setMessage("该行程与已添加行程发生冲突");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("查看行程", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setClass(SetTripActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                    }

            }
        });
    }

    @Override
    public void positiveListener(View view) {

        switch (view.getId()){
            case R.id.selected_date1:
                hour1=mTimePickerDialog.getHour();
                minute1=mTimePickerDialog.getMinute();
                startTime.setText(hour1 + ":" + minute1);
                break;
            case R.id.selected_date2:
                hour2=mTimePickerDialog.getHour();
                minute2=mTimePickerDialog.getMinute();
                endTime.setText(hour2 + ":" + minute2);
                break;
        }
    }

    @Override
    public void negativeListener() {

    }

    public int isClash(int Month[], int starthour, int endhour){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Trip", new String[] {"starthour", "endhour" }, "month=? and day=?", new String[] {month, day}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                int startHour = cursor.getInt(cursor.getColumnIndex("starthour"));
                int endHour = cursor.getInt(cursor.getColumnIndex("endhour"));
                for (int i = startHour; i <= endHour; i++){
                    Month[i]=1;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        if(Month[starthour] ==0 && Month[endhour] ==0){
            return 1;
        } else {
            return 0;
        }
    }
}
