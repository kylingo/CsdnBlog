package com.free.blog.ui.detail.comment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.Comment;
import com.free.blog.data.entity.CommentComparator;
import com.free.blog.data.local.dao.BlogCommentDao;
import com.free.blog.data.local.dao.DaoFactory;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.config.Config;
import com.free.blog.library.util.DateUtils;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.ui.base.BaseActivity;

import java.util.Collections;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import rx.Subscriber;

/**
 * 博客评论列表
 *
 * @author tangqi
 * @since 2015年7月20日下午8:20:20
 */
public class BlogCommentActivity extends BaseActivity implements IXListViewRefreshListener, IXListViewLoadMore {

    private XListView mListView;
    private BlogCommentAdapter mAdapter;
    private ImageView mReLoadImageView;
    private TextView mTvComment;
    private ProgressBar mPbLoading;

    private String mFileName;
    private int mPage = 1;
    private BlogCommentDao mBlogCommentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initData();
        initComponent();
    }

    private void initData() {
        mFileName = getIntent().getExtras().getString("filename"); // 获得文件名
        mAdapter = new BlogCommentAdapter(this);

        mBlogCommentDao = DaoFactory.getInstance().getBlogCommentDao(this, mFileName);
    }

    private void initComponent() {
        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
        mReLoadImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("click");
                mReLoadImageView.setVisibility(View.INVISIBLE);
                onRefresh();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvComment = (TextView) findViewById(R.id.comment);

        mListView = (XListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.NotRefreshAtBegin();
        mListView.setPullRefreshEnable(this);

        // 先预加载数据，再请求最新数据
        mHandler.sendEmptyMessage(Config.MSG_PRELOAD_DATA);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        requestData(mPage);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        requestData(mPage);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_no, R.anim.push_right_out);
    }

    private void requestData(int page) {
//		if (mAsyncTask != null) {
//			mAsyncTask.cancel(true);
//		}
//
//		mAsyncTask = new HttpAsyncTask(this);
//		String url = UrlUtils.getCommentListURL(mFileName, String.valueOf(page));
//		mAsyncTask.execute(url);
//		mAsyncTask.setOnResponseListener(onResponseListener);
        NetEngine.getInstance().getBlogComment(mFileName, page)
                .compose(NetEngine.<String>getErrAndIOSchedulerTransformer())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse(null);
                    }

                    @Override
                    public void onNext(String s) {
                        onResponse(s);
                    }
                });
    }

    public void onResponse(String resultString) {
        // 解析html页面获取列表
        if (resultString != null) {
            List<Comment> list = JsoupUtils.getBlogCommentList(resultString, mPage, mPageSize);
            CommentComparator comparator = new CommentComparator();
            Collections.sort(list, comparator);
            if (mPage == 1) {
                mAdapter.setData(list);
            } else {
                mAdapter.addList(list);
            }
            mAdapter.notifyDataSetChanged();
            mListView.setPullLoadEnable(BlogCommentActivity.this);// 设置可上拉加载
            mTvComment.setText(String.format("%s条", String.valueOf(mAdapter.getCount())));
            saveDB(list);

        } else {
            ToastUtil.show(BlogCommentActivity.this, "网络已断开");
            mListView.disablePullLoad();
        }

        mPbLoading.setVisibility(View.GONE);
        mReLoadImageView.setVisibility(View.GONE);
        mListView.stopRefresh(DateUtils.getDate());
        mListView.stopLoadMore();
    }

    /**
     * 保存数据库
     */
    private void saveDB(final List<Comment> list) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mBlogCommentDao.insert(list);
            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.MSG_PRELOAD_DATA:
                    mListView.setRefreshTime(DateUtils.getDate());
                    List<Comment> list = mBlogCommentDao.query(mPage);

                    if (list != null) {
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                        mListView.setPullLoadEnable(BlogCommentActivity.this);
                        mListView.setRefreshTime(DateUtils.getDate());
                        mTvComment.setText(String.format("%d条", mAdapter.getCount()));
                    } else {
                        // 不请求最新数据，让用户自己刷新或者加载
                        mPbLoading.setVisibility(View.VISIBLE);
                        requestData(mPage);
                        mListView.disablePullLoad();
                    }
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
