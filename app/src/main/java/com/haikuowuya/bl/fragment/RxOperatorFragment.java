package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentRxOperatorBinding;
import com.haikuowuya.bl.util.SoutUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 说明:
 * 日期:2016/5/9
 * 时间:14:10
 * 创建者：hkwy
 * 修改者：
 **/

/***
 * https://github.com/ReactiveX/RxJava/wiki
 * http://reactivex.io/documentation/operators.html
 * https://github.com/ReactiveX/RxJava/wiki/Alphabetical-List-of-Observable-Operators
 */
public class RxOperatorFragment extends BaseFragment
{

    public static  RxOperatorFragment newInstance()
    {
        RxOperatorFragment fragment = new RxOperatorFragment();
        return  fragment;
    }

    private FragmentRxOperatorBinding mFragmentRxOperatorBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
     mFragmentRxOperatorBinding =    DataBindingUtil.inflate(inflater, R.layout.fragment_rx_operator, container, false);
        return  mFragmentRxOperatorBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        from();


    }


    private void from()
    {
        String filterStr = "8888";
        String[] texts =new String[]{"kkkk","JJJJ","EEEE",filterStr};
        Observable.just(texts).subscribe(new Action1<String[]>()
        {
            @Override
            public void call(String[] strings)
            {
                Observable.from(strings).subscribe(new Action1<String>()
                {
                    @Override
                    public void call(String s)
                    {
                        SoutUtils.out(s);
                    }
                });
            }
        });
        //use Lambda
       Observable.just(texts).from(texts).subscribe(url-> SoutUtils.out(url +" lambda"));

        Observable.just(texts).flatMap(new Func1<String[], Observable<String>>()
        {
            @Override
            public Observable<String> call(String[] strings)
            {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                SoutUtils.out(s);
            }
        });
        //use Lambda
        Observable.just(texts).flatMap(urls ->Observable.from(urls)).subscribe(url -> SoutUtils.out(url+" lambda"));



        Observable.just(texts).flatMap(new Func1<String[], Observable<String >>()
        {
            @Override
            public Observable<String > call(String[] strings)
            {
                return Observable.from(strings);
            }
        }).flatMap(new Func1<String, Observable<Integer>>()
        {
            @Override
            public Observable<Integer> call(String s)
            {
                return Observable.from(new Integer[]{Integer.valueOf(TextUtils.isEmpty(s)?0:s.hashCode())});
            }
        }).subscribe(i->SoutUtils.out("i = " + i));

        //use Lambda
        Observable.just(texts).flatMap(urls -> Observable.from(urls))
                .flatMap(url -> Observable.from(new Integer[]{Integer.valueOf(TextUtils.isEmpty(url)?0:url.hashCode())}))
                .subscribe(i->SoutUtils.out("i = " + i + " lambda"));


        //use Lambda filter
        Observable.just(texts).flatMap(urls -> Observable.from(urls))
                .filter(url-> filterStr.equals(url))
                .subscribe(url ->SoutUtils.out(url + " filter "));

        //use Lambda take
        Observable.just(texts).flatMap(urls -> Observable.from(urls))
                .take(2)
                /*.takeLast(2)*/

                .subscribe(url -> SoutUtils.out(url + " take "));

        //use Lambda doOnNext
        Observable.just(texts).flatMap(urls-> Observable.from(urls))
                .filter(url -> !filterStr.equals(url))
                .take(3)
                .doOnNext(text -> SoutUtils.out(text+" doOnNext "   ))
                .subscribe(s -> SoutUtils.out(s   ));




    }




}
