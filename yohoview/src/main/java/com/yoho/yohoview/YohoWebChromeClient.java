package com.yoho.yohoview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;

import org.apache.cordova.LOG;

/**
 * Created by yoho on 2015/9/24.
 */
public class YohoWebChromeClient extends WebChromeClient
{
    private static final String TAG = "YohoWebChromeClient";

    /**
     * 最大缓存容量
     */
    private static long MAX_QUOTA = 100 * 1024 * 1024;

    /**
     * 实现IYohoWebClient接口的对象,实际为具体的使用YohoWebView的一个Activity对象
     */
    private IYohoWebClient mYohoWebClient;

    public YohoWebChromeClient(IYohoWebClient yohoWebClient)
    {
        mYohoWebClient = yohoWebClient;
    }

    @Override
    public void onReceivedTitle(WebView view, String title)
    {
        mYohoWebClient.onReceivedTitle(view, title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress)
    {
        mYohoWebClient.onProgressChanged(view, newProgress);
    }

    /**
     * 扩充缓存的容量
     */
    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize,
                                        long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater)
    {
        LOG.d(TAG, "onExceededDatabaseQuota estimatedSize: %d  currentQuota: %d  totalUsedQuota: %d", estimatedSize, currentQuota, totalUsedQuota);
        quotaUpdater.updateQuota(MAX_QUOTA);
    }

    /**
     * 弹出一个Dialog框警告用户
     *
     * @param view
     * @param url
     * @param message
     * @param result
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
    {
        showAlertDialog("Alert", message, result);
        return true;
    }

    /**
     * 弹出一个Dialog框提示用户确认
     *
     * @param view
     * @param url
     * @param message
     * @param result
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result)
    {
        showAlertDialog("Confirm", message, result);
        return true;
    }

    /**
     * 显示一个AlertDialog
     * @param title Dialog标题
     * @param message Dialog内容
     * @param result
     */
    private void showAlertDialog(String title, String message, final JsResult result)
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(mYohoWebClient.getActivity());
        dlg.setMessage(message);
        dlg.setTitle(title);
        dlg.setCancelable(true);
        dlg.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        result.confirm();
                    }
                });
        dlg.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        result.cancel();
                    }
                });
        dlg.setOnCancelListener(
                new DialogInterface.OnCancelListener()
                {
                    public void onCancel(DialogInterface dialog)
                    {
                        result.cancel();
                    }
                });
        dlg.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            //DO NOTHING
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    result.cancel();
                    return false;
                }
                else
                    return true;
            }
        });
        dlg.create();
        dlg.show();
    }

}

