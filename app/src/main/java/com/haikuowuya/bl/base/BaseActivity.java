package com.haikuowuya.bl.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.haikuowuya.bl.MainActivity;
import com.haikuowuya.bl.util.ToastUtils;

/**
 * 说明:
 * 日期:2016/4/12
 * 时间:20:33
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseActivity extends AppCompatActivity
{
    protected BaseActivity mActivity;
    protected SharedPreferences mSharedPreferences;
    private  ActionBar mActionBar;
    public static  final int REQUEST_PERMISSION_LOCATION = 453;

    @Override
    protected void onCreate(Bundle savedInstanceState )
    {
        mActivity = this;
        mSharedPreferences = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
        mActionBar = getSupportActionBar();
        configActionBar();
        super.onCreate(savedInstanceState);
    }

    /***
     * 使用ActionBar{@link ActionBar}作为Title Bar
     * 配置Title Bar的信息
     */
    protected void configActionBar()
    {
        if (null != mActionBar)
        {
            mActionBar.setDisplayHomeAsUpEnabled(!(mActivity instanceof MainActivity));
        }
    }

    public SharedPreferences getSharedPreferences()
    {
        return  mSharedPreferences;
    }
    /**
     * 设置ActionBar的标题
     *
     * @param title：标题
     */
    public void setActionBarTitle(CharSequence title)
    {
        mActionBar.setTitle(title);
    }

    /***
     * 设置ActionBar的子标题
     *
     * @param subTitle：子标题
     */
    public void setActionBarSubTitle(CharSequence subTitle)
    {
        mActionBar.setSubtitle(subTitle);
    }

    /***
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = getCurrentFocus();
        if (inputMethodManager.isActive(focusView))
        {
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ToastUtils.showShortToast(mActivity, permissions+""+ grantResults);
    }
}
