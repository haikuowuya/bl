package com.haikuowuya.bl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.fragment.LineFragment;
import com.haikuowuya.bl.model.SearchLine;

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

        SearchLine searchLine = (SearchLine) getIntent().getSerializableExtra(EXTRA_SEARCH_LINE);
        setActionBarTitle(searchLine.lineNo+"路公交");
        setActionBarSubTitle(searchLine.lineName);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, LineFragment.newInstance(searchLine)).commit();
    }
}
