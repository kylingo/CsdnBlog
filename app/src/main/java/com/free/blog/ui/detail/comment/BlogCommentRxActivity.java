package com.free.blog.ui.detail.comment;

import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.Comment;
import com.free.blog.ui.base.activity.BaseRefreshActivity;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

import java.util.List;

/**
 * @author tangqi on 17-3-22.
 */
public class BlogCommentRxActivity extends BaseRefreshActivity<List<Comment>> {

    public static final String EXTRA_BLOG_ID = "blog_id";

    private TextView mTvComment;

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected void beforeInitView() {
        String blogId = getIntent().getExtras().getString(EXTRA_BLOG_ID);
        new BlobCommentPresenter(this, blogId);
    }

    @Override
    protected void initView() {
        super.initView();
        mTvComment = (TextView) findViewById(R.id.comment);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new BlogCommentRxAdapter();
    }

    @Override
    public void onRefreshUI(List<Comment> data) {
        super.onRefreshUI(data);
        updateCommentUI();
    }

    @Override
    public void onMoreUI(List<Comment> data) {
        super.onMoreUI(data);
        updateCommentUI();

    }

    protected void updateCommentUI() {
        mTvComment.setText(String.format("%s条", String.valueOf(mAdapter.getItemCount())));
    }
}
