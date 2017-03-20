package com.free.blog.ui.list;

import com.free.blog.data.entity.BlogCategory;
import com.free.blog.ui.base.mvp.IBaseRefreshPresenter;
import com.free.blog.ui.base.mvp.IBaseRefreshView;

import java.util.List;

/**
 * @author tangqi on 17-3-20.
 */
public class BlogListRxContract {

    public interface View<T, P> extends IBaseRefreshView<T, P> {

    }

    public interface Presenter extends IBaseRefreshPresenter {

        void setCategoryName(String category);

        void setCategoryLink(String categoryLink);

        void setCategoryList(List<BlogCategory> categoryList);
    }
}
