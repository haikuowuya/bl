package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.haikuowuya.bl.BLApplication;
import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.PREF;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.StopActivity;
import com.haikuowuya.bl.adapter.LineStopListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentLineBinding;
import com.haikuowuya.bl.model.BaseLineStopModel;
import com.haikuowuya.bl.model.LineStopItem;
import com.haikuowuya.bl.model.SearchLineItem;
import com.haikuowuya.bl.retrofit.APIService;
import com.haikuowuya.bl.util.APIServiceUtils;
import com.haikuowuya.bl.util.SoutUtils;
import com.haikuowuya.bl.util.ToastUtils;

import java.io.Serializable;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private SearchLineItem mSearchLine;
    private int mListSelection = 0;
    private Handler mHandler = new Handler();

    private Callback<BaseLineStopModel> mLineStopModelCallback = new Callback<BaseLineStopModel>()
    {
        @Override
        public void onResponse(Call<BaseLineStopModel> call, Response<BaseLineStopModel> response)
        {
            SoutUtils.out("LineFragment response = " + response.raw().toString() );
            if (response.isSuccessful())
            {
                BaseLineStopModel baseLineStopModel = response.body();
                onGetDataSuccess(baseLineStopModel);
            }
        }

        @Override
        public void onFailure(Call<BaseLineStopModel> call, Throwable t)
        {

        }
    };

    private Runnable mDelayRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            doGetData();
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
            mSearchLine = (SearchLineItem) getArguments().getSerializable(LineActivity.EXTRA_SEARCH_LINE);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        doGetData();
    }

    /****
     * 去获取数据
     */
    private void doGetData()
    {
        final APIService.V18 v18 = APIServiceUtils.getV18();
        final String lng = mActivity.getSharedPreferences().getString(PREF.PREF_LOCATION_LNG, "");
        final String lat = mActivity.getSharedPreferences().getString(PREF.PREF_LOCATION_LAT, "");
        v18.getLineInfo(mSearchLine.Guid, lng, lat).enqueue(mLineStopModelCallback);
    }

    /***
     * 数据获取成功
     * @param baseLineStopModel
     */
    private void onGetDataSuccess(BaseLineStopModel baseLineStopModel)
    {
        LinkedList<LineStopItem> standInfo = baseLineStopModel.data.StandInfo;
        if (null != standInfo && !standInfo.isEmpty())
        {
            mListSelection = mFragmentLineBinding.lvListview.getFirstVisiblePosition();
            mFragmentLineBinding.lvListview.setAdapter(new LineStopListAdapter(standInfo));
            mFragmentLineBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    LineStopItem lineStopModel = (LineStopItem) parent.getItemAtPosition(position);
                    StopActivity.actionStop(mActivity, lineStopModel);
                }
            });
            mFragmentLineBinding.lvListview.setSelection(mListSelection);
        }
        else
        {
            ToastUtils.showShortToast(mActivity, "没有数据");
        }
        mHandler.postDelayed(mDelayRunnable, BLApplication.DELAY_TIME_IN_MM);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mHandler.removeCallbacks(mDelayRunnable);
    }
}
