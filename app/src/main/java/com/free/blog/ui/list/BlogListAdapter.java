package com.free.blog.ui.list;

import android.text.TextUtils;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.library.config.Config;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

/**
 * @author studiotang on 17/3/19
 */
public class BlogListAdapter extends BaseViewAdapter<BlogItem> {

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(getItemView(R.layout.listitem_blog, parent));
    }

    @Override
    protected void convert(BaseViewHolder holder, BlogItem item) {
        holder.setText(R.id.title, item.getTitle());

        if (TextUtils.isEmpty(item.getContent())) {
            item.setContent("暂无描述");
        }
        holder.setText(R.id.content, String.format("\b\b\b\b\b\b\b%s", item.getContent()));

        if (!TextUtils.isEmpty(item.getDate())) {
            holder.setText(R.id.date, item.getDate());
        }

        String icoType = item.getIcoType();
        int imageResource = R.drawable.ic_original;
        if (Config.BLOG_TYPE.BLOG_TYPE_ORIGINAL.equals(icoType)) {
            imageResource = R.drawable.ic_original;
        } else if (Config.BLOG_TYPE.BLOG_TYPE_REPOST.equals(icoType)) {
            imageResource = R.drawable.ic_repost;
        } else if (Config.BLOG_TYPE.BLOG_TYPE_TRANSLATED.equals(icoType)) {
            imageResource = R.drawable.ic_translate;
        }
        holder.setImageResource(R.id.blogImg, imageResource);
    }
}

