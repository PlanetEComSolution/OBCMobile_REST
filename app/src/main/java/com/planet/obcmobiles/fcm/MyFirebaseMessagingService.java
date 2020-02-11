package com.planet.obcmobiles.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Utility;


import java.util.Map;



public class MyFirebaseMessagingService extends FirebaseMessagingService {
   

    @Override
    public void onMessageReceived(RemoteMessage message) {
  

        if (message.getData().size() > 0) {
            Log.e("data_payload==>", "" + message.getData());
            Map<String, String> data = message.getData();
           // String ShortDesc = data.get("ShortDesc");
           // String IncidentNo = data.get("IncidentNo");
            //String url = data.get("url");
           // String Description = data.get("Description");
          //  String text = data.get("text");
            //String click_action = data.get("click_action");

           // startNotification("Oriental Saathi",Description,click_action);

        }else {

            String titile = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            //  sendMyNotification(titile, body);
            startNotification(titile,body,"");
        }


    }



    public  void startNotification(String title,String message,String click_action) {
        // TODO Auto-generated method stub
        NotificationCompat.Builder notification;
        PendingIntent pIntent;
        NotificationManager manager;
        // Intent resultIntent;

        TaskStackBuilder stackBuilder;
        Intent intent=null;

        intent = new Intent(click_action);
        //intent.putExtra("fileLink",url);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notification_mp3);

       /* AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            final String channelId = "OBC_Saathi_Channel_ID_1111";
            final String channelName = "my_channel_citizen_charter";
            final NotificationChannel defaultChannel =
                    new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            defaultChannel.setDescription(title);
            defaultChannel.enableLights(true);
            //  defaultChannel.enableVibration(true);
          //  defaultChannel.setSound(soundUri, attributes); //

            manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(defaultChannel);
            }

            stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntent(intent);
            pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification2 = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.logo)
                    .setChannelId(channelId)
                    .setContentIntent(pIntent)
                    .setOngoing(false)
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .build();


            manager.notify(Utility.random_number_notificationId(), notification2);

        } else {
            //Creating Notification Builder
            notification = new NotificationCompat.Builder(this);
            //Title for Notification
            notification.setContentTitle(title);
            //Message in the Notification
            notification.setContentText(message);
            //Alert shown when Notification is received
            notification.setTicker(message);
            //Icon to be set on Notification
            notification  .setSmallIcon(R.mipmap.logo);
            notification   .setSound(soundUri);
            /*nks*/
      /*  notification.setCategory(NotificationCompat.CATEGORY_SERVICE);
        notification.setPriority(NotificationCompat.PRIORITY_MIN);*/
            notification.setOngoing(false);
            notification.setAutoCancel(true);
            /*nks*/
            //Creating new Stack Builder
            stackBuilder = TaskStackBuilder.create(this);
            /*  stackBuilder.addParentStack(Result.class);*/
            //Intent which is opened when notification is clicked
            stackBuilder.addNextIntent(intent);
            pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pIntent);
            manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            manager.notify(Utility.random_number_notificationId(), notification.build());

        }


    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);

       // Shared_Preference.setFCM_TOKEN(this,s);
      //  String empId = Shared_Preference.getLOGIN_USER_ID(this);
       // UpdateFCMToken(s);

    }



}
