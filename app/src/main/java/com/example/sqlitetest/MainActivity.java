package com.example.sqlitetest;



import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;



public class MainActivity extends AppCompatActivity {

    Button btnNotify;



    private Handler handler = new Handler();

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent()) ;
        System.out.println("naan change maadde");
        int naanu=420;
        btnNotify=(Button) findViewById(R.id.btnNotify);

    }


    @Override
    protected void onNewIntent (Intent intent) {
        super .onNewIntent(intent) ;
        Bundle extras = intent.getExtras() ;
        if (extras != null ) {
            if (extras.containsKey( "NotificationMessage" )) {
                String msg = extras.getString( "NotificationMessage" ) ;
                TextView tvNotify = findViewById(R.id. tvNotify ) ;
                tvNotify.setText(msg) ;
            }
        }
    }
    public void createNotification (View view) {
        Intent notificationIntent = new Intent(MainActivity. this, MainActivity. class ) ;
        notificationIntent.putExtra( "NotificationMessage" , "I am from Notification" ) ;
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent resultIntent = PendingIntent. getActivity (MainActivity. this, 0 , notificationIntent , 0 ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder
                (MainActivity. this, default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Test" )
                .setContentText( "Hello! This is my first push notification" )
                .setContentIntent(resultIntent) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

}
