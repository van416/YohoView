package com.yoho.yohoviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.yoho.yohoview.IYohoWebClient;
import com.yoho.yohoview.YohoWebView;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用YohoWebView的模板类，把公共的逻辑处理放在此类，具体实现业务的Activity集成此类即可
 *
 * 1.子类中如果需要设置YohoWebView不同的属性，通过方法YohoWebView.getWebSettings()取出内部的
 * WebSetting重新设置
 * 2.子类中如果有具体的业务逻辑与模板实现不同（原写在WebViewClient、WebChromeClient里的逻辑），
 * 可在子类中重写IYohoWebClient里的方法
 */
public abstract class YohoWebActivity extends Activity implements IYohoWebClient
{

    /**
     * 布局资源ID
     */
    protected int mLayoutResId;

    /**
     * YohoWebView控件
     * 内部初始化了WebView的常用属性(如支持JS，缓存等)，同时设置好了WebViewClient以及WebChromeClient
     */
    protected YohoWebView vWebView;

    private final ExecutorService mThreadPool = Executors.newCachedThreadPool();

    public YohoWebActivity(int resId)
    {
        mLayoutResId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutResId);
        vWebView = initWebView();

        // 添加白名单,否则LoadUrl无法加载URL
        Config.init();
        Config.addWhiteListEntry("*", true);
    }

    protected abstract YohoWebView initWebView();

    @Override
    public Activity getActivity()
    {
        return this;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedTitle(WebView view, String title)
    {
        // TODO 如果需要改变界面Title就重写
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress)
    {
        // TODO 如果需要在加载URL时显示进度条就重写
    }

    /**
     * 在Cordova某些插件执行的时候会调用
     * @return
     */
    @Override
    public ExecutorService getThreadPool()
    {
        return mThreadPool;
    }

    @Override
    public Object onMessage(String id, Object data)
    {
        return null;
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin)
    {

    }

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode)
    {

    }

}
