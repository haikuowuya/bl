package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.StopActivity;
import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.adapter.LineStopListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentLineBinding;
import com.haikuowuya.bl.model.LineStop;
import com.haikuowuya.bl.model.SearchLine;
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
public class LineFragment extends BaseFragment
{
    public static LineFragment newInstance(Serializable searchLine)
    {
        LineFragment fragment = new LineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LineActivity.EXTRA_SEARCH_LINE, searchLine);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FragmentLineBinding mFragmentLineBinding;
    private SearchLine mSearchLine;

    private int mListSelection = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            mListSelection = mFragmentLineBinding.lvListview.getSelectedItemPosition();
            new Thread(mAutoRefreshRunnable).start();
        }
    };

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
        if (null != getArguments() && null != getArguments().getSerializable(LineActivity.EXTRA_SEARCH_LINE))
        {
            mSearchLine = (SearchLine) getArguments().getSerializable(LineActivity.EXTRA_SEARCH_LINE);
        }
        if (null != mSearchLine)
        {
            new Thread(mAutoRefreshRunnable).start();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mHandler.removeCallbacks(mAutoRefreshRunnable);
    }



    private  Runnable mAutoRefreshRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            final LinkedList<LineStop> lineStops = new LinkedList<>();
            try
            {
                SoutUtils.out("lineHref = " + mSearchLine.lineHref);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(mSearchLine.lineHref).openConnection();
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

                                    if (null != tdElements && tdElements.size() == 4)
                                    {
                                        LineStop lineStop = new LineStop();
                                        String stopHref;
                                        String stopName;
                                        String stopCode;
                                        String stopCar;
                                        String stopCarTime;
                                        Element aElement = tdElements.get(0).getElementsByTag("a").get(0);
                                        stopName = aElement.text();
                                        stopHref = URLConstants.BUS_LINE_QUERY_PREFIX + aElement.attr("href");
                                        stopCode = tdElements.get(1).text();
                                        stopCar = tdElements.get(2).text();
                                        stopCarTime = tdElements.get(3).text();
                                        lineStop.stopCar = stopCar;
                                        lineStop.stopCarTime = stopCarTime;
                                        lineStop.stopCode = stopCode;
                                        lineStop.stopName = stopName;
                                        lineStop.stopHref = stopHref;
                                        lineStops.add(lineStop);
                                    }
                                }
                                mActivity.runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        mFragmentLineBinding.lvListview.setAdapter(new LineStopListAdapter(lineStops));
                                        mFragmentLineBinding.lvListview.setSelection(mListSelection);
                                        mFragmentLineBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {
                                                LineStop lineStop = (LineStop) parent.getItemAtPosition(position);
                                                StopActivity.actionStop(mActivity, lineStop);
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
                mHandler.sendEmptyMessageDelayed(0,20*1000L);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };
}
