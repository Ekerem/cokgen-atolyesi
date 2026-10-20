package com.madebyus.cokgen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Uygulama internet varsa GitHub Pages'teki güncel sürümü açar,
 * yoksa APK'nın içine gömülü kopyayı (assets/index.html) açar.
 */
public class MainActivity extends Activity {

    private static final String LOCAL = "file:///android_asset/index.html";
    private WebView web;
    private boolean fellBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.addJavascriptInterface(new Bridge(), "AndroidApp");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                String u = req.getUrl().toString();
                if (u.startsWith(LOCAL) || u.startsWith(BuildConfig.REMOTE_URL)) {
                    if (u.startsWith(BuildConfig.REMOTE_URL)) fellBack = false;
                    return false;
                }
                // APK indirme bağlantısı gibi dış adresler tarayıcıda açılır.
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, req.getUrl()));
                } catch (Exception ignored) {
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
                if (req.isForMainFrame()) fallback();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest req, WebResourceResponse res) {
                if (req.isForMainFrame()) fallback();
            }
        });

        if (savedInstanceState != null) {
            web.restoreState(savedInstanceState);
        } else {
            web.loadUrl(BuildConfig.REMOTE_URL + "?app=" + System.currentTimeMillis());
        }
    }

    private void fallback() {
        if (!fellBack) {
            fellBack = true;
            web.loadUrl(LOCAL);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        if (web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }

    public class Bridge {
        @JavascriptInterface
        public String apkVersion() {
            return BuildConfig.VERSION_NAME;
        }
    }
}
