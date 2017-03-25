package com.free.blog.ui.home.find.rank;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.library.config.Config;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.entity.RankItem;
import com.free.blog.ui.base.activity.BaseRefreshActivity;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.content.BlogContentActivity;
import com.free.blog.ui.home.column.detail.ColumnDetailActivity;
import com.free.blog.ui.list.BlogListActivity;

import java.util.List;

/**
 * @author studiotang on 17/3/25
 */
public class RankActivity extends BaseRefreshActivity<List<RankItem>> implements RankContract.View<List<RankItem>, IRefreshPresenter> {

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.rank);
    }

    @Override
    protected void beforeInitView() {
        new RankPresenter(this);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new RankAdapter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RankItem rankItem = (RankItem) adapter.getItem(position);

        String url = rankItem.getUrl();
        if (!TextUtils.isEmpty(url)) {
            if (!url.replace(Config.HOST_BLOG, "").contains("/")) {
                gotoBlogList(rankItem);
            } else if (url.matches(Config.HOST_BLOG + "(\\w+)/article/details/(\\d+)")) {
                gotoBlogContent(rankItem);
            } else if (url.matches("/column/details/.*")) {
                gotoColumnList(rankItem);
            }
        }
    }

    private void gotoBlogList(RankItem rankItem) {
        Blogger blogger = new Blogger();
        blogger.setUserId(rankItem.getName());
        blogger.setTitle(rankItem.getName());

        Intent intent = new Intent(this, BlogListActivity.class);
        intent.putExtra(BlogListActivity.EXTRA_BLOGGER, blogger);
        startActivity(intent);
    }

    private void gotoBlogContent(RankItem rankItem) {
        BlogItem blogItem = new BlogItem();
        blogItem.setLink(rankItem.getUrl());
        blogItem.setTitle(rankItem.getName());

        Intent intent = new Intent(this, BlogContentActivity.class);
        intent.putExtra(BlogContentActivity.EXTRA_BLOG_ITEM, blogItem);
        startActivity(intent);
    }

    protected void gotoColumnList(RankItem rankItem) {
        BlogColumn blogColumn = new BlogColumn();
        blogColumn.setName(rankItem.getName());
        blogColumn.setUrl(rankItem.getUrl());
        blogColumn.setIcon(rankItem.getIcon());

        Intent intent = new Intent(this, ColumnDetailActivity.class);
        intent.putExtra(ColumnDetailActivity.EXTRA_COLUMN, blogColumn);
        startActivity(intent);
    }
}
