package com.haikuowuya.bl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.fragment.LineFragment;
import com.haikuowuya.bl.model.SearchLineItem;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:15:16
 * 创建者：hkwy
 * 修改者：
 **/
public class LineActivity extends BaseActivity
{
    public static final String EXTRA_SEARCH_LINE = "extra_search_line";

    public static void actionLine(Context context, Serializable searchLine)
    {
        Intent intent = new Intent(context, LineActivity.class);
        intent.putExtra(EXTRA_SEARCH_LINE, searchLine);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        SearchLineItem searchLine = (SearchLineItem) getIntent().getSerializableExtra(EXTRA_SEARCH_LINE);
        setActionBarTitle(searchLine.LName);
        setActionBarSubTitle(searchLine.LDirection);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, LineFragment.newInstance(searchLine)).commit();
    }
}
