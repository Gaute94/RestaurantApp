package com.example.s326197mappe2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {


    private String message = "Hei der";
    private String phoneNo = "9131611";
    private DBHandler dbHandler = new DBHandler(this);
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "i notificationService");
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Result.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        message = sharedPreferences.getString("sms_text", "Hei, du har en eller flere restaurantbestillinger i dag.");
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Restaurantbestillinger")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pIntent).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        Log.d("NotificationService", "Notification: " + notification);

        Calendar c = Calendar.getInstance();

// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date today = c.getTime();

        SmsManager smsMan = SmsManager.getDefault();
        for(Booking booking: dbHandler.findAllBookings()){
            if(booking.getDate().equals(today)){
                Log.d("NotificationService", "Booking today at restaurant: " + booking.getRestaurant().getName() + " at " + booking.getDate());
                for(Friend friend : booking.getFriendList()){
                    phoneNo = friend.getTelephone();
                    smsMan.sendTextMessage(phoneNo, null, message, null, null);
                    Log.d("NotificationService", "Sent SMS to " + friend.getName());
                }
            }
        }



        Toast.makeText(this, "Har sendt sms", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

}
