package com.free.blog.ui.home.find.rank;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.free.blog.R;
import com.free.blog.library.util.ImageLoaderUtils;
import com.free.blog.model.entity.RankItem;
import com.free.blog.ui.base.adapter.BaseViewAdapter;

/**
 * @author studiotang on 17/3/25
 */
class RankAdapter extends BaseViewAdapter<RankItem> {

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(getItemView(R.layout.listitem_column, null));
    }

    @Override
    protected void convert(BaseViewHolder helper, RankItem item) {
        helper.setText(R.id.tvChannel, item.getName());
        ImageView imageView = helper.getView(R.id.imvChannel);
        ImageLoaderUtils.displayRoundImage(item.getIcon(), imageView);
    }
}
