package utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.dsbridage_learning.R;

import wendu.dsbridge.CompletionHandler;

public class JsInterfaceUtil {
    private Context mContext;
    public JsInterfaceUtil(Context context) {
        mContext = context;
    }
    /***************** for MainActivity*******************************************************/
    // js 调用java
    @JavascriptInterface
    public void synchronizedCall(Object msg) {
        Toast.makeText(mContext, msg.toString(), Toast.LENGTH_SHORT).show();
//        return "syn all success:" + msg;
    }

    @JavascriptInterface
    public void asynchronizeCall(Object msg, CompletionHandler<String> handler) {
        handler.complete("async all success:" + msg);
    }
    /***************** for MainActivity*******************************************************/

    @JavascriptInterface
    public void nativeNotifyCall(Object msg) {
        Activity activity = (Activity) mContext;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                //Android O （8.0）以上版本需要渠道
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //最后一个参数是通知重要度，DEFAULT及以上，通知时手机默认会有振动
                    NotificationChannel notificationChannel = new NotificationChannel("channelid1", "channelname", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(notificationChannel);
                }
                //创建Notification对象
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channelid1");
                builder.setContentTitle("通知");
                builder.setContentText("你好");
                //设置通知被创建的时间
                builder.setWhen(System.currentTimeMillis());
                //设置出现在状态栏的小图标
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                //设置通知的大图标
//                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.face));
                //让通知显示出来 第一个参数时id，要保证每个通知所指定的id各不相同 第二个参数是Notification对象
                manager.notify(1, builder.build());
            }
        });
    }

    @JavascriptInterface
    public void nativeServiceCall(Object msg) {
        Intent startIntent = new Intent(mContext, MyService.class);
        mContext.startService(startIntent);
    }

    @JavascriptInterface
    public void destroyNativeService(Object msg) {
        Intent stopIntent = new Intent(mContext, MyService.class);
        mContext.stopService(stopIntent);
    }

}
