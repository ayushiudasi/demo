package com.example.calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    final static int req1=1;
    public String a = "0"; // initialize this globally at the top of your class.
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private static final String TAG =MainActivity.class.getSimpleName();
    Button dateButton,timeButton,btnSetAlarm, btnCancelAlarm;
    TextView dateTextView,timeTextView;
    String timeString ,datestring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSetAlarm = (Button)findViewById(R.id.button3);
        btnCancelAlarm = (Button)findViewById(R.id.button4);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                String[] dateList = datestring.split(":");
                String[] timeList = timeString.split(":");

                String year = dateList[0];
                String month = dateList[1];
                String date = dateList[2];
                String hour = timeList[0];
                String min = timeList[1];

                cal.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), Integer.parseInt(hour), Integer.parseInt(min), 0);
                setAlarm(cal);
                if(a.equals("0"))
                {
                    // do whatever you want to do.
                }
            }
        });
        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        dateButton =  findViewById(R.id.button);
                timeButton = findViewById(R.id.button2);
        dateTextView = findViewById(R.id.textView);
                timeTextView = findViewById(R.id.textView3);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });
    }
    private void handleTimeButton() {
        Toast.makeText(this,"Handle Time Button",Toast.LENGTH_SHORT);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int HOUR, int MINUTE) {
                timeString = HOUR+":"+MINUTE;
                timeTextView.setText(timeString);
            }
        } , hour, minute,is24HourFormat);

        timePickerDialog.show();

    }
    private void handleDateButton() {
        Toast.makeText(this,"Handle Date Button",Toast.LENGTH_SHORT);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int YEAR, int MONTH, int DATE) {
               datestring = DATE+"/"+MONTH+"/"+YEAR;
               dateTextView.setText(datestring);
            }
        },year,month,date);

        datePickerDialog.show();

    }
    /*private void setAlarm(){
        Log.d(TAG,"AlarmReciever set");
        Intent intent = new Intent(MainActivity.this,AlarmReciever.class);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String[] dateList = datestring.split(":");

        String year = dateList[0];
        String month = dateList[1];
        String date = dateList[2];



        calendar.set(Calendar.YEAR,Integer.parseInt(year));
        calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
        calendar.set(Calendar.DATE,Integer.parseInt(date));

        String[] timeList = timeString.split(":");

        String hour = timeList[0];
        String min = timeList[1];

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, pendingIntent);

        setBootReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);

    }*/


    private void setAlarm(Calendar target){
        Intent intent = new Intent(getBaseContext(), AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), req1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
        a ="1";
    }

    private void cancelAlarm(){
        Log.d(TAG,"Alarm Cancelled");
        alarmManager.cancel(pendingIntent);
    }

    private void setBootReceiverEnabled(int componentEnabledState) {
        ComponentName componentName = new ComponentName(this, AlarmReciever.class);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,componentEnabledState,PackageManager.DONT_KILL_APP);
    }
}
