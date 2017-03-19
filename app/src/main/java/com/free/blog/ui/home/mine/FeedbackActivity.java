package com.free.blog.ui.home.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.library.util.LogUtils;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 自定义反馈界面
 * 
 * @author tangqi
 *
 */
@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class FeedbackActivity extends Activity {

	private static final int VIEW_TYPE_USER = 0;
	private static final int VIEW_TYPE_DEV = 1;
	private static final int VIEW_TYPE_COUNT = 2;

	private ListView mListView;
	private Conversation mConversation;
	private Context mContext;
	private ReplyAdapter adapter;
	private EditText inputEdit;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.umeng_fb_activity_custom);
		mContext = this;

		initView();
		mConversation = new FeedbackAgent(this).getDefaultConversation();
		adapter = new ReplyAdapter();
		mListView.setAdapter(adapter);
		sync();

	}

	private void initView() {
		TextView mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText(getString(R.string.feedback));

		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mListView = (ListView) findViewById(R.id.fb_reply_list);
		Button sendBtn = (Button) findViewById(R.id.fb_send_btn);
		inputEdit = (EditText) findViewById(R.id.fb_send_content);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fb_reply_refresh);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = inputEdit.getText().toString();
				inputEdit.getEditableText().clear();
				if (!TextUtils.isEmpty(content)) {
					// 将内容添加到会话列表
					mConversation.addUserReply(content);
					// 刷新新ListView
					mHandler.sendMessage(new Message());
					// 数据同步
					sync();
				}
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				sync();
			}
		});
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
				android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
	}

	/**
	 * 数据同步
	 */
	private void sync() {
		mConversation.sync(new SyncListener() {

			@Override
			public void onSendUserReply(List<Reply> replyList) {

			}

			@Override
			public void onReceiveDevReply(List<Reply> replyList) {
				mSwipeRefreshLayout.setRefreshing(false);

				mHandler.sendMessage(new Message());

				if (replyList == null || replyList.size() == 0) {
					LogUtils.log("onReceiveDevReply empty");
				}
			}
		});

		adapter.notifyDataSetChanged();
		mListView.smoothScrollToPosition(mConversation.getReplyList().size());
	}

	class ReplyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mConversation.getReplyList().size();
		}

		@Override
		public Object getItem(int arg0) {
			return mConversation.getReplyList().get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE_COUNT;
		}

		@Override
		public int getItemViewType(int position) {
			Reply reply = mConversation.getReplyList().get(position);
			if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
				return VIEW_TYPE_DEV;
			} else {
				return VIEW_TYPE_USER;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			Reply reply = mConversation.getReplyList().get(position);
			if (convertView == null) {
				if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
					convertView = LayoutInflater.from(mContext).inflate(R.layout.umeng_fb_custom_dev_reply, null);
				} else {
					convertView = LayoutInflater.from(mContext).inflate(R.layout.umeng_fb_custom_user_reply, null);
				}

				holder = new ViewHolder();
				holder.replyContent = (TextView) convertView.findViewById(R.id.fb_reply_content);
				holder.replyProgressBar = (ProgressBar) convertView.findViewById(R.id.fb_reply_progressBar);
				holder.replyStateFailed = (ImageView) convertView.findViewById(R.id.fb_reply_state_failed);
				holder.replyData = (TextView) convertView.findViewById(R.id.fb_reply_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.replyContent.setText(reply.content);
			if (!Reply.TYPE_DEV_REPLY.equals(reply.type)) {
				if (Reply.STATUS_NOT_SENT.equals(reply.status)) {
					holder.replyStateFailed.setVisibility(View.VISIBLE);
				} else {
					holder.replyStateFailed.setVisibility(View.GONE);
				}

				if (Reply.STATUS_SENDING.equals(reply.status)) {
					holder.replyProgressBar.setVisibility(View.VISIBLE);
				} else {
					holder.replyProgressBar.setVisibility(View.GONE);
				}
			}

			if ((position + 1) < mConversation.getReplyList().size()) {
				Reply nextReply = mConversation.getReplyList().get(position + 1);
				if (nextReply.created_at - reply.created_at > 100000) {
					Date replyTime = new Date(reply.created_at);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					holder.replyData.setText(sdf.format(replyTime));
					holder.replyData.setVisibility(View.VISIBLE);
				}
			}
			return convertView;
		}

		class ViewHolder {
			TextView replyContent;
			ProgressBar replyProgressBar;
			ImageView replyStateFailed;
			TextView replyData;
		}
	}

}
