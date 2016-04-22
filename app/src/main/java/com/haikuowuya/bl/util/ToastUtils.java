package com.haikuowuya.bl.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.google.repacked.antlr.v4.codegen.model.Loop;
import com.haikuowuya.bl.base.BaseActivity;

public class ToastUtils
{
    private static Toast sCurrentToast;

    /****
     * 显示一个短的{@linkplain Toast},会取消之前的toast
     * @param context
     * @param toastMsg
     */
    public static void showShortToast(Context context, String toastMsg)
    {
        if (null != sCurrentToast)
        {
            sCurrentToast.cancel();
        }
        sCurrentToast = Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT);
        sCurrentToast.show();
    }

}
