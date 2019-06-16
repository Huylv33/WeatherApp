
package com.project.mobile.weatherapp.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.project.mobile.weatherapp.utils.Constants;

public class Noti extends BroadcastReceiver {
    public  Context context;
    private static final int TIME_VIBRATE = 1000;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        int index = intent.getIntExtra(Constants.KEY_TYPE, 0);
        Log.i("ddm ", index + "");
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";

    }
}
