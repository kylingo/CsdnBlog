package com.free.blog.ui.content;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.free.blog.R;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.model.entity.BlogHtml;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.ui.base.activity.BaseSingleActivity;
import com.free.blog.ui.base.vp.single.ISinglePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客详细内容界面
 *
 * @author tangqi
 * @since 2015年7月20日下午9:20:20
 */
@SuppressLint("SetJavaScriptEnabled")
public class BlogContentActivity extends BaseSingleActivity implements
        BlogContentContract.View<BlogHtml, ISinglePresenter>, OnClickListener, OnCheckedChangeListener {
    public static final String EXTRA_BLOG_ITEM = "blog_item";

    private BlogContentPresenter mPresenter;
    private String mTitle;
    private String mUrl;
    private String mBlogId;
    private boolean isFirstCheck;
    private List<String> mHistoryUrlList;

    private WebView mWebView;
    private ToggleButton mCollectBtn;
    private ProgressBar mProgressBar;
    private ImageView mReLoadImageView;

    @Override
    protected int getContentView() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void beforeInitView() {
        BlogItem blogItem = (BlogItem) getIntent().getSerializableExtra(EXTRA_BLOG_ITEM);
        if (blogItem != null) {
            mHistoryUrlList = new ArrayList<>();
            mUrl = blogItem.getLink();
            mTitle = blogItem.getTitle();
            mBlogId = mUrl.substring(mUrl.lastIndexOf("/") + 1);
            new BlogContentPresenter(this, blogItem, mUrl);
        } else {
            finish();
        }

    }

    @Override
    protected void initView() {
        TextView mTitleView = (TextView) findViewById(R.id.tv_title);
        mTitleView.setText(R.string.blog_detail);

        mProgressBar = (ProgressBar) findViewById(R.id.blogContentPro);
        mReLoadImageView = (ImageView) findViewById(R.id.iv_reload);
        ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
        mCollectBtn = (ToggleButton) findViewById(R.id.tb_collect);

        findViewById(R.id.iv_comment).setOnClickListener(this);
        findViewById(R.id.iv_share).setOnClickListener(this);
        findViewById(R.id.iv_more).setOnClickListener(this);

        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        mReLoadImageView.setOnClickListener(this);
        mCollectBtn.setOnCheckedChangeListener(this);

        initWebView();
        mPresenter.loadData();
        mPresenter.queryCollect();
        mPresenter.saveHistory();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);

        // LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        // LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
        // 总结：根据以上两种模式，建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT，无网络时，使用LOAD_CACHE_ELSE_NETWORK。
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void onUpdateCollectUI(boolean isCollect) {
        if (isCollect) {
            isFirstCheck = true;
            mCollectBtn.setChecked(true);
        }
    }

    @Override
    public void onCollectSuccess(boolean isCollect) {
        if (isCollect) {
            ToastUtil.show(this, "收藏成功");
        } else {
            ToastUtil.show(this, "取消收藏");
        }
    }

    @Override
    public void onCollectFailure(boolean isCollect) {
        if (isCollect) {
            ToastUtil.show(this, "收藏失败");
        } else {
            ToastUtil.show(this, "取消收藏失败");
        }
    }

    @Override
    public void onUpdateUI(BlogHtml blogHtml) {
        if (blogHtml != null) {
            mTitle = blogHtml.getTitle();
            loadHtml(blogHtml.getHtml());
        } else {
            loadHtml(null);
        }
    }

    @Override
    public void onUpdateFailure(int errNo) {
        loadHtml(null);
    }

    @Override
    public void setPresenter(ISinglePresenter iSinglePresenter) {
        mPresenter = (BlogContentPresenter) iSinglePresenter;
        mPresenter.subscribe();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isFirstCheck) {
            isFirstCheck = false;
            return;
        }

        mPresenter.collect(isChecked);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.iv_reload:
                reload();
                break;

            case R.id.iv_comment:
                mPresenter.comment(this, mBlogId);
                break;

            case R.id.iv_share:
                mPresenter.share(this, mTitle, mUrl);
                break;

            case R.id.iv_more:
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();

            // handle history
            if (mHistoryUrlList != null && mHistoryUrlList.size() > 0) {
                int lastHistoryIndex = mHistoryUrlList.size() - 1;
                loadData(mHistoryUrlList.get(lastHistoryIndex));
                mHistoryUrlList.remove(lastHistoryIndex);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void reload() {
        mReLoadImageView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData(mUrl);
    }

    private void loadData(String url) {
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.setUrl(url);
        mPresenter.loadData();
    }

    private void loadHtml(String html) {
        if (!TextUtils.isEmpty(html)) {
            mWebView.loadDataWithBaseURL("https://blog.csdn.net", html, "text/html", "utf-8", null);
            mReLoadImageView.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mReLoadImageView.setVisibility(View.VISIBLE);
            ToastUtil.show(this, "网络已断开");
        }
    }

    class MyWebViewClient extends WebViewClient {

        MyWebViewClient() {

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView paramWebView, String paramString) {
            mWebView.getSettings().setBlockNetworkImage(false);
            mProgressBar.setVisibility(View.GONE);
            super.onPageFinished(paramWebView, paramString);
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            if ((paramString.matches("https://blog.csdn.net/(\\w+)/article/details/(\\d+)"))) {
                mHistoryUrlList.add(mUrl);

                mUrl = paramString;
                loadData(paramString);
                return false;
            }
            return true;
        }
    }

}
