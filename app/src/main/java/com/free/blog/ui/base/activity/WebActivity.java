package com.free.blog.ui.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.blog.R;


/**
 * @author tangqi on 17-2-24.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {
    public static final String LINK = "link";
    public static final String NAME = "name";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mTitle;
    private String mLink;

    public static void showWeb(Context context, String link, String name) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(LINK, link);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);

        beforeInitView();
        initView();

        loadData();
    }

    private void beforeInitView() {
        mLink = getIntent().getStringExtra(LINK);
        mTitle = getIntent().getStringExtra(NAME);
    }

    private void initView() {
        initActionBar();

        mProgressBar = (ProgressBar) findViewById(R.id.pb_webview);
        mWebView = (WebView) findViewById(R.id.base_webview);
        mWebView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    private void initActionBar() {
        TextView mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
        ImageView mMenuBtn = (ImageView) findViewById(R.id.btn_menu);
        mBackBtn.setOnClickListener(this);
        mMenuBtn.setOnClickListener(this);
        mMenuBtn.setVisibility(View.GONE);
        mMenuBtn.setImageResource(R.drawable.ic_menu);
    }

    private void loadData() {
        String url = mWebView.getUrl();
        if (TextUtils.isEmpty(url)) {
//            if (isOledMode() && needRebuild(mLink)) {
//                mLink += HelpAndFeedbackFragment.THEME_OLED;
//            }
            mWebView.loadUrl(mLink);
        } else {
//            if (isOledMode() && needRebuild(url)) {
//                url += HelpAndFeedbackFragment.THEME_OLED;
//                mWebView.loadUrl(url);
//                return;
//            }
            mWebView.reload();
        }
    }

    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (WebUtil.isEmailUrl(url)) {
//                WebUtil.toEmailActivity(WebActivity.this, url);
//            } else {
            view.loadUrl(url);
//            }
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }

        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            view.clearView();
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.GONE);
        }
    };

    private final WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            int maxProgress = mProgressBar.getMax();
            int loadProgress = newProgress * maxProgress / 100;
            int nowProgress = mProgressBar.getProgress();
            if (nowProgress < loadProgress) {
                mProgressBar.setProgress(loadProgress);
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_menu:
                showMenu(view);
                break;

            default:
                break;
        }
    }

    private void showMenu(View view) {

    }
}
