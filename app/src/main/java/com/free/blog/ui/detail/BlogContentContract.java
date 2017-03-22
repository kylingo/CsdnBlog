package com.free.blog.ui.detail;

import android.app.Activity;

import com.free.blog.ui.base.mvp.single.ISinglePresenter;
import com.free.blog.ui.base.mvp.single.ISingleView;

/**
 * @author studiotang on 17/3/21
 */
public interface BlogContentContract {
    interface View<T, P> extends ISingleView<T, P> {
        void onUpdateCollectUI(boolean isCollect);
        void onCollectSuccess(boolean isCollect);
        void onCollectFailure(boolean isCollect);
    }

    interface Presenter extends ISinglePresenter {

        void setUrl(String url);

        void comment(Activity activity, String blogId);

        void share(Activity activity, String title, String url);

        void queryCollect();

        void collect(boolean isCollect);
    }
}
