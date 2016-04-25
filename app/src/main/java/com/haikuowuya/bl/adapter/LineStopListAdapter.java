package com.haikuowuya.bl.adapter;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.databinding.LineStopListItemBinding;
import com.haikuowuya.bl.model.LineStopItem;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:14:05
 * 创建者：hkwy
 * 修改者：
 **/
public class LineStopListAdapter extends BaseAdapter
{
    private LinkedList<LineStopItem> mLineStops;

    public LineStopListAdapter(LinkedList<LineStopItem> lineStops)
    {
        mLineStops = lineStops;
    }

    @Override
    public int getCount()
    {
        return null == mLineStops ? 0 : mLineStops.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null == mLineStops ? null : mLineStops.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LineStopListItemBinding lineStopListItemBinding;
        if (convertView == null)
        {
            lineStopListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.line_stop_list_item, parent, false);
            convertView = lineStopListItemBinding.getRoot();
            convertView.setTag(lineStopListItemBinding);
        }
        else
        {
            lineStopListItemBinding = (LineStopListItemBinding) convertView.getTag();
        }
        LineStopItem lineStop = mLineStops.get(position);
        lineStopListItemBinding.setLineStop(lineStop);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lineStopListItemBinding.ivBus.getLayoutParams();
        if (!TextUtils.isEmpty(lineStop.InTime)  )
        {
            if (!isArrivedValidated(lineStop))
            {
                layoutParams.removeRule(RelativeLayout.CENTER_IN_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lineStopListItemBinding.ivBus.setLayoutParams(layoutParams);
                lineStopListItemBinding.ivBus.setImageResource(R.drawable.ic_bus_ontheway);
            }
            else
            {
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                lineStopListItemBinding.ivBus.setLayoutParams(layoutParams);
                lineStopListItemBinding.ivBus.setImageResource(R.drawable.ic_bus_arrive);
            }
        }
        else
        {
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            lineStopListItemBinding.ivBus.setLayoutParams(layoutParams);
            lineStopListItemBinding.ivBus.setImageResource(R.drawable.circle_stop_background);
        }
        return convertView;
    }

    /**
     * 处理路上大于1分钟而没有更新当前站点的车辆，以防止延误
     * 说明 一个到站的车辆最多可以在当前站点停留1分钟
     *
     * @param lineStop
     * @return true：到站车辆有效
     */
    private boolean isArrivedValidated(LineStopItem lineStop)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = 0, minute = 0, second = 0;
        if (!TextUtils.isEmpty(lineStop.InTime)
                && lineStop.InTime.contains(":"))
        {
            String[] tmp = lineStop.InTime.split(":");
            hourOfDay = Integer.parseInt(tmp[0]);
            minute = Integer.parseInt(tmp[1]);
            second = Integer.parseInt(tmp[2]);
        }
        Calendar busCalendar = Calendar.getInstance();
        busCalendar.set(year, month, day, hourOfDay, minute, second);
        return calendar.getTimeInMillis() - busCalendar.getTimeInMillis() < 1 * 60 * 1000;
    }

}
