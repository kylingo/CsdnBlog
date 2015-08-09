package com.free.csdn.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.Blogger;
import com.free.csdn.util.ImageLoaderUtils;
import com.free.csdn.view.CircleImageView;

/**
 * 博主列表
 */
public class BloggerListAdapter extends BaseAdapter {

	private Context context;
	private List<Blogger> list;

	public BloggerListAdapter(Context context, List<Blogger> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(List<Blogger> list) {
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public Blogger getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_blogger, null);
			holder = new ViewHolder();
			holder.imv_blogger = (CircleImageView) convertView.findViewById(R.id.imv_blogger);
			holder.tv_blog_title = (TextView) convertView.findViewById(R.id.tv_blog_title);
			holder.tv_blog_desc = (TextView) convertView.findViewById(R.id.tv_blog_desc);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ImageLoaderUtils.displayImg(getItem(position).getImgUrl(), holder.imv_blogger);
		if (getItem(position).getIsTop() == 1 || getItem(position).getIsNew() == 1) {
			holder.tv_blog_title.setTextColor(context.getResources().getColor(R.color.blue_text));
		} else {
			holder.tv_blog_title.setTextColor(context.getResources().getColor(R.color.black_text));
		}
		holder.tv_blog_title.setText(getItem(position).getTitle());
		holder.tv_blog_desc.setText(getItem(position).getDescription());

		return convertView;
	}

	static class ViewHolder {
		CircleImageView imv_blogger;
		TextView tv_blog_title, tv_blog_desc;
	}

}
