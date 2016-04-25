package com.haikuowuya.bl.adapter;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.databinding.StopListItemBinding;
import com.haikuowuya.bl.model.StopItem;
import com.haikuowuya.bl.util.SoutUtils;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:14:05
 * 创建者：hkwy
 * 修改者：
 **/
public class StopListAdapter extends BaseAdapter
{
    private LinkedList<StopItem> mStopItems;

    public StopListAdapter(LinkedList<StopItem> stopItems)
    {
        mStopItems = stopItems;
    }

    @Override
    public int getCount()
    {
        return null == mStopItems ? 0 : mStopItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null == mStopItems ? null : mStopItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        StopListItemBinding stopListItemBinding;
        if (convertView == null)
        {
            stopListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.stop_list_item, parent, false);
            convertView = stopListItemBinding.getRoot();
            convertView.setTag(stopListItemBinding);
        }
        else
        {
            stopListItemBinding = (StopListItemBinding) convertView.getTag();
        }
        StopItem stopItem = mStopItems.get(position);
        stopListItemBinding.setStopItem(stopItem);
        stopListItemBinding.tvBusDirection.setText( handleBusDirection(stopItem.LDirection));
        return convertView;
    }

    private String handleBusDirection(String direction)
    {
        if(!TextUtils.isEmpty(direction) && direction.contains("=>"))
        {
            direction = direction.replace("=>","\n");
        }
        return  direction;
    }
}
