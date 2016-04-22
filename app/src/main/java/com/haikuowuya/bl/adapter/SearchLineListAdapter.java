package com.haikuowuya.bl.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.databinding.SearchLineListItemBinding;
import com.haikuowuya.bl.model.SearchLine;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:14:05
 * 创建者：hkwy
 * 修改者：
 **/
public class SearchLineListAdapter extends BaseAdapter
{
    private LinkedList<SearchLine> mSearchLines;

    public SearchLineListAdapter(LinkedList<SearchLine> searchLines)
    {
        mSearchLines = searchLines;
    }

    @Override
    public int getCount()
    {
        return null == mSearchLines ? 0 : mSearchLines.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null == mSearchLines ? null : mSearchLines.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SearchLineListItemBinding searchLineListItemBinding ;
        if (convertView == null)
        {
            searchLineListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_line_list_item, parent, false);
            convertView = searchLineListItemBinding.getRoot();
            convertView.setTag(searchLineListItemBinding);
        }
        else
        {
            searchLineListItemBinding = (SearchLineListItemBinding) convertView.getTag();
        }
        searchLineListItemBinding.setSearchLine(mSearchLines.get(position));

        return convertView;
    }
}
