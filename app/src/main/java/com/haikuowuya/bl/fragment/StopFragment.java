package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.haikuowuya.bl.Constants;
import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.PREF;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.StopActivity;
import com.haikuowuya.bl.adapter.StopListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentLineBinding;
import com.haikuowuya.bl.model.BaseStopModel;
import com.haikuowuya.bl.model.BusStopInfoModel;
import com.haikuowuya.bl.model.LineStopItem;
import com.haikuowuya.bl.model.SearchLineItem;
import com.haikuowuya.bl.model.StopItem;
import com.haikuowuya.bl.retrofit.APIService;
import com.haikuowuya.bl.util.APIServiceUtils;
import com.haikuowuya.bl.util.BLDataUtils;
import com.haikuowuya.bl.util.SoutUtils;

import org.jsoup.nodes.Document;

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
    private LineStopItem mLineStop;
    private Handler mHandler = new Handler();

    private Callback<BaseStopModel> mBaseStopModelCallback = new Callback<BaseStopModel>()
    {
        @Override
        public void onResponse(Call<BaseStopModel> call, Response<BaseStopModel> response)
        {
            SoutUtils.out("StopFragment response = " + response.raw().toString() );
            if(response.isSuccessful())
            {
                onGetDataSuccess(response.body());
            }

        }

        @Override
        public void onFailure(Call<BaseStopModel> call, Throwable t)
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



    private void doGetData()
    {
        final APIService.V18 v18 = APIServiceUtils.getV18();
        final String lng = mActivity.getSharedPreferences().getString(PREF.PREF_LOCATION_LNG, "");
        final String lat = mActivity.getSharedPreferences().getString(PREF.PREF_LOCATION_LAT, "");
         v18.getStationInfo(mLineStop.SCode,lng,lat).enqueue(mBaseStopModelCallback);
        APIServiceUtils.getWeb().getStationInfo(mLineStop.SCode, mLineStop.SName).enqueue(new Callback<Document>()
        {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response)
            {
                if(response.isSuccessful())
                {
                    LinkedList<StopItem> stopItems =   BLDataUtils.htmlToStop(response.body());
                    BaseStopModel baseStopModel = new BaseStopModel();
                    baseStopModel.data = new BusStopInfoModel();
                    baseStopModel.data.list = stopItems;
                  // onGetDataSuccess(baseStopModel);
                }
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        doGetData();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mHandler.removeCallbacks(mDelayRunnable);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (null != getArguments() && null != getArguments().getSerializable(StopActivity.EXTRA_LINE_STOP))
        {
            mLineStop = (LineStopItem) getArguments().getSerializable(StopActivity.EXTRA_LINE_STOP);
        }
    }
    public void onGetDataSuccess(BaseStopModel baseStopModel )
    {
        LinkedList<StopItem> stopModels = baseStopModel.data.list;
        if(null != stopModels &&!stopModels.isEmpty())
        {
            mFragmentLineBinding.lvListview.setAdapter(new StopListAdapter(stopModels));
            mFragmentLineBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    StopItem stopModel = (StopItem) parent.getItemAtPosition(position);
                    SearchLineItem searchLine = new SearchLineItem();
                    searchLine.LName = stopModel.LName;
                    searchLine.Guid = stopModel.Guid;
                    searchLine.LDirection = stopModel.LDirection;
                    LineActivity.actionLine(mActivity,searchLine);
                }
            });
        }
        mHandler.postDelayed(mDelayRunnable, Constants.DELAY_TIME_IN_MM);
    }
}
