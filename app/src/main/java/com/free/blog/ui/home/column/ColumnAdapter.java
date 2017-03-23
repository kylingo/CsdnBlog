package com.free.blog.ui.home.column;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.library.util.ImageLoaderUtils;
import com.free.blog.model.entity.Channel;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

/**
 * @author studiotang on 17/3/23
 */
public class ColumnAdapter extends BaseViewAdapter<Channel> {

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(getItemView(R.layout.listitem_channel, null));
    }

    @Override
    protected void convert(BaseViewHolder helper, Channel item) {
        helper.setText(R.id.tvChannel, item.getChannelName());
        if (!TextUtils.isEmpty(item.getImgUrl())) {
            ImageView imageView = helper.getView(R.id.imvChannel);
            ImageLoaderUtils.displayRoundImage(item.getImgUrl(), imageView);
        } else {
            helper.setImageResource(R.id.imvChannel, item.getImgResourceId());
        }
    }
}
