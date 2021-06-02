package com.example.studynotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final String cid = "cid";
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new NotificationCompat.Builder(getApplicationContext(), cid)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("타이틀")
                        .setContentText("내용")
                        .setAutoCancel(true) //클릭시 알림 삭제
                        .setOnlyAlertOnce(true) //알림 못끄게 하기
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
                notificationManager.notify(0, builder.build());
            }
        }); //기본 알림
        findViewById(R.id.abtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snoozeIntent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, snoozeIntent, 0);
                builder = new NotificationCompat.Builder(getApplicationContext(), cid)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("액션알림 제목")
                        .setContentText("액션알림 내용")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .addAction(R.mipmap.ic_launcher,"출력",pendingIntent);
                notificationManager.notify(1, builder.build());
            }
        }); // 액션이 추가된 알림- 브로드캐스트리시버 이용
        findViewById(R.id.bbtn).setOnClickListener(view -> {
            Bitmap icon = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.one);
            builder = new NotificationCompat.Builder(getApplicationContext(), cid)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("확장형 제목")
                    .setContentText("확장형 내용")
                    .setLargeIcon(icon)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText("asdasdasdasdasdaaaaasasadsdsdsdsdsdsdsdsdsdsdsdsdsdsd")); //확장시 텍스트 표시
                    .setStyle(new NotificationCompat.BigPictureStyle() //확장시 큰 이미지 표시
                            .bigPicture(icon)
                            .bigLargeIcon(null)
                    );

            notificationManager.notify(2, builder.build());
        }); //확장형 알림
        findViewById(R.id.cbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoteViews expandView = new RemoteViews(getPackageName(),R.layout.notification_expand);
                Intent i=new Intent(MainActivity.this, ReminderBroadcastReiceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,1,i,0);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(),cid)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(expandView)
                        .setContentIntent(pendingIntent)
                        .build();
                notificationManager.notify(1,notification);
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "채널이름";
            String description = "채널자막?";
            int importance = NotificationManager.IMPORTANCE_DEFAULT; //HIGH= HEADUP
            NotificationChannel channel = new NotificationChannel("cid", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}