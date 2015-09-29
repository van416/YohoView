package com.yoho.yohoview;

import android.webkit.JsResult;
import android.webkit.WebView;

import org.apache.cordova.CordovaInterface;

/**
 * 将原生WebViewClient以及WebChromeClient里的部分方法提取，
 * 交给实际实现此接口的Activity去处理。
 */
public interface IYohoWebClient extends CordovaInterface
{
    public abstract boolean shouldOverrideUrlLoading(WebView view, String url);

    public abstract void onReceivedTitle(WebView view, String title);

    public abstract void onProgressChanged(WebView view, int newProgress);
}
