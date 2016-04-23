package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
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
import com.haikuowuya.bl.model.BaseLineStopModel;
import com.haikuowuya.bl.model.LineStopModel;
import com.haikuowuya.bl.model.SearchLineModel;
import com.haikuowuya.bl.retrofit.SearchLineService;
import com.haikuowuya.bl.util.ToastUtils;

import java.io.Serializable;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private SearchLineModel mSearchLine;

    private int mListSelection = 0;

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
            mSearchLine = (SearchLineModel) getArguments().getSerializable(LineActivity.EXTRA_SEARCH_LINE);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLConstants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SearchLineService searchLineService = retrofit.create(SearchLineService.class);
        searchLineService.getBusLineDetail("GetBusLineDetail",mSearchLine.Guid).enqueue(new Callback<BaseLineStopModel>()
        {
            @Override
            public void onResponse(Call<BaseLineStopModel> call, Response<BaseLineStopModel> response)
            {
                if(response.isSuccessful())
                {
                    LinkedList<LineStopModel> standInfo = response.body().list.StandInfo;
                    if(null != standInfo &&!standInfo.isEmpty())
                    {
                        mListSelection = mFragmentLineBinding.lvListview.getSelectedItemPosition();
                        mFragmentLineBinding.lvListview.setAdapter(new LineStopListAdapter(standInfo));
                        mFragmentLineBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                LineStopModel lineStopModel = (LineStopModel) parent.getItemAtPosition(position);
                                StopActivity.actionStop(mActivity, lineStopModel);
                            }
                        });
                        mFragmentLineBinding.lvListview.setSelection(mListSelection);
                    }
                    else
                    {
                         ToastUtils.showShortToast(mActivity,"没有数据");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseLineStopModel> call, Throwable t)
            {

            }
        });



    }
}
