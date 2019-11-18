package com.sjy.personalassistance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TRIP = "create table Trip ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "location text, "
            + "year text, "
            + "month text, "
            + "day text, "
            + "starthour integer, "
            + "startminute integer, "
            + "endhour integer, "
            + "endminute integer, "
            + "iscancel integer, "
            + "preminute integer)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRIP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Trip");
        onCreate(db);
    }
}
