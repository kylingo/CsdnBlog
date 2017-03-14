package com.free.blog.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.Channel;
import com.free.blog.domain.util.ImageLoaderUtils;

import java.util.List;


/**
 * 频道列表
 *
 * @author tangqi
 * @since 2015年8月9日下午2:01:25
 */
public class ChannelListAdapter extends BaseAdapter {

    private Context context;
    private List<Channel> mData;
    private String checkType = null;

    public ChannelListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setList(List<Channel> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void addList(List<Channel> list) {
        if (mData != null && list != null) {
            this.mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Channel getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_channel, null);
            holder = new ViewHolder();
            holder.imvChannel = (ImageView) convertView.findViewById(R.id.imvChannel);
            holder.imvChecked = (ImageView) convertView.findViewById(R.id.imvChecked);
            holder.tvChannel = (TextView) convertView.findViewById(R.id.tvChannel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Channel channel = getItem(position);
        holder.tvChannel.setText(channel.getChannelName());
        if (!TextUtils.isEmpty(channel.getImgUrl())) {
            ImageLoaderUtils.displayRoundImage(channel.getImgUrl(), holder.imvChannel);
        } else {
            holder.imvChannel.setImageResource(channel.getImgResourceId());

        }

        if (getItem(position).getChannelName().equals(checkType)) {
            holder.imvChecked.setVisibility(View.VISIBLE);
        } else {
            holder.imvChecked.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView imvChannel, imvChecked;
        TextView tvChannel;
    }

}
