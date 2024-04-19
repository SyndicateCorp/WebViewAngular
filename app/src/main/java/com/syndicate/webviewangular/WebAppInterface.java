package com.syndicate.webviewangular;

import static android.content.Context.CAMERA_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class WebAppInterface {
    private Activity _activity;
    private Context _context;
    
    public WebAppInterface(Context context, Activity activity){
        _context = context;
        _activity = activity;
    }
    
    @JavascriptInterface
    public void showNotification(String title, String message) {
        NotificationChannel channel = new NotificationChannel("twChannel", "tw", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context.getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channel.getId());

        NotificationManager manager = (NotificationManager) _context.getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        manager.notify(1, builder.build());
    }

    @JavascriptInterface
    public void showCall(){
        Intent callIntent =new Intent(Intent.ACTION_DIAL);
        _context.startActivity(callIntent);
    }

    @JavascriptInterface
    public void showWhatsApp(){
        try {
            String url = "https://api.whatsapp.com/send?phone="+"089677448448";
            Intent waIntent = new Intent(Intent.ACTION_VIEW);
            waIntent.setData(Uri.parse(url));
            _context.startActivity(waIntent);
        } catch (Exception e){
            Toast.makeText(_context, "Whatsapp not installed!", Toast.LENGTH_LONG).show();
        }
    }

    @JavascriptInterface
    public void showCamera(){
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        _context.startActivity(camIntent);
    }

}
