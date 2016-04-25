package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.PREF;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.adapter.SearchLineListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentMainBinding;
import com.haikuowuya.bl.model.BaseSearchLineModel;
import com.haikuowuya.bl.model.SearchLineItem;
import com.haikuowuya.bl.retrofit.APIService;
import com.haikuowuya.bl.util.APIServiceUtils;
import com.haikuowuya.bl.util.LocationUtils;
import com.haikuowuya.bl.util.ToastUtils;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:13:46
 * 创建者：hkwy
 * 修改者：
 **/
public class MainFragment extends BaseFragment
{
    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    private FragmentMainBinding mFragmentMainBinding;
    private String mSearchLineNo ="";

    private  Callback<BaseSearchLineModel> mBaseSearchLineModelCallback = new Callback<BaseSearchLineModel>()
    {
        @Override
        public void onResponse(Call<BaseSearchLineModel> call, Response<BaseSearchLineModel> response)
        {
            if (response.isSuccessful())
            {
                LinkedList<SearchLineItem> searchLines = response.body().dataToList();
                if (null != searchLines && !searchLines.isEmpty())
                {
                    mFragmentMainBinding.lvListview.setAdapter(new SearchLineListAdapter(searchLines, mSearchLineNo));
                    mFragmentMainBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            SearchLineItem searchLine = (SearchLineItem) parent.getItemAtPosition(position);
                            LineActivity.actionLine(mActivity, searchLine);
                        }
                    });
                }
                else
                {
                    mFragmentMainBinding.lvListview.setAdapter(new SearchLineListAdapter(null, mSearchLineNo));
                    ToastUtils.showShortToast(mActivity, "没有数据");
                }
            }
        }

        @Override
        public void onFailure(Call<BaseSearchLineModel> call, Throwable t)
        {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mFragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return mFragmentMainBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mFragmentMainBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    doGetData();
                    mActivity.hideSoftKeyBoard();
                    return true;
                }
                return false;
            }
        });
        mFragmentMainBinding.etSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
               doGetData();
            }
        });
        mFragmentMainBinding.ivSearch.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                 doGetData();
                mActivity.hideSoftKeyBoard();
            }
        });
        getLocationInfo();
    }

    /***
     * 获取定位信息数据
     */
    private void getLocationInfo()
    {
        LocationUtils.startLocation(mActivity, new LocationUtils.OnLocationFinishListener()
        {
            @Override
            public void onLocationFinished(AMapLocation amapLocation)
            {
                mActivity.getSharedPreferences().edit().putString(PREF.PREF_LOCATION_LNG,amapLocation.getLongitude()+"").commit();
                mActivity.getSharedPreferences().edit().putString(PREF.PREF_LOCATION_LAT, amapLocation.getLatitude()+"").commit();
                mActivity.getSharedPreferences().edit().putString(PREF.PREF_LOCATION_ADDRESS, amapLocation.getAddress()).commit();
            }
        });
    }

    private void doGetData( )
    {
        mSearchLineNo = mFragmentMainBinding.etSearch.getText().toString();
        if(!TextUtils.isEmpty(mSearchLineNo))
        {
            APIService.V18 v18 = APIServiceUtils.getV18();
            v18.searchLine(mSearchLineNo).enqueue(mBaseSearchLineModelCallback);
        }
        else
        {
            mFragmentMainBinding.lvListview.setAdapter(new SearchLineListAdapter(null, mSearchLineNo));
        }

    }


}
