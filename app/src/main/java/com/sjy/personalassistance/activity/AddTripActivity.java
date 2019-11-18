package com.sjy.personalassistance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;

import com.sjy.personalassistance.R;

public class AddTripActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener{

    private CalendarView calendarView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(this);

        fab = findViewById(R.id.fab);

    }

    @Override
    public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddTripActivity.this, SetTripActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("dayOfMonth", dayOfMonth);

                startActivity(intent);

            }
        });

    }
}
