package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.StopActivity;
import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.adapter.StopListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentLineBinding;
import com.haikuowuya.bl.model.LineStop;
import com.haikuowuya.bl.model.SearchLine;
import com.haikuowuya.bl.model.StopItem;
import com.haikuowuya.bl.util.SoutUtils;
import com.haikuowuya.bl.util.ToastUtils;

import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:15:18
 * 创建者：hkwy
 * 修改者：
 **/
public class StopFragment extends BaseFragment
{
    public static StopFragment newInstance(Serializable lineStop)
    {
        StopFragment fragment = new StopFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StopActivity.EXTRA_LINE_STOP, lineStop);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FragmentLineBinding mFragmentLineBinding;
    private LineStop mLineStop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mFragmentLineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_line, container, false);
        return mFragmentLineBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (null != getArguments() && null != getArguments().getSerializable(StopActivity.EXTRA_LINE_STOP))
        {
            mLineStop = (LineStop) getArguments().getSerializable(StopActivity.EXTRA_LINE_STOP);
        }
        if (null != mLineStop)
        {
            final LinkedList<StopItem> stopItems = new LinkedList<>();
            new Thread()
            {
                public void run()
                {
                    try
                    {
                        SoutUtils.out("lineHref = " + mLineStop.stopHref);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(mLineStop.stopHref).openConnection();
                        int responseCode = httpURLConnection.getResponseCode();
                        if (httpURLConnection != null && responseCode == 200)
                        {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            Document document = DataUtil.load(inputStream, "utf-8", "");
                            if (null != document)
                            {
                                Element element = document.getElementsByTag("tbody").get(0);
                                Elements trElements = element.getElementsByTag("tr");
                                if (null != trElements && !trElements.isEmpty())
                                {
                                    if (trElements.size() > 1)
                                    {
                                        for (Element tmpElement : trElements)
                                        {
                                            Elements tdElements = tmpElement.getElementsByTag("td");

                                            if (null != tdElements && tdElements.size() == 5)
                                            {

                                                String lineNo;
                                                String lineHref;
                                                String stopName;
                                                String stopCar;
                                                String stopSpacing;
                                                String stopCarTime;
                                                Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                                lineNo = aElement.text();
                                                lineHref = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                                stopName = tdElements.get(1).text();
                                                stopCar = tdElements.get(2).text();
                                                stopCarTime = tdElements.get(3).text();
                                                stopSpacing = tdElements.get(4).text();
                                                StopItem stopItem = new StopItem();
                                                stopItem.lineHref = lineHref;
                                                stopItem.lineNo = lineNo;
                                                stopItem.stopCar = stopCar;
                                                stopItem.lineName = stopName;
                                                stopItem.stopCarTime = stopCarTime;
                                                stopItem.stopSpacing = stopSpacing;
                                                stopItems.add(stopItem);
                                            }
                                        }
                                        mActivity.runOnUiThread(new Runnable()
                                        {
                                            public void run()
                                            {
                                                mFragmentLineBinding.lvListview.setAdapter(new StopListAdapter(stopItems));
                                                mFragmentLineBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                                    {
                                                        StopItem stopItem = (StopItem) parent.getItemAtPosition(position);
                                                        SearchLine searchLine = new SearchLine();
                                                        searchLine.lineHref = stopItem.lineHref;
                                                        searchLine.lineNo = stopItem.lineNo;
                                                        searchLine.lineName = stopItem.lineName;
                                                        LineActivity.actionLine(mActivity,searchLine);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else
                                    {
                                        mActivity.runOnUiThread(new Runnable()
                                        {
                                            public void run()
                                            {
                                                ToastUtils.showShortToast(mActivity, "没有数据");
                                            }
                                        });
                                    }
                                }
                            }
                        }

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }
}
