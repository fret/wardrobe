package com.example.dopamine.wardrobe.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.example.dopamine.wardrobe.Services.MyAlarmService;

/**
 * Created by Dopamine on 12/31/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent service = new Intent(context, MyAlarmService.class);
        service.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        context.startService(service);
    }
}