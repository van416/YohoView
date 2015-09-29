package com.yoho.yohoview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import org.apache.cordova.CordovaWebView;

/**
 * YohoWebView内部初始化了WebSettings以及WebViewClient、YohoWebChromeClient对象
 * 1.对WebSettings进行了常用属性的初始化，并暴露给具体的Activity，方便对属性进行修改
 * 2.通过接口IYohoWebClient，把WebViewClient、YohoWebChromeClient内部的部分方法暴露给
 * 具体的Activity，交给其自己处理（业务逻辑）
 */
public class YohoWebView extends CordovaWebView
{
    private static final String TAG = "YohoWebView";

    private Context mContext;

    /**
     * 实现IYohoWebClient接口的对象,实际为具体的使用YohoWebView的一个Activity对象
     */
    private IYohoWebClient mYohoWebClient;

    private WebSettings mWebSettings;

    public YohoWebView(Context context)
    {
        super(context);
        init(context);
    }

    public YohoWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public YohoWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context)
    {
        mContext = context;
        initYohoWebInterface();
        initDefaultWebSettings();
        initWebViewClient();
        initWebChromeClient();
    }

    /**
     * 初始化IYohoWebClient对象
     */
    private void initYohoWebInterface()
    {
        if (IYohoWebClient.class.isInstance(mContext))
        {
            mYohoWebClient = (IYohoWebClient)mContext;
        }
        else
        {
            Log.e(TAG, "Your activity must implement IYohoWebClient to work!");
        }
    }

    /**
     * 初始化默认的WebSettings属性
     * 由于是继承了CordovaWebView，有部分属性已经在父类里设置（例如 支持JS，增加WebView缓存等）
     */
    private void initDefaultWebSettings()
    {
        mWebSettings = getSettings();
        // 不支持界面放大缩小
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setDisplayZoomControls(false);
        // 将图片调整到合适WebView的大小
        mWebSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        mWebSettings.setLoadWithOverviewMode(true);
    }

    /**
     * 初始化并设置WebViewClient
     */
    private void initWebViewClient()
    {
        WebViewClient webViewClient = new YohoWebViewClient(mYohoWebClient);
        setWebViewClient(webViewClient);
    }

    /**
     * 初始化并设置WebChromeClient
     */
    private void initWebChromeClient()
    {
        WebChromeClient webChromeClient = new YohoWebChromeClient(mYohoWebClient);
        setWebChromeClient(webChromeClient);
    }

    /**
     * 暴露出WebSettings对象,方便调用者设置不同于默认属性的WebSettings的值
     * @return WebSettings对象
     */
    public WebSettings getWebSettings()
    {
        return mWebSettings;
    }
}
