package com.free.blog.ui.detail.comment;

import com.free.blog.R;
import com.free.blog.data.entity.Comment;
import com.free.blog.ui.base.activity.BaseRefreshActivity;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

import java.util.List;

/**
 * @author tangqi on 17-3-22.
 */
public class BlogCommentActivity extends BaseRefreshActivity<List<Comment>> {

    public static final String EXTRA_BLOG_ID = "blog_id";

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.comment_list);
    }

    @Override
    protected void beforeInitView() {
        String blogId = getIntent().getExtras().getString(EXTRA_BLOG_ID);
        new BlobCommentPresenter(this, blogId);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new BlogCommentAdapter();
    }
}
