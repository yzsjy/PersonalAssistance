package com.sjy.personalassistance.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import com.sjy.personalassistance.AlarmReceiver;
import com.sjy.personalassistance.database.MyDatabaseHelper;

import java.util.Calendar;

public class RunningService extends Service {
    private MyDatabaseHelper dbHelper;
    private String location, title;
    private int startHour, startMinute, id;
    private AlarmManager am;
    private PendingIntent pi;


    public RunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getAlarmTime();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        String currentMonth = String.valueOf(calendar.get(Calendar.MONTH));
        String currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        dbHelper = new MyDatabaseHelper(this, "PersonalAssistant.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Trip", new String[] { "id", "title", "location", "starthour", "startminute" }, "month = ? and day = ? and iscancel = ?", new String[] {currentMonth, currentDay, "1"}, null, null, null);
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            location = cursor.getString(cursor.getColumnIndex("location"));
            startHour = cursor.getInt(cursor.getColumnIndex("starthour"));
            startMinute = cursor.getInt(cursor.getColumnIndex("startminute"));
            do {
                if (isLarger(startHour, startMinute, cursor.getInt(cursor.getColumnIndex("starthour")), cursor.getInt(cursor.getColumnIndex("startminute"))) == 1) {
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                    title = cursor.getString(cursor.getColumnIndex("title"));
                    location = cursor.getString(cursor.getColumnIndex("location"));
                    startHour = cursor.getInt(cursor.getColumnIndex("starthour"));
                    startMinute = cursor.getInt(cursor.getColumnIndex("startminute"));
                }
            } while (cursor.moveToNext());
        }else {
            startHour = 0;
            startMinute = 0;
        }

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, startHour);
        c1.set(Calendar.MINUTE, startMinute);
        long triggerAtMillis= c1.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put("iscancel", 0);
        db.update("Trip", values, "id = ?", new String[] {String.valueOf(id)});
        cursor.close();
        Intent startNotification = new Intent(this, AlarmReceiver.class);
        startNotification.putExtra("title", title);
        startNotification.putExtra("location", location);
        startNotification.putExtra("hour1", startHour);
        startNotification.putExtra("minute1", startMinute);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        pi = PendingIntent.getBroadcast(this, 0, startNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        if(startHour !=0 && startMinute !=0){
            am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi);
        } else {
            stopService(new Intent(this, RunningService.class));
        }
    }

    public int isLarger(int hour1, int minute1, int hour2, int minute2){
        if (hour1 < hour2){
            return 0;
        } else if (hour1 == hour2){
            if (minute1 < minute2){
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}