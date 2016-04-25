package com.haikuowuya.bl.adapter;

import android.databinding.DataBindingUtil;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.databinding.SearchLineListItemBinding;
import com.haikuowuya.bl.model.SearchLineItem;

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
    private LinkedList<SearchLineItem> mSearchLines;

    private String mKeyword;
    public SearchLineListAdapter(LinkedList<SearchLineItem> searchLines, String keyword)
    {
        mSearchLines = searchLines;
        mKeyword = keyword;
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
        SearchLineItem searchLineModel = mSearchLines.get(position);
        searchLineListItemBinding.setSearchLine(searchLineModel);
        highlightSearchKeyword(searchLineListItemBinding.tvBusNo, searchLineModel);
        return convertView;
    }

    private void highlightSearchKeyword(TextView tvBusNo, SearchLineItem searchLineModel)
    {
        SpannableStringBuilder busNoBuilder = new SpannableStringBuilder(
                searchLineModel.LName);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                0xFFFF0000);
        if (!TextUtils.isEmpty(mKeyword) && searchLineModel.LName.contains(mKeyword))
        {
            int start = -1, end = -1;
            start = searchLineModel.LName.indexOf(mKeyword.toString());
            end = start + mKeyword.length();
            busNoBuilder.setSpan(foregroundColorSpan, start, end,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        tvBusNo.setText(busNoBuilder);
    }


}
