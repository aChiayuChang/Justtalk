package com.example.justtalk;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.NotificationChannel;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;

import android.content.BroadcastReceiver;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager m_notificationMgr = null;
    private NotificationCompat.Builder m_notificationCpt = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, alarm_Activity.class);
            context.startService(serviceIntent);
        }
        m_notificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        m_notificationCpt = new NotificationCompat.Builder(context, "channel01");
        Intent call = new Intent(context, chatting_Activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, call, 0);

        m_notificationCpt.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("提醒")
                            .setWhen(System.currentTimeMillis())
                            .setContentText("Let's talk")
                            .setTicker("Come from JustTalk")
                            .setContentIntent(pIntent);
        @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("channel01", "My Notifications", NotificationManager.IMPORTANCE_HIGH);

        m_notificationMgr.createNotificationChannel(notificationChannel);
        m_notificationMgr.notify(1, m_notificationCpt.build());
    }
}
