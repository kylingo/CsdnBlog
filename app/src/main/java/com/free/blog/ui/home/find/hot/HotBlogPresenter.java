package com.free.blog.ui.home.find.hot;

import com.free.blog.library.config.HotBlogManager;
import com.free.blog.library.config.UrlManager;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.home.find.last.NewBlogContract;
import com.free.blog.ui.home.find.last.NewBlogPresenter;

import java.util.List;

/**
 * @author studiotang on 17/3/26
 */
public class HotBlogPresenter extends NewBlogPresenter {

    HotBlogPresenter(NewBlogContract.View<List<BlogItem>, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
    }

    @Override
    protected UrlManager getUrlManager() {
        return new HotBlogManager();
    }
}
