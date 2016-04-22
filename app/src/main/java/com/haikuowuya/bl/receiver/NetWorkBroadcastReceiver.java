package com.haikuowuya.bl.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.base.BaseActivity;

public class NetWorkBroadcastReceiver extends BroadcastReceiver
{
    private BaseActivity mActivity;
    public NetWorkBroadcastReceiver(BaseActivity activity)
    {
        super();
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
        {
            ConnectivityManager  mConnectivityManager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo     mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable())
            {
                String name = mNetworkInfo.getTypeName();
                System.out.println("当前网络名称：" + name);
            }
            else
            {
                System.out.println("没有可用网络");
            }
        }

    }

    /***
     * 使用新版本创建并发送通知
     *
     * @param style :创建通知时使用的样式
     */
    public void sendNotification(NotificationCompat.Style style)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mActivity)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("你好")
                .setContentText("消息")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(
                        BitmapFactory.decodeResource(mActivity.getResources(),
                                R.mipmap.ic_launcher));
        if (null != style)
        {
            mBuilder.setStyle(style);
        }
        Notification mNotification = mBuilder.build();
        NotificationManager mNotificationManager = (NotificationManager) mActivity
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify("libo", 1, mNotification);

        // NotificationCompat.BigPictureStyle mPictureStyle = new NotificationCompat.BigPictureStyle()
        // .bigPicture(
        // BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher))
        // .setBigContentTitle("大图片标题").setSummaryText("大图片概述标题");
        // NotificationCompat.BigTextStyle mTextStyle = new NotificationCompat.BigTextStyle()
        // .bigText("大文字").setBigContentTitle("大文字标题").setSummaryText("概述标题");
        // 以前的notification
        // sendNotification("网络状态已经改变");
        // 可以制定样式的notification
        // sendNotification(mPictureStyle);
        // sendNotification(mTextStyle);
    }

    private AnimationSet genOutAnimation()
    {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1.f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private AnimationSet genInAnimation()
    {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1.f, Animation.RELATIVE_TO_PARENT, 0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }
}
