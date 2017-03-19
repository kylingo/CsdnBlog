package com.free.blog.ui.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.library.config.Config;
import com.free.blog.ui.base.BaseViewAdapter;


/**
 * @author studiotang on 17/3/19
 */
public class BlogListRxAdapter extends BaseViewAdapter<BlogItem, BlogListRxAdapter.BlogListViewHolder> {

    @Override
    protected BlogListViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new BlogListViewHolder(getItemView(R.layout.listitem_blog, parent));
    }

    @Override
    protected void convert(BlogListViewHolder helper, BlogItem item) {
        helper.setAssociatedObject(item);
    }

    @Override
    public void onBindViewHolder(BlogListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position <= 0) {
            return;
        }

        BlogItem item = getItem(position); // 获取当前数据
        if (null != item) {
            // 显示标题内容
            holder.title.setText(item.getTitle());
            holder.content.setText(String.format("\b\b\b\b\b\b\b%s", item.getContent()));
            holder.date.setText(item.getDate());
            holder.img.setVisibility(View.VISIBLE);

            String icoType = item.getIcoType();
            if (Config.BLOG_ICO_TYPE.BLOG_TYPE_ORIGINAL.equals(icoType)) {
                holder.img.setImageResource(R.drawable.ic_original);
            } else if (Config.BLOG_ICO_TYPE.BLOG_TYPE_REPOST.equals(icoType)) {
                holder.img.setImageResource(R.drawable.ic_repost);
            } else if (Config.BLOG_ICO_TYPE.BLOG_TYPE_TRANSLATED.equals(icoType)) {
                holder.img.setImageResource(R.drawable.ic_translate);
            } else {
                holder.img.setImageResource(R.drawable.ic_original);
            }
        }
    }

    static class BlogListViewHolder extends BaseViewHolder {
        // TextView id;
        TextView date;
        TextView title;
        ImageView img;
        TextView content;

        BlogListViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            content = (TextView) view.findViewById(R.id.content);
            img = (ImageView) view.findViewById(R.id.blogImg);
        }
    }
}
