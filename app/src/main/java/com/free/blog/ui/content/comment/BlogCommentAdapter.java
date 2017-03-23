package com.free.blog.ui.content.comment;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.data.entity.Comment;
import com.free.blog.library.config.Config;
import com.free.blog.library.util.ImageLoaderUtils;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

/**
 * @author tangqi on 17-3-22.
 */
class BlogCommentAdapter extends BaseViewAdapter<Comment> {

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Config.COMMENT_TYPE.PARENT) {
            return new BaseViewHolder(getItemView(R.layout.listitem_comment, null));
        } else if (viewType == Config.COMMENT_TYPE.CHILD) {
            return new BaseViewHolder(getItemView(R.layout.listitem_comment_child, null));
        }

        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected int getDefItemViewType(int position) {
        Comment comment = getItem(position);
        return comment.getType();
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        switch (helper.getItemViewType()) {
            case Config.COMMENT_TYPE.PARENT:
                updateParentItem(helper, item);
                break;

            case Config.COMMENT_TYPE.CHILD:
                updateChildItem(helper, item);
                break;
        }
    }

    private void updateParentItem(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.name, item.getUsername());
        helper.setText(R.id.content, Html.fromHtml(item.getContent()));
        helper.setText(R.id.date, item.getPostTime());
        ImageLoaderUtils.displayRoundImage(item.getUserface(), (ImageView) helper.getView(R.id.userface));
    }

    private void updateChildItem(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.name, item.getUsername());
        String replyText = item.getContent().replace("[reply]", "【");
        replyText = replyText.replace("[/reply]", "】");
        helper.setText(R.id.content, Html.fromHtml(replyText));
        helper.setText(R.id.date, item.getPostTime());
    }
}
