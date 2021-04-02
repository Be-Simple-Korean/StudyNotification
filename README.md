# StudyNotification
## 사용순서
* 1. 알림 채널 만들기 = createNotificationChannel() 
* 2. 알림을 발생시킬 코드 부분에 알림에 관한 코드 넣기
```
NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), 채널 아이디값)
.set~
```
* 3. 알림 발생 코드
```
 NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
 notificationManager.notify(알림 id값(정수형), builder.build());
```
## 특정 메소드
* .setAutoCancel(Boolean) =  클릭 시 알림 삭제
* .setOnlyAlerOnce(Boolean) = 알림 삭제 못하게 하기
* .setOnlyAlerOnce(Boolean) = 알림 삭제 못하게 하기
* .setPriority(NotificationCompat.PRIORITY_DEFAULT) = 알림 우선순위
* .addAction(아이콘리소스,텍스트값,PendingIntent)
* .setStyle() = 확장형 스타일 등으로 사용 가능

## 커스텀 알림
* RemoteViews 사용

```RemoteViews expandView = new RemoteViews(getPackageName(),커스텀 레이아웃 리소스);```
* builder.setCustomContentView(expandView).setStyle(new NotificationCompat.DecoratedCustomViewStyle()) 추가

