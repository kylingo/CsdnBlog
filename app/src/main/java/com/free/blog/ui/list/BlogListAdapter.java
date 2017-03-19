package com.free.blog.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.library.config.Config.BLOG_ICO_TYPE;

import java.util.ArrayList;
import java.util.List;

/**
 *  博客列表适配器
 * 
 * @author tangqi
 * @since 2015年8月9日下午2:01:25
 */
public class BlogListAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater; // 布局加载器
	private List<BlogItem> list; // 博客列表

	public BlogListAdapter(Context context) {
		super();
		layoutInflater = LayoutInflater.from(context);
		list = new ArrayList<BlogItem>();

	}

	public void setList(List<BlogItem> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void addList(List<BlogItem> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<BlogItem> getList() {
		return list;
	}

	@SuppressWarnings("unused")
	public void removeItem(int position) {
		if (list.size() > 0) {
			list.remove(position);
		}
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			// 装载布局文件blog_list_item.xml
			convertView = layoutInflater.inflate(R.layout.listitem_blog, null);
			holder = new ViewHolder();
			// holder.id = (TextView) convertView.findViewById(R.id.id);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.img = (ImageView) convertView.findViewById(R.id.blogImg);
			convertView.setTag(holder); // 表示给View添加一个格外的数据，
		} else {
			holder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
		}

		BlogItem item = list.get(position); // 获取当前数据
		if (null != item) {
			// 显示标题内容
			holder.title.setText(item.getTitle());
			holder.content.setText(String.format("\b\b\b\b\b\b\b%s", item.getContent()));
			holder.date.setText(item.getDate());
			holder.img.setVisibility(View.VISIBLE);

			String icoType = item.getIcoType();
			if (BLOG_ICO_TYPE.BLOG_TYPE_ORIGINAL.equals(icoType)) {
				holder.img.setImageResource(R.drawable.ic_original);
			} else if (BLOG_ICO_TYPE.BLOG_TYPE_REPOST.equals(icoType)) {
				holder.img.setImageResource(R.drawable.ic_repost);
			} else if (BLOG_ICO_TYPE.BLOG_TYPE_TRANSLATED.equals(icoType)) {
				holder.img.setImageResource(R.drawable.ic_translate);
			} else {
				holder.img.setImageResource(R.drawable.ic_original);
			}
		}

		return convertView;
	}

	private class ViewHolder {
		// TextView id;
		TextView date;
		TextView title;
		ImageView img;
		TextView content;
	}

}
