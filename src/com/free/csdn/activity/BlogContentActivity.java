package com.free.csdn.activity;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.free.csdn.R;
import com.free.csdn.bean.BlogHtml;
import com.free.csdn.db.BlogContentDb;
import com.free.csdn.db.impl.BlogContentDbImpl;
import com.free.csdn.network.HttpAsyncTask;
import com.free.csdn.network.HttpAsyncTask.OnCompleteListener;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;

/**
 * 博客详细内容界面
 * 
 * @author wwj_748
 * @date 2014/8/10
 */
@SuppressLint("SetJavaScriptEnabled")
public class BlogContentActivity extends BaseActivity implements
		OnCompleteListener {
	private WebView webView = null;
	private ProgressBar progressBar; // 进度条
	private ImageView reLoadImageView; // 重新加载的图片
	private ImageView backBtn; // 回退按钮
	private ImageView commentBtn; // 评论按钮

	public static String url;
	public String urlMD5 = "url-md5";
	private String filename;
	private static final int MSG_RELOAD_DATA = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);

		init();
		initComponent();

		// 预加载数据
		mHandler.sendEmptyMessage(MSG_RELOAD_DATA);
	}

	// 初始化
	private void init() {
		url = getIntent().getExtras().getString("blogLink");
		filename = url.substring(url.lastIndexOf("/") + 1);
		System.out.println("filename--->" + filename);

	}

	// 初始化组件
	private void initComponent() {
		progressBar = (ProgressBar) findViewById(R.id.blogContentPro);
		reLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		reLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				reLoadImageView.setVisibility(View.INVISIBLE);
				progressBar.setVisibility(View.VISIBLE);
				requestData(url);
			}
		});

		backBtn = (ImageView) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		commentBtn = (ImageView) findViewById(R.id.comment);
		commentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent();
				i.setClass(BlogContentActivity.this, BlogCommentActivity.class);
				i.putExtra("filename", filename);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
			}
		});

		initWebView();
	}

	private void initWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);

		// LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
		// LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
		// 总结：根据以上两种模式，建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT，无网络时，使用LOAD_CACHE_ELSE_NETWORK。
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	}

	/**
	 * 处理WebView返回
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
		// webView.goBack();// WEBVIEW返回前一个页面
		// return true;
		// }
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 加载数据
	 */
	private void requestData(String url) {
		// TODO Auto-generated method stub
		progressBar.setVisibility(View.VISIBLE);
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(this);
		httpAsyncTask.execute(url);
		httpAsyncTask.setOnCompleteListener(this);
	}

	/**
	 * 数据请求完成
	 */
	@Override
	public void onComplete(String resultString) {
		// TODO Auto-generated method stub
		String html = adjustPicSize(JsoupUtil.getContent(resultString));
		loadHtml(html);
		saveDb(html);
	}

	/**
	 * 加载页面
	 * 
	 * @param resultString
	 */
	private void loadHtml(String html) {
		if (!TextUtils.isEmpty(html)) {
			webView.loadDataWithBaseURL("http://blog.csdn.net", html,
					"text/html", "utf-8", null);
			reLoadImageView.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.GONE);
			reLoadImageView.setVisibility(View.VISIBLE);
			ToastUtil.showToast(this, "暂无最新数据");
		}
	}

	/**
	 * 保存数据库
	 * 
	 * @param html
	 */
	private void saveDb(String html) {
		if (TextUtils.isEmpty(html)) {
			return;
		}
		BlogHtml blogHtml = new BlogHtml();
		blogHtml.setUrl(url);
		blogHtml.setHtml(html);
		blogHtml.setReserve("");

		BlogContentDb blogContentDb = new BlogContentDbImpl(this, url);
		blogContentDb.saveBlogContent(blogHtml);
	}

	/**
	 * 适应 页面
	 * 
	 * @param paramString
	 * @return
	 */
	private String adjustPicSize(String paramString) {
		if (TextUtils.isEmpty(paramString)) {
			return null;
		}
		Element localElement = Jsoup.parse(paramString)
				.getElementsByClass("details").get(0);
		Iterator<?> localIterator = localElement.getElementsByTag("img")
				.iterator();
		while (true) {
			if (!localIterator.hasNext())
				return "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script><script type=\"text/javascript\" src=\"file:///android_asset/shBrushCpp.js\"></script><script type=\"text/javascript\" src=\"file:///android_asset/shBrushXml.js\"></script><script type=\"text/javascript\" src=\"file:///android_asset/shBrushJScript.js\"></script><script type=\"text/javascript\" src=\"file:///android_asset/shBrushJava.js\"></script><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\"><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\"><script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
						+ localElement.toString();
			((Element) localIterator.next()).attr("width", "100%");
		}
	}

	private void getData(String url) {
		BlogContentDb blogContentDb = new BlogContentDbImpl(this, url);
		BlogHtml blogHtml = blogContentDb.getBlogContent(url);
		if (blogHtml != null) {
			loadHtml(blogHtml.getHtml());
		} else {
			requestData(url);
		}
	}

	// 预加载数据
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_RELOAD_DATA:
				getData(url);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 自定义
	 * 
	 * @author Administrator
	 *
	 */
	class MyWebViewClient extends WebViewClient {

		MyWebViewClient() {

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}

		public void onPageFinished(WebView paramWebView, String paramString) {
			webView.getSettings().setBlockNetworkImage(false);
			progressBar.setVisibility(View.GONE);
			super.onPageFinished(paramWebView, paramString);
		}

		public void onReceivedError(WebView paramWebView, int paramInt,
				String paramString1, String paramString2) {
			super.onReceivedError(paramWebView, paramInt, paramString1,
					paramString2);
		}

		public boolean shouldOverrideUrlLoading(WebView paramWebView,
				String paramString) {
			// int i = 1;
			// String str = "brian512";
			// Log.i("CSDNBlog_BlogContentActivity", "url=" + paramString);
			// if
			// (paramString.matches("http://blog.csdn.net/(\\w+)/article/details/(\\d+)"))
			// BlogContentActivity.this.mQueue.add(new
			// StringRequest(paramString, BlogContentActivity.this, null));
			// while (true)
			// {
			// label61: return i;
			// if (paramString.matches("http://blog.csdn.net/(\\w+)[/]{0,1}"));
			// for (Pattern localPattern =
			// Pattern.compile("http://blog.csdn.net/(\\w+)[/]{0,1}"); ;
			// localPattern =
			// Pattern.compile("http://blog.csdn.net/(\\w+)/article/\\w+/(\\d+)"))
			// {
			// Matcher localMatcher = localPattern.matcher(paramString);
			// if (localMatcher.find())
			// str = localMatcher.group(i);
			// Intent localIntent = new Intent();
			// localIntent.setClass(BlogContentActivity.this,
			// MainTabActivity.class);
			// localIntent.putExtra(BlogContentActivity.this.getString(2131296299),
			// str);
			// BlogContentActivity.this.startActivity(localIntent);
			// BlogContentActivity.this.finish();
			// break label61:
			// if
			// ((!paramString.matches("http://blog.csdn.net/(\\w+)/article/category/(\\d+)"))
			// &&
			// (!paramString.matches("http://blog.csdn.net/(\\w+)/article/list/(\\d+)")))
			// break;
			// }
			// i = 0;
			// }
			//
			if ((paramString
					.matches("http://blog.csdn.net/(\\w+)/article/details/(\\d+)"))) {
				url = paramString;
				getData(paramString);
				return false;
			}
			return true;
		}
	}

}
