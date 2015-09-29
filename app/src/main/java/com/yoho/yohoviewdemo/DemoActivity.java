package com.yoho.yohoviewdemo;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.yoho.yohoview.YohoWebView;

/**
 * 具体需要内嵌WebView的Activity
 */
public class DemoActivity extends YohoWebActivity {
    /**
     * 界面标题
     */
    private TextView vTitleTxt;

    public DemoActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vTitleTxt = (TextView) findViewById(R.id.yoho_title);

        // 如果需要对WebSetting增加或修改一些属性,就取出YohoWebView内部的Setting
        WebSettings settings = vWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // 加载URL
        vWebView.loadUrl("http://m.yohobuy.com/");
    }

    @Override
    protected YohoWebView initWebView() {
        return (YohoWebView) findViewById(R.id.yoho_webview);
    }

    /**
     * 如果子类的某个具体业务与模板不同，就重写
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        vTitleTxt.setText(title);
    }
}
