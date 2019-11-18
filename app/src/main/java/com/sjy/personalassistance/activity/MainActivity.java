package com.sjy.personalassistance.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sjy.personalassistance.R;
import com.sjy.personalassistance.adapter.TripAdapter;
import com.sjy.personalassistance.database.MyDatabaseHelper;
import com.sjy.personalassistance.database.Trip;
import com.sjy.personalassistance.iflyAIUI.ChatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private MyDatabaseHelper dbHelper;

    private Trip[] trips = new Trip[50];
    private List<Trip> tripList = new ArrayList<>();
    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        dbHelper.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addTrip = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(addTrip);
            }
        });

        initTrip();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        if(!(tripList.isEmpty())){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TripAdapter(tripList);
            recyclerView.setAdapter(adapter);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.message:
                Intent mMessage = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(mMessage);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.trip) {
            Intent trip = new Intent(MainActivity.this, TripHistoryActivity.class);
            startActivity(trip);
            // Handle the camera action
        } else if (id == R.id.weather) {
            Intent weather = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(weather);
        } else if (id == R.id.reservation) {
            Intent reservation = new Intent(MainActivity.this, ReservationActivity.class);
            startActivity(reservation);
        } else if (id == R.id.voice) {
            Intent voice = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(voice);

        }
        return true;
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
                if (month >=currentMonth && day >=currentDay) {
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
