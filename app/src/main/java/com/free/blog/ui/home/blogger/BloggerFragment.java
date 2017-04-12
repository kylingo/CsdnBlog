package com.free.blog.ui.home.blogger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.library.config.KeyConfig;
import com.free.blog.library.config.UrlFactory;
import com.free.blog.library.util.SpfUtils;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.library.view.dialog.BaseDialog;
import com.free.blog.library.view.dialog.BloggerAddDialog;
import com.free.blog.library.view.dialog.BloggerOperationDialog;
import com.free.blog.library.view.dialog.LoadingDialog;
import com.free.blog.model.entity.Blogger;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.fragment.BaseRefreshFragment;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.list.BlogListActivity;

import java.util.List;

/**
 * @author tangqi on 17-3-23.
 */
public class BloggerFragment extends BaseRefreshFragment<List<Blogger>> implements
        BloggerContract.View<List<Blogger>, IRefreshPresenter> {

    private ProgressDialog mProgressDialog;

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void beforeInitView() {
        setHasOptionsMenu(true);
        String type = (String) SpfUtils.get(getActivity(), KeyConfig.BLOG_TYPE, UrlFactory
                .CategoryName.ANDROID);
        new BloggerPresenter(this, type);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new BloggerAdapter();
    }

    @Override
    public BloggerPresenter getPresenter() {
        return (BloggerPresenter) mPresenter;
    }

    @Override
    protected boolean isShowMenu() {
        return true;
    }

    @Override
    protected int getMenuResId() {
        return R.drawable.ic_add;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showMenu(null);
                return true;
        }

        return true;
    }

    @Override
    protected void showMenu(View view) {
        BloggerAddDialog dialog = new BloggerAddDialog(getActivity(), new BaseDialog
                .OnConfirmListener() {

            @Override
            public void onConfirm(String userId) {
                if (TextUtils.isEmpty(userId)) {
                    ToastUtil.show(getActivity(), "博客ID为空");
                    return;
                }
                getPresenter().addBlogger(userId);
            }

        });

        dialog.show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Blogger blogger = (Blogger) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), BlogListActivity.class);
        intent.putExtra(BlogListActivity.EXTRA_BLOGGER, blogger);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        final Blogger blogger = (Blogger) adapter.getItem(position);
        BloggerOperationDialog dialog = new BloggerOperationDialog(getActivity(), blogger);
        dialog.setOnDeleteListener(new BaseDialog.OnDeleteListener() {

            @Override
            public void onDelete(String result) {
                getPresenter().deleteBlogger(blogger);
            }
        });

        dialog.setOnStickListener(new BaseDialog.OnStickListener() {

            @Override
            public void onStick(String result) {
                getPresenter().stickBlogger(blogger);
            }
        });

        dialog.show();
        return true;
    }

    @Override
    public void addBloggerStart() {
        showProgressDialog();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addBloggerSuccess(Blogger blogger) {
        ToastUtil.show(getActivity(), "博客ID添加成功");
        mAdapter.addData(0, blogger);
        mRecyclerView.scrollToPosition(0);
        dismissProgressDialog();
    }

    @Override
    public void addBloggerRepeat() {
        ToastUtil.show(getActivity(), "博客ID重复添加");
    }

    @Override
    public void addBloggerFailure() {
        ToastUtil.show(getActivity(), "博客ID不存在，添加失败");
        dismissProgressDialog();
    }

    @Override
    public void deleteBloggerSuccess() {
        ToastUtil.show(getActivity(), "删除成功");
    }

    @Override
    public void deleteBloggerFailure() {
        ToastUtil.show(getActivity(), "删除失败");
    }

    @Override
    public void stickBloggerSuccess(Blogger blogger) {
        if (blogger.getIsTop() == 0) {
            ToastUtil.show(getActivity(), "取消置顶成功");
        } else {
            ToastUtil.show(getActivity(), "置顶成功");
        }
    }

    @Override
    public void stickBloggerFailure() {
        ToastUtil.show(getActivity(), "操作失败");
    }

    private void showProgressDialog() {
        mProgressDialog = new LoadingDialog(getActivity(), "正在添加博客...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
