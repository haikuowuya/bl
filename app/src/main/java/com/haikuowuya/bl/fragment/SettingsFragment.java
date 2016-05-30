package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haikuowuya.bl.PREF;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentSettingsBinding;

/**
 * 说明:
 * 日期:2016/4/26
 * 时间:20:44
 * 创建者：hkwy
 * 修改者：
 **/
public class SettingsFragment extends BaseFragment
{
    public static  final SettingsFragment newInstance()
    {
        SettingsFragment fragment = new SettingsFragment();
        return  fragment;
    }
    private FragmentSettingsBinding mFragmentSettingsBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       mFragmentSettingsBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_settings,container,false);
        return  mFragmentSettingsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initDefaultChecked();
        setListener();
    }

    /***
     * 初始化默认的选择
     */
    private void initDefaultChecked()
    {
        /*默认数据来自WEB */
        if(!mActivity.getSharedPreferences().contains(PREF.PREF_DATA_FROM_WEB))
        {
            mActivity.getSharedPreferences().edit().putBoolean(PREF.PREF_DATA_FROM_WEB, true).commit();
        }
        boolean fromWeb = mActivity.getSharedPreferences().getBoolean(PREF.PREF_DATA_FROM_WEB, false);
        boolean fromApp = mActivity.getSharedPreferences().getBoolean(PREF.PREF_DATA_FROM_APP, false);
        mFragmentSettingsBinding.ctvWeb.setChecked(fromWeb);
        mFragmentSettingsBinding.ctvApp.setChecked(fromApp);
    }
    private void setListener()
    {
        mFragmentSettingsBinding.ctvApp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               onAppClick();
            }
        });
        //replace with lambda
        mFragmentSettingsBinding.ctvApp.setOnClickListener(view ->{onAppClick();});

        mFragmentSettingsBinding.ctvWeb.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onWebClick();
            }
        });

        //replace with lambda
        mFragmentSettingsBinding.ctvWeb.setOnClickListener(view ->{ onWebClick();});
    }

    /***
     * APP item 的点击事件
     */
    private void onAppClick()
    {
        boolean isAppChecked = mFragmentSettingsBinding.ctvApp.isChecked();
        if(!isAppChecked)
        {
            mFragmentSettingsBinding.ctvApp.setChecked(!isAppChecked);
            mActivity.getSharedPreferences().edit().putBoolean(PREF.PREF_DATA_FROM_APP, !isAppChecked).commit();
                /*将另外一个置为与之对立的状态*/
            mActivity.getSharedPreferences().edit().putBoolean(PREF.PREF_DATA_FROM_WEB, isAppChecked).commit();
            mFragmentSettingsBinding.ctvWeb.setChecked(isAppChecked);
        }
    }

    /**
     * WEB item 的点击事件
     */
    private void onWebClick()
    {
        boolean isWebChecked = mFragmentSettingsBinding.ctvWeb.isChecked();
        if(!isWebChecked)
        {
            mFragmentSettingsBinding.ctvWeb.setChecked(!isWebChecked);
            mActivity.getSharedPreferences().edit().putBoolean(PREF.PREF_DATA_FROM_WEB, !isWebChecked).commit();
                /*将另外一个置为与之对立的状态*/
            mActivity.getSharedPreferences().edit().putBoolean(PREF.PREF_DATA_FROM_APP, isWebChecked).commit();
            mFragmentSettingsBinding.ctvApp.setChecked(isWebChecked);
        }
    }
}
