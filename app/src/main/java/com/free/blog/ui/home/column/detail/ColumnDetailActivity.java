package com.free.blog.ui.home.column.detail;

import com.free.blog.model.entity.Channel;
import com.free.blog.ui.base.activity.BaseBlogListActivity;

/**
 * @author tangqi on 17-3-13.
 */
public class ColumnDetailActivity extends BaseBlogListActivity {
    public static final String EXTRA_COLUMN = "column";

    private Channel mChannel;

    @Override
    protected String getActionBarTitle() {
        return mChannel.getChannelName();
    }

    @Override
    protected void beforeInitView() {
        mChannel = (Channel) getIntent().getSerializableExtra(EXTRA_COLUMN);
        new ColumnDetailPresenter(this, mChannel);
    }
}
