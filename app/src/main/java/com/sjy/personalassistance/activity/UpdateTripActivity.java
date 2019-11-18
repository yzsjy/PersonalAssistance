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

import com.sjy.personalassistance.database.MyDatabaseHelper;
import com.sjy.personalassistance.R;
import com.sjy.personalassistance.TimePickerDialog;

public class UpdateTripActivity extends AppCompatActivity implements TimePickerDialog.TimePickerDialogInterface {

    private MyDatabaseHelper dbHelper;
    private TimePickerDialog mTimePickerDialog;
    private int hour1, hour2, minute1, minute2;
    private int id, starthour, endhour, isremind;
    private String stringId, year, month, day;
    private TextView startTime, endTime, tripDate;
    private EditText tripTitle, tripLocation;
    private String Title, Location;
    private int[] Month = new int[25];
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        dbHelper.getWritableDatabase();
        final Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        stringId = String.valueOf(id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tripTitle = (EditText) findViewById(R.id.ut_title);
        tripLocation = (EditText) findViewById(R.id.ut_location);
        tripDate = (TextView) findViewById(R.id.ut_date);
        startTime = (TextView) findViewById(R.id.ut_selected_date1);
        endTime = (TextView) findViewById(R.id.ut_selected_date2);
        radioGroup = (RadioGroup) findViewById(R.id.ut_cancel);
        mTimePickerDialog = new TimePickerDialog(UpdateTripActivity.this);

        initView();

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Title = tripTitle.getText().toString();
                Location = tripLocation.getText().toString();
                int state = isClash(Month, hour1, hour2);
                if (state == 1){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateTripActivity.this);
                    dialog.setTitle("注意");
                    dialog.setMessage("确定要更新行程吗？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("title", Title);
                            values.put("location", Location);
                            values.put("starthour", hour1);
                            values.put("startminute", minute1);
                            values.put("endhour", hour2);
                            values.put("endminute", minute2);
                            values.put("iscancel", isremind);
                            db1.update("Trip", values, "id = ?", new String[] { stringId });
                            Toast.makeText(UpdateTripActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent();
                            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.setClass(UpdateTripActivity.this, MainActivity.class);
                            startActivity(intent2);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                } else if (state == 0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateTripActivity.this);
                    dialog.setTitle("注意");
                    dialog.setMessage("该行程与已添加行程发生冲突");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("查看行程", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setClass(UpdateTripActivity.this, MainActivity.class);
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
            case R.id.ut_selected_date1:
                hour1=mTimePickerDialog.getHour();
                minute1=mTimePickerDialog.getMinute();
                startTime.setText(hour1 + ":" + minute1);
                break;
            case R.id.ut_selected_date2:
                hour2=mTimePickerDialog.getHour();
                minute2=mTimePickerDialog.getMinute();
                endTime.setText(hour2 + ":" + minute2);
                break;
        }
    }

    @Override
    public void negativeListener() {

    }

    public void initView() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor1 = db.query("Trip", new String[] { "title", "location", "year", "day", "month", "starthour", "startminute", "endhour", "endminute"}, "id = ?", new String[] { stringId}, null, null, null);
        if (cursor1.moveToFirst()) {
            String title = cursor1.getString(cursor1.getColumnIndex("title"));
            String location = cursor1.getString(cursor1.getColumnIndex("location"));
            year = cursor1.getString(cursor1.getColumnIndex("year"));
            month = cursor1.getString(cursor1.getColumnIndex("month"));
            day = cursor1.getString(cursor1.getColumnIndex("day"));
            starthour = cursor1.getInt(cursor1.getColumnIndex("starthour"));
            int startminute = cursor1.getInt(cursor1.getColumnIndex("startminute"));
            endhour = cursor1.getInt(cursor1.getColumnIndex("endhour"));
            int endminute = cursor1.getInt(cursor1.getColumnIndex("endminute"));
            tripTitle.setText(title);
            tripLocation.setText(location);
            tripDate.setText(year + "年" + month + "月" + day);
            startTime.setText(starthour + ":" + startminute);
            endTime.setText(endhour + ":" + endminute);
        }
        cursor1.close();
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