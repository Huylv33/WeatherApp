
package com.project.mobile.weatherapp.Broadcast;

        import android.app.Activity;
        import android.app.Notification;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.provider.Settings;
        import android.support.v4.app.NotificationCompat;
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
//        int importance = NotificationManager.IMPORTANCE_LOW;
//        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
//        notificationManager.createNotificationChannel(notificationChannel);
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        int requestID = (int) System.currentTimeMillis();
//        PendingIntent contentIntent = PendingIntent
//                .getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(context.getString(R.string.app_name))
//                        .setContentText("index = " + index)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setDefaults(Notification.DEFAULT_SOUND)
//                        .setAutoCancel(true)
//                        .setPriority(6)
//                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
//                                TIME_VIBRATE})
//                        .setContentIntent(contentIntent)
//                        .setChannelId(channelId);
//
//        notificationManager.notify(index, builder.build());
    }
}
