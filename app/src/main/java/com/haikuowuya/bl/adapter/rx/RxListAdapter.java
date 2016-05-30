package com.haikuowuya.bl.adapter.rx;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.databinding.RxListItemBinding;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/5/6
 * 时间:15:22
 * 创建者：hkwy
 * 修改者：
 **/
public class RxListAdapter extends BaseAdapter
{
    public LinkedList<String> mStrings;

    public RxListAdapter(LinkedList<String> strings)
    {
        mStrings = strings;
    }

    @Override
    public int getCount()
    {
        return null == mStrings ? 0 : mStrings.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null == mStrings ? null : mStrings.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RxListItemBinding rxListItemBinding;
        if (convertView == null)
        {
            rxListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rx_list_item, parent, false);
            convertView = rxListItemBinding.getRoot();
            convertView.setTag(rxListItemBinding);
        }
        else
        {
            rxListItemBinding = (RxListItemBinding) convertView.getTag();
        }
        rxListItemBinding.setText(mStrings.get(position));
        return convertView;
    }
}
