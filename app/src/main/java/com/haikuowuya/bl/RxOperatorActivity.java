package com.haikuowuya.bl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.fragment.RxOperatorFragment;

/**
 * 说明:操作符
 * 日期:2016/4/15
 * 时间:15:16
 * 创建者：hkwy
 * 修改者：
 **/
public class RxOperatorActivity extends BaseActivity
{

    public static void actionRxOperator(Context context     )
    {
        Intent intent = new Intent(context, RxOperatorActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_operator);
        setActionBarTitle("RX");
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, RxOperatorFragment.newInstance( )).commit();
    }
}
