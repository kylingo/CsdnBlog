package com.free.blog.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.entity.Channel;
import com.free.blog.model.local.dao.BloggerDao;
import com.free.blog.model.local.dao.DaoFactory;
import com.free.blog.library.config.KeyConfig;
import com.free.blog.library.util.DateUtils;
import com.free.blog.library.util.SpfUtils;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.library.view.dialog.BaseDialog;
import com.free.blog.library.view.dialog.BloggerOperationDialog;
import com.free.blog.library.view.dialog.SelectionDialog;
import com.free.blog.ui.base.activity.BaseActivity;
import com.free.blog.ui.list.BlogListActivity;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 频道详情
 *
 * @author Frank
 * @since 2015年9月18日下午5:27:58
 */
@SuppressWarnings("unused")
public class ChannelDetailActivity extends BaseActivity
        implements OnClickListener, OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener {

    public static final String EXTRA_CHANNEL = "channel";
    private XListView mListView;

    private BloggerListAdapter mAdapter;
    private BloggerDao mBloggerDao;
    private Channel mChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);

        initData();
        initView();
        queryDb(true);
    }

    private void initData() {
        mChannel = (Channel) getIntent().getSerializableExtra(EXTRA_CHANNEL);
        mBloggerDao = DaoFactory.create().getBloggerDao(this, mChannel.getChannelName());
    }

    private void initView() {
        if (mChannel != null) {
            TextView mTitleView = (TextView) findViewById(R.id.tv_title);
            mTitleView.setText(mChannel.getChannelName());
        }
        ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
        ImageView mMenuBtn = (ImageView) findViewById(R.id.btn_menu);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        mMenuBtn.setVisibility(View.VISIBLE);
        mMenuBtn.setImageResource(R.drawable.ic_yes);
        mMenuBtn.setOnClickListener(this);

        mListView = (XListView) findViewById(R.id.listView);
        List<Blogger> mBloggerList = new ArrayList<Blogger>();
        mAdapter = new BloggerListAdapter(this, mBloggerList);
        mListView.setPullRefreshEnable(this);
        mListView.NotRefreshAtBegin();
        mListView.setRefreshTime(DateUtils.getDate());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_menu:
                showMenu();
                break;

            default:
                break;
        }
    }

    /**
     * 设置为默认频道
     */
    private void showMenu() {
        SelectionDialog dialog = new SelectionDialog(this, "设置【" + mChannel.getChannelName() + "】为默认频道？");
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {

            @Override
            public void onConfirm(String result) {
                SpfUtils.put(ChannelDetailActivity.this, KeyConfig.BLOG_TYPE, mChannel
                        .getChannelName());
                ToastUtil.show(ChannelDetailActivity.this, "设置成功");
                finish();
            }
        });

        dialog.show();
    }

    public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
        Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, BlogListActivity.class);
        intent.putExtra(BlogListActivity.EXTRA_BLOGGER, blogger);
        startActivity(intent);
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
        BloggerOperationDialog dialog = new BloggerOperationDialog(this, blogger);
        dialog.setOnDeleteListener(new BaseDialog.OnDeleteListener() {

            @Override
            public void onDelete(String result) {
                deleteBlogger(blogger);
            }
        });

        dialog.setOnStickListener(new BaseDialog.OnStickListener() {

            @Override
            public void onStick(String result) {
                stickBlogger(blogger);
            }
        });
        dialog.show();
        return true;
    }

    /**
     * 查询数据库
     */
    private void queryDb(final boolean isRequest) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final List<Blogger> list = mBloggerDao.queryAll();
                if (list != null && list.size() != 0) {
                    // 数据库有数据，则更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setList(list);
                        }
                    });
                } else {
                    // 否则请求数据
                    if (isRequest) {
                        requestData();
                    }
                }

            }
        }).start();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        if (mChannel != null) {
            String url = mChannel.getUrl();
//            HttpAsyncTask asyncTask = new HttpAsyncTask(this);
//            asyncTask.execute(url);
//            asyncTask.setOnResponseListener(new OnResponseListener() {
//
//                @Override
//                public void onResponse(String resultString) {
//                    if (resultString != null) {
//                        List<Blogger> bloggerList = JsoupUtils.getBloggerList(mChannel
//                                .getChannelName(), resultString);
//                        if (bloggerList != null) {
//                            mAdapter.setList(bloggerList);
//                            saveDB(bloggerList);
//                        }
//                    } else {
//                        ToastUtil.show(ChannelDetailActivity.this, "数据请求失败");
//                    }
//
//                    updateListView();
//                }
//            });
        }
    }

    /**
     * 更新ListView的刷新、加载状态
     */
    protected void updateListView() {
        mListView.stopRefresh(DateUtils.getDate());
    }

    /**
     * 保存数据库
     */
    private void saveDB(final List<Blogger> list) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mBloggerDao.deleteAll();
                mBloggerDao.insert(list);
            }
        }).start();

    }

    /**
     * 置顶博主
     */
    private void stickBlogger(Blogger blogger) {
        if (blogger.getIsTop() == 1) {
            blogger.setIsTop(0);
            ToastUtil.show(this, "取消置顶成功");
        } else {
            blogger.setIsTop(1);
            ToastUtil.show(this, "置顶成功");
        }

        blogger.setUpdateTime(System.currentTimeMillis());
        mBloggerDao.insert(blogger);
        queryDb(false);
    }

    /**
     * 删除博主
     */
    private void deleteBlogger(Blogger blogger) {
        if (blogger != null) {
            ToastUtil.show(this, "删除成功");
            mBloggerDao.delete(blogger);
            queryDb(false);
        } else {
            ToastUtil.show(this, "删除失败");
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        // 处理刷新的问题
        SelectionDialog dialog = new SelectionDialog(this, "刷新数据会导致以前的数据丢失，确定要执行吗？");
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {

            @Override
            public void onConfirm(String result) {
                requestData();
            }
        });

        dialog.setOnCancelListener(new BaseDialog.OnCancelListener() {

            @Override
            public void onCancel(String result) {
                mListView.stopRefresh(DateUtils.getDate());
            }
        });

        dialog.show();
    }
}
