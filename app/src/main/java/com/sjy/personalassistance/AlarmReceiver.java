package com.sjy.personalassistance;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.sjy.personalassistance.activity.MainActivity;
import com.sjy.personalassistance.service.RunningService;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager manager;
    private static final int NOTIFICATION_ID_1 = 0x00113;
    private String title, location, year, month, day;
    private int starthour, startminute;
    private String content = "提醒的时间到啦，快看看你要做的事！";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        title = intent.getStringExtra("title");
        location = intent.getStringExtra("location");
        starthour = intent.getIntExtra("hour1", 0);
        startminute = intent.getIntExtra("minute1", 0);
        showNormal(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, RunningService.class);
        context.startService(intent);
    }

    private void showNormal(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)
                .setTicker(content)
                .setContentInfo("便签提醒")
                .setContentTitle(title)
                .setContentText("地点：" + location + "    " + starthour + ":" + startminute + "开始")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pi)
                .build();
        manager.notify(NOTIFICATION_ID_1, notification);
    }


}
