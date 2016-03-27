package com.echo.tmallapppopdialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Created by jiangecho on 16/3/27.
 */
public class PenetrateWebViewContainer extends FrameLayout {
    public WebView webView;
    private String url;
    private boolean loadFinished = false;
    private boolean finishInflate = true;
    private int penetrateAlpha = 255;

    public PenetrateWebViewContainer(Context context) {
        super(context);
        init(context, null);
    }

    public PenetrateWebViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PenetrateWebViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PenetrateWebViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setPenetrateAlpha(int alpha){
        this.penetrateAlpha = alpha;
    }

    private void init(Context context, AttributeSet attr) {
        LayoutInflater.from(context).inflate(R.layout.webview_contaner, this, true);
        // TODO maybe need to set style

        webView = (WebView) findViewById(R.id.webView);
        if (webView != null) {
            finishInflate = true;
            initWebView(webView);

            if (url != null) {
                webView.loadUrl(url);
            }

        }
    }

    private void initWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadFinished = true;
                setVisibility(VISIBLE);
            }
        });

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        webView = (WebView) findViewById(R.id.webView);
        initWebView(webView);

        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            final int pixel = getDrawingCache().getPixel((int) ev.getX(), (int) ev.getY());
            final int alpha = 255 - Color.alpha(pixel);

            if (alpha > penetrateAlpha){
                return true;
            }

            return false;
        } catch (Throwable e) {
            return true;
        }
    }

    public void loadUrl(String url) {
        loadFinished = false;
        this.url = url;

        if (finishInflate) {
            webView.loadUrl(url);
            this.url = null;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility != VISIBLE || loadFinished)
            super.setVisibility(visibility);
    }
}
