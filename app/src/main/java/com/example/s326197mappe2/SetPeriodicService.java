package com.example.s326197mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class SetPeriodicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        java.util.Calendar cal = Calendar.getInstance();

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean runService = defaultSharedPreferences.getBoolean("NotificationService", true);
        if(!runService) return super.onStartCommand(intent, flags, startId);

        String timeOfDay = defaultSharedPreferences.getString("timeOfDay", "15:13");

        String[] parts = timeOfDay.split(":", 2);
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        Intent i = new Intent(this, NotificationService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
        return super.onStartCommand(intent, flags, startId);
    }
}