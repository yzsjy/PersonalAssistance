package com.sjy.personalassistance.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sjy.personalassistance.R;
import com.sjy.personalassistance.adapter.TripAdapter;
import com.sjy.personalassistance.database.MyDatabaseHelper;
import com.sjy.personalassistance.database.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TripHistoryActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    private Trip[] trips = new Trip[50];
    private List<Trip> tripList = new ArrayList<>();
    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        dbHelper.getWritableDatabase();

        initTrip();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        if(!(tripList.isEmpty())){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TripAdapter(tripList);
            recyclerView.setAdapter(adapter);
        }

    }

    private void initTrip(){
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Trip", new String[] { "id", "title", "location", "month", "day", "starthour", "startminute", "endhour", "endminute" }, null,null, null, null, null);
        int i = 0;
        int length = 0;
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                int month = Integer.parseInt(cursor.getString(cursor.getColumnIndex("month")));
                int day = Integer.parseInt(cursor.getString(cursor.getColumnIndex("day")));
                int starthour = cursor.getInt(cursor.getColumnIndex("starthour"));
                int startminute = cursor.getInt(cursor.getColumnIndex("startminute"));
                int endhour = cursor.getInt(cursor.getColumnIndex("endhour"));
                int endminute = cursor.getInt(cursor.getColumnIndex("endminute"));
                if (!(month >= currentMonth && day >=currentDay)) {
                    trips[i] = new Trip(id, "标题    " + title, "地点    " + location, "开始时间  " + month + "月" + day + "  " + starthour + ":" + startminute, "结束时间  " + month + "月" + day + "  " + endhour + ":" + endminute);
                    length++;
                    i++;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        tripList.clear();
        for(int j =0; j < length; j++){
            tripList.add(trips[j]);
        }
    }

}
