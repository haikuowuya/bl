package com.haikuowuya.bl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.util.SoutUtils;

import java.net.URLDecoder;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:14:54
 * 创建者：hkwy
 * 修改者：
 **/
public class WebViewActivity extends BaseActivity
{
    public static final String EXTRA_URL = "extra_url";
    private WebView mWebView;
    private ProgressBar mProgressBar;

    public static void actionWebView(Context context, String url)
    {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        configWebView();
        String url = getIntent().getStringExtra(EXTRA_URL);
//        SoutUtils.out("url = " +  url);
//        SoutUtils.out("urldecode = " + URLDecoder.decode(url));
        mWebView.loadUrl(url);
    }

    private void initView()
    {
        mWebView = (WebView) findViewById(R.id.wv_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
    }

    /***
     * config WebView
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void configWebView()
    {
        mWebView.setWebViewClient(new WebViewClientSub());
        mWebView.setWebChromeClient(new WebChromeClientSub());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
    }

    private class WebChromeClientSub extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            if (newProgress == 0)
            {
                mProgressBar.setProgress(0);
                if (mProgressBar.getVisibility() != View.VISIBLE)
                {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
            if (newProgress > mProgressBar.getProgress())
            {
                mProgressBar.setProgress(newProgress);
            }
            if (newProgress == 100)
            {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private class WebViewClientSub extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

    }

}
