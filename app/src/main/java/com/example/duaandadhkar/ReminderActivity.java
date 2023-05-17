package com.example.duaandadhkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    private MaterialTimePicker timePicker;
    private Calendar calendar;//used to set the alarm
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        createNotificationChannel();

    }

    private void createNotificationChannel(){
        CharSequence name = "adhkar";
        String description = "Channel for alarm manager";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("adhkar",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    public void onSelectTime(View v){
        Button button = (Button) v;
        showTimePicker();
    }

    public void onSetReminder(View v){
        Button button = (Button) v;
        setReminder();
    }

    public void onCancelReminder(View v){
        Button button = (Button) v;
        cancelReminder();
    }

    private void cancelReminder() {
        Intent intent = new Intent(this, ReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent,0);
        if(alarmManager==null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setReminder() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0, intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Reminder set successfully", Toast.LENGTH_SHORT).show();

    }

    private void showTimePicker() {
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setMinute(0)
                .setTitleText("Set Reminder Time")
                .build();
        timePicker.show(getSupportFragmentManager(),"adhkar");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });

    }
}