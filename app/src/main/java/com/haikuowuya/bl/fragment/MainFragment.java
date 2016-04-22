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
import com.haikuowuya.bl.adapter.SearchLineListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentMainBinding;
import com.haikuowuya.bl.model.BaseModel;
import com.haikuowuya.bl.model.SearchLine;
import com.haikuowuya.bl.retrofit.SearchLineService;
import com.haikuowuya.bl.util.BLDataUtils;
import com.haikuowuya.bl.util.JsoupUtils;
import com.haikuowuya.bl.util.SoutUtils;
import com.haikuowuya.bl.util.ToastUtils;

import org.jsoup.nodes.Document;

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
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://content.2500city.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SearchLineService searchLineService = retrofit.create(SearchLineService.class);
        searchLineService.searchLine("SearchBusLine",lineNo).enqueue(new Callback<BaseModel>()
        {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response)
            {
                SoutUtils.out("str = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t)
            {

            }
        });
        new Thread()
        {
            public void run()
            {
                Document document = JsoupUtils.searchFuzzyLineDocument(lineNo);
                final LinkedList<SearchLine> searchLines = BLDataUtils.htmlToSearchLine(document);
                mActivity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if (!searchLines.isEmpty())
                        {
                            mFragmentMainBinding.lvListview.setAdapter(new SearchLineListAdapter(searchLines));
                            mFragmentMainBinding.lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    SearchLine searchLine = (SearchLine) parent.getItemAtPosition(position);
                                    LineActivity.actionLine(mActivity, searchLine);
                                }
                            });
                        }
                        else
                        {
                            ToastUtils.showShortToast(mActivity, "没有数据");
                        }
                    }
                });
            }

        }.start();
    }
}
