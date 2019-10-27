package com.example.s326197mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "I BroadcastReceiver", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, SetPeriodicService.class);
        context.startService(i);
    }
}