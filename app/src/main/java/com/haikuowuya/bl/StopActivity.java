package com.haikuowuya.bl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.fragment.StopFragment;
import com.haikuowuya.bl.model.LineStopItem;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:15:16
 * 创建者：hkwy
 * 修改者：
 **/
public class StopActivity extends BaseActivity
{
    public static final String EXTRA_LINE_STOP = "extra_line_stop";

    public static void actionStop(Context context, Serializable lineStop)
    {
        Intent intent = new Intent(context, StopActivity.class);
        intent.putExtra(EXTRA_LINE_STOP, lineStop);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

          LineStopItem lineStop = (LineStopItem) getIntent().getSerializableExtra(EXTRA_LINE_STOP);
        setActionBarTitle(lineStop.SName);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, StopFragment.newInstance(lineStop)).commit();
    }
}
