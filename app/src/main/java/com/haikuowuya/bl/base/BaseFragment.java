package com.haikuowuya.bl.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 说明:
 * 日期:2016/4/14
 * 时间:20:16
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseFragment extends Fragment
{
    protected  BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }
}
