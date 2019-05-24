package com.example.justtalk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;

import java.util.Calendar;

public class alarm_Activity extends AppCompatActivity {
    public static final String BROADCAST_ACTION ="action.for.receiver";
    private static final String TAG = "alarm_Activicty";
    private AlarmManager alarmManager;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到闹钟管理器
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if(setting.getBoolean("notify",false)) {
            setAlarm();
        } else {
            closeAlarm();
        }

        return_to_setting();
    }
    //开启周期闹钟
    public void setAlarm() {
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        //获得当前系统的小时跟分
        Calendar calendar = Calendar.getInstance();
        int hour = setting.getInt("hour", calendar.get(Calendar.HOUR_OF_DAY));//得到小时
        final int minute = setting.getInt("minute", calendar.get(Calendar.MINUTE));//得到分钟

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Intent intent=new Intent();
        intent.setClass(alarm_Activity.this, AlarmReceiver.class);
        pi = PendingIntent.getBroadcast(alarm_Activity.this,0x102, intent,0);
        //时间一到
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

        return_to_setting();
    }

    //关闭周期闹钟
    public void closeAlarm() {
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        alarmManager.cancel(pi);//取消周期的闹钟

        return_to_setting();
    }

    public void return_to_setting () {

        Intent intent = new Intent();
        intent.setClass(alarm_Activity.this, setting_Activity.class);
        startActivity(intent);
        alarm_Activity.this.finish();
    }
}
