package com.free.csdn.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.Channel;

/**
 * 频道列表
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:01:25
 */
public class ChannelListAdapter extends BaseAdapter {

	private Context context;
	private List<Channel> list;

	public ChannelListAdapter(Context context, List<Channel> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<Channel> list) {
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public Channel getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_channel, null);
			holder = new ViewHolder();
			holder.imvChannel = (ImageView) convertView.findViewById(R.id.imvChannel);
			holder.tvChannel = (TextView) convertView.findViewById(R.id.tvChannel);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvChannel.setText(getItem(position).getChannelName());
		holder.imvChannel.setImageResource(getItem(position).getImgResourceId());

		return convertView;
	}

	static class ViewHolder {
		ImageView imvChannel;
		TextView tvChannel;
	}

}
