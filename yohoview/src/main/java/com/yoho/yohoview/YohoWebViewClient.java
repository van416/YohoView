package com.yoho.yohoview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yoho on 2015/9/24.
 */
public class YohoWebViewClient extends WebViewClient
{
    /**
     * 实现IYohoWebClient接口的对象,实际为具体的使用YohoWebView的一个Activity对象
     */
    private IYohoWebClient mYohoWebClient;

    public YohoWebViewClient(IYohoWebClient yohoWebInterface)
    {
        mYohoWebClient = yohoWebInterface;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        return mYohoWebClient.shouldOverrideUrlLoading(view, url);
    }

}