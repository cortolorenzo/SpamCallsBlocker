package com.example.spamcallsblocker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class DialerService extends Service {
    private Timer timer;
    private Handler handler;

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";

    public DialerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    CallReceiver myReceiver;


    private Notification createNotification() {
        // Create a notification channel for Android 8.0 (API level 26) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ForegroundServiceChannel",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle("My Foreground Service")
                .setContentText("Service is running...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(createPendingIntent(this))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Return the notification
        return builder.build();
    }
    public static PendingIntent createPendingIntent(Context context) {
        // Replace MainActivity with the desired activity you want to open
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);



        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){


        PrefsManager prefs = new PrefsManager(this);
        handler = new Handler();
        //startTimer();
       // String myNumber = intent.getStringExtra("number");

        showToast("started");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE"); // Replace with the action you're listening for
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        if (myReceiver == null)
        {
            showToast("Receiver started");
            myReceiver = new CallReceiver(this, prefs.getString("number", ""));
            registerReceiver(myReceiver, intentFilter);

        }

        // Create a notification for the foreground service
        Notification notification = createNotification();

        // Start the service as a foreground service with the specified notification
        startForeground(NOTIF_ID, notification);

        return super.onStartCommand(intent, flags, startId);
        //return START_STICKY;

    }


    public void onDestroy(){

        unregisterReceiver(myReceiver);
        myReceiver = null;
        stopSelf();
        showToast("destroyed");
        super.onDestroy();
    }


    private void startTimer() {
        // Initialize the Timer
        timer = new Timer();

        // Schedule the TimerTask to run every 15 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Execute code on the main thread using the Handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //showToast("Hello, this is a toast message!");
                    }
                });
            }
        }, 0, 5000); // 15 seconds interval in milliseconds (1000 ms = 1 second)
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}