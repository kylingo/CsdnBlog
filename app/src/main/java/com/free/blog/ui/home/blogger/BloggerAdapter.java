package com.free.blog.ui.home.blogger;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.library.util.ImageLoaderUtils;
import com.free.blog.model.entity.Blogger;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

/**
 * @author tangqi on 17-3-23.
 */
public class BloggerAdapter extends BaseViewAdapter<Blogger> {
    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(getItemView(R.layout.listitem_blogger, null));
    }

    @Override
    protected void convert(BaseViewHolder helper, Blogger item) {
        if (!TextUtils.isEmpty(item.getImgUrl())) {
            ImageView imageView = helper.getView(R.id.imv_blogger);
            ImageLoaderUtils.displayRoundImage(item.getImgUrl(), imageView);
        } else {
            helper.setImageResource(R.id.imv_blogger, R.drawable.ic_default);
        }

//        if (item.getIsTop() == 1) {
//            helper.setText(R.id.tv_blog_title, String.format("%s\b[顶]", item.getTitle()));
//            helper.setTextColor(R.id.tv_blog_title, R.color.blue_text);
//        } else {
            helper.setText(R.id.tv_blog_title, item.getTitle());
//            helper.setTextColor(R.id.tv_blog_title, R.color.black_text);
//        }

        if (!TextUtils.isEmpty(item.getDescription())) {
            helper.setText(R.id.tv_blog_desc, item.getDescription());
            helper.setVisible(R.id.tv_blog_desc, true);
        } else {
            helper.setVisible(R.id.tv_blog_desc, false);
        }
    }
}
