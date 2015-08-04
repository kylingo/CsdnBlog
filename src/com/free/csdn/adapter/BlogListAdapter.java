package com.free.csdn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.util.ImageLoaderUtils;

/**
 * 博客列表适配器
 * 
 * @author wwj_748
 * @date 2012/8/9
 */
public class BlogListAdapter extends BaseAdapter {
	private ViewHolder holder; // 视图容器
	private LayoutInflater layoutInflater; // 布局加载器
	private Context context; // 上下文对象
	private List<BlogItem> list; // 博客列表

	public BlogListAdapter(Context context) {
		super();
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		list = new ArrayList<BlogItem>();

	}

	public void setList(List<BlogItem> list) {
		this.list = list;
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

	public void removeItem(int position) {
		if (list.size() > 0) {
			list.remove(position);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
			holder.content.setText(item.getContent());
			holder.date.setText(item.getDate());
			if (item.getImgLink() != null) {
				holder.img.setVisibility(View.VISIBLE);
				// 异步加载图片
				ImageLoaderUtils.displayImg(item.getImgLink(), holder.img);
			} else {
				//
				holder.img.setVisibility(View.VISIBLE);
			}
		}

		return convertView;
	}

	private class ViewHolder {
		TextView id;
		TextView date;
		TextView title;
		ImageView img;
		TextView content;
	}

}
