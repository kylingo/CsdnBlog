package com.free.csdn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.Blog;
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
	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem_blogger, null);
			holder = new ViewHolder();
			holder.imv_blogger = (CircleImageView) convertView
					.findViewById(R.id.imv_blogger);
			holder.tv_blog_title = (TextView) convertView
					.findViewById(R.id.tv_blog_title);
			holder.tv_blog_desc = (TextView) convertView
					.findViewById(R.id.tv_blog_desc);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ImageLoaderUtils.displayImg(list.get(position).getImgUrl(),
				holder.imv_blogger);
		holder.tv_blog_title.setText(list.get(position).getTitle());
		holder.tv_blog_desc.setText(list.get(position).getDescription());

		return convertView;
	}

	private class ViewHolder {
		CircleImageView imv_blogger;
		TextView tv_blog_title, tv_blog_desc;
	}

}
