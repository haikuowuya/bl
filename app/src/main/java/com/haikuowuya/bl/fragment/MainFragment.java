package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.haikuowuya.bl.LineActivity;
import com.haikuowuya.bl.R;
import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.adapter.SearchLineListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentMainBinding;
import com.haikuowuya.bl.model.BaseSearchLineModel;
import com.haikuowuya.bl.model.SearchLineModel;
import com.haikuowuya.bl.retrofit.APIService;
import com.haikuowuya.bl.retrofit.SearchLineService;
import com.haikuowuya.bl.util.SoutUtils;
import com.haikuowuya.bl.util.ToastUtils;

import java.util.LinkedList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                    mockSearch(mFragmentMainBinding.etSearch.getText().toString());
                    mActivity.hideSoftKeyBoard();
                    return true;
                }
                return false;
            }
        });
        mFragmentMainBinding.ivSearch.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mockSearch(mFragmentMainBinding.etSearch.getText().toString());
                mActivity.hideSoftKeyBoard();
            }
        });
    }

    private void mockSearch(final String lineNo)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLConstants.BASE_API_18_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       APIService.V18 v18  = retrofit.create(APIService.V18.class);
        v18.searchLine(lineNo).enqueue(new Callback<BaseSearchLineModel>()
        {
            @Override
            public void onResponse(Call<BaseSearchLineModel> call, Response<BaseSearchLineModel> response)
            {
                if (response.isSuccessful())
                {
                    LinkedList<SearchLineModel> searchLines = response.body().dataToList();
                    if(null != searchLines &&!searchLines.isEmpty())
                    {
                        mFragmentMainBinding.lvListview.setAdapter(new SearchLineListAdapter(searchLines));
                        mFragmentMainBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                SearchLineModel searchLine = (SearchLineModel) parent.getItemAtPosition(position);
                                LineActivity.actionLine(mActivity, searchLine);
                            }
                        });
                    }
                    else
                    {
                        ToastUtils.showShortToast(mActivity,"没有数据");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseSearchLineModel> call, Throwable t)
            {

            }
        });

    }
}
