package com.example.calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Your Time is up!!!!!", Toast.LENGTH_LONG).show();

        Intent mIntent = new Intent(context,MainActivity.class);
        context.startActivity(mIntent);
    }
}
