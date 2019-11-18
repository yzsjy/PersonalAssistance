package com.sjy.personalassistance.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.sjy.personalassistance.database.MyDatabaseHelper;
import com.sjy.personalassistance.R;

public class TripActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
        final String stringId = String.valueOf(id);
        String tripTitle = intent.getStringExtra("title");
        String tripLocation = intent.getStringExtra("location");
        String tripstartTime = intent.getStringExtra("starttime");
        String tripendTime = intent.getStringExtra("endtime");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        TextView tripContentTitle = (TextView) findViewById(R.id.i_title);TextView tripContentLocation = (TextView) findViewById(R.id.i_location);
        TextView tripContentstartTime = (TextView) findViewById(R.id.i_starttime);
        TextView tripContentendTime = (TextView) findViewById(R.id.i_endtime);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("行程详情");

        tripContentTitle.setText(tripTitle);
        tripContentLocation.setText(tripLocation);
        tripContentstartTime.setText(tripstartTime);
        tripContentendTime.setText(tripendTime);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.update);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TripActivity.this, UpdateTripActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.delete);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(TripActivity.this);
                dialog.setTitle("提醒");
                dialog.setMessage("确定要删除吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("Trip", "id = ?", new String[] {stringId});
                        Toast.makeText(TripActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(TripActivity.this, MainActivity.class);
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
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
