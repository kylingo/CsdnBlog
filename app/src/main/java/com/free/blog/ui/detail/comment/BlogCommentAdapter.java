package com.free.blog.ui.detail.comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.Comment;
import com.free.blog.library.config.Config;
import com.free.blog.library.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;


/**
 *  评论列表适配器
 * 
 * @author tangqi
 * @since 2015年8月9日下午4:01:25
 */
public class BlogCommentAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private List<Comment> mData;

	public BlogCommentAdapter(Context context) {
		super();
		mLayoutInflater = LayoutInflater.from(context);
		mData = new ArrayList<Comment>();
	}

	public void setData(List<Comment> data) {
		this.mData = data;
	}

	public void addList(List<Comment> list) {
		this.mData.addAll(list);
	}

	@SuppressWarnings("unused")
	public void clearList() {
		this.mData.clear();
	}

	@SuppressWarnings("unused")
	public List<Comment> getmData() {
		return mData;
	}

	@SuppressWarnings("unused")
	public void removeItem(int position) {
		if (mData.size() > 0) {
			mData.remove(position);
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Comment item = mData.get(position); // 获取评论项
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			switch (item.getType()) {
			case Config.DEF_COMMENT_TYPE.PARENT: // 父项
				convertView = mLayoutInflater.inflate(R.layout.listitem_comment, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				// holder.reply = (TextView) convertView
				// .findViewById(R.id.replyCount);
				holder.userface = (ImageView) convertView.findViewById(R.id.userface);

				break;
			case Config.DEF_COMMENT_TYPE.CHILD: // 子项
				convertView = mLayoutInflater.inflate(R.layout.listitem_comment_child, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				break;
			}

			if (convertView != null) {
				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (null != item) {
			switch (item.getType()) {
			case Config.DEF_COMMENT_TYPE.PARENT:
				holder.name.setText(item.getUsername());
				holder.content.setText(Html.fromHtml(item.getContent()));
				holder.date.setText(item.getPostTime());
				// holder.reply.setText(item.getReplyCount());

				ImageLoaderUtils.displayRoundImage(item.getUserface(), holder.userface);
				break;
			case Config.DEF_COMMENT_TYPE.CHILD:
				holder.name.setText(item.getUsername());
				String replyText = item.getContent().replace("[reply]", "【");
				replyText = replyText.replace("[/reply]", "】");
				holder.content.setText(Html.fromHtml(replyText));
				holder.date.setText(item.getPostTime());
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		switch (mData.get(position).getType()) {
		case Config.DEF_COMMENT_TYPE.PARENT:
			return 0;
		case Config.DEF_COMMENT_TYPE.CHILD:
			return 1;
		}
		return 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	private class ViewHolder {
		// TextView id;
		TextView date;
		TextView name;
		TextView content;
		ImageView userface;
		// TextView reply;
	}
}
