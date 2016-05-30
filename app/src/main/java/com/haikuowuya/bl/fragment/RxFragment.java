package com.haikuowuya.bl.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haikuowuya.bl.R;
import com.haikuowuya.bl.RxOperatorActivity;
import com.haikuowuya.bl.adapter.rx.RxListAdapter;
import com.haikuowuya.bl.base.BaseFragment;
import com.haikuowuya.bl.databinding.FragmentRxBinding;
import com.haikuowuya.bl.util.SoutUtils;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * 说明:
 * 日期:2016/4/26
 * 时间:20:44
 * 创建者：hkwy
 * 修改者：
 **/

/**
 * 参考：http://mp.weixin.qq.com/s?__biz=MzA4MjA0MTc4NQ==&mid=504089555&idx=3&sn=094a2c4f7072fb9e113b12541001f622#rd
 * http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/
 * http://gank.io/post/560e15be2dca930e00da1083
 * http://gank.io/post/published
 */
public class RxFragment extends BaseFragment
{
    public static final RxFragment newInstance()
    {
        RxFragment fragment = new RxFragment();
        return fragment;
    }

    private FragmentRxBinding mFragmentRxBinding;

    private int mNum = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mFragmentRxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rx, container, false);
        return mFragmentRxBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Observable.just("hello Lambda").subscribe(s -> SoutUtils.out(s));

        //1.
        //===================================================
        Observable<LinkedList<String>> just = Observable.just(getRxDemoList("just"));
        just.subscribe(new Subscriber<LinkedList<String>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(LinkedList<String> strings)
            {
                mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings));
            }
        });
        //TODO
        //use lambda :
        //Observable.just(getRxDemoList("just")).subscribe(strings ->   mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings)));
        //TODO
        //===================================================

        //2.
        Observable fromCallable = Observable.fromCallable(new Callable<LinkedList<String>>()
        {
            @Override
            public LinkedList<String> call() throws Exception
            {
                return getRxDemoList("fromCallable");
            }
        });

        Subscription subscription = fromCallable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LinkedList<String>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(LinkedList<String> strings)
                    {
                        mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings));
                    }
                });

        //use Lambda
//        Observable.fromCallable(() -> getRxDemoList("fromCallable"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(strings ->      mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings)));
//

        //3.
        Single single = Single.fromCallable(new Callable<LinkedList<String>>()
        {
            @Override
            public LinkedList<String> call() throws Exception
            {
                return getRxDemoList("single");
            }
        });
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<LinkedList<String>>()
                {
                    @Override
                    public void onSuccess(LinkedList<String> strings)
                    {
                        mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings));
                    }

                    @Override
                    public void onError(Throwable error)
                    {
                        SoutUtils.out("error = " + error.toString());
                    }
                });
        //use Lambda
//        Single.fromCallable(()->getRxDemoList("single"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(strings -> mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings)));
        //4.
        final PublishSubject publishSubject = PublishSubject.create();
        publishSubject/*.debounce(400, TimeUnit.MILLISECONDS)*/.subscribe(new Subscriber<Integer>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(Integer o)
            {
                mFragmentRxBinding.btnSubject.setText("" + o.intValue());
            }
        });
        //use Lambda
//        PublishSubject.create().map(o1 -> Integer.parseInt(o1.toString())).subscribe(o ->mFragmentRxBinding.btnSubject.setText("" + o.intValue()) );

        //5.
        mFragmentRxBinding.btnSubject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNum++;
                publishSubject.onNext(mNum);
            }
        });

        //onclick use Lambda
//        mFragmentRxBinding.btnSubject.setOnClickListener(v -> {
//            mNum++;
//            publishSubject.onNext(mNum);});

        //6.
        /*map方法将给定的对象通过 Func1函数转化 为 ---》  55 数字 ---》字符串*/
        Single.just(55).map(new Func1<Integer, String>()
        {
            @Override
            public String call(Integer integer)
            {
                return String.valueOf(integer);
            }
        }).subscribe(new SingleSubscriber<String>()
        {
            @Override
            public void onSuccess(String value)
            {
                mFragmentRxBinding.btnMap.setText(value);
            }

            @Override
            public void onError(Throwable error)
            {

            }
        });
        //use Lambda
//        Single.just(55).map(integer -> String.valueOf(integer)).subscribe(value -> mFragmentRxBinding.btnMap.setText(value))


        //7.
        final PublishSubject searchSubject = PublishSubject.create();
        searchSubject.debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Func1<String, LinkedList<String>>()
                {
                    @Override
                    public LinkedList<String> call(String s)
                    {
                        return getRxDemoList("debounce " + s);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LinkedList<String>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(LinkedList<String> strings)
                    {
                        mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings));
                    }
                });

//use Lambda
//        PublishSubject.create().debounce(400, TimeUnit.MILLISECONDS)
//                .observeOn(Schedulers.io())
//                .map(s->getRxDemoList("debounce " + s))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(strings -> mFragmentRxBinding.lvListview.setAdapter(new RxListAdapter(strings)));
        mFragmentRxBinding.etSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                searchSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //跳转到 rxoperatoractivity
        //use Lambda
        mFragmentRxBinding.btnRxOperator.setOnClickListener(view-> RxOperatorActivity.actionRxOperator(mActivity));
    }

    public LinkedList<String> getRxDemoList(String flag)
    {
        if (flag.equals("fromCallable"))
        {
            try
            {
                Thread.sleep(4000L);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        if (flag.equals("single"))
        {
            try
            {
                Thread.sleep(8000L);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            throw new IllegalArgumentException("参数不合法");
        }
        if (flag.contains("debounce"))
        {
        }
        LinkedList<String> items = new LinkedList<>();
        items.add("BASE:just " + flag);
        items.add("BASE:fromCallable " + flag);/*使用Observable.fromCallable()方法有两点好处：获取要发送的数据的代码只会在有Observer订阅之后执行。获取数据的代码可以在子线程中执行。*/
        items.add("BASE:single " + flag);
        items.add("BASE:subject  " + flag);/*从一端把数据注入，结果就会从另一端输出*/
        items.add("BASE:map " + flag);/*接收一个数据，然后输出另一个数据，当然输入输出的两个数据之间是有联系的*/
        items.add("BASE:deboundce " + flag);/*当用户400ms都没有改变输入内容*/
        return items;
    }
}
