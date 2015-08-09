package com.free.csdn.db;

import java.util.ArrayList;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.bean.Channel;

import android.content.Context;

/**
 *
 * @author tangqi
 * @data 2015年8月9日下午2:27:17
 */

public class ChannelManager {

	String[] mChannelNameArray = { "移动开发", "Web前端", "架构设计", "编程语言", "互联网", "数据库", "系统运维", "云计算",
			"研发管理", "综合" };

	int[] mResourceId = { R.drawable.logo_dropbox, R.drawable.logo_evernote,
			R.drawable.logo_googleplus, R.drawable.logo_neteasemicroblog,
			R.drawable.logo_pinterest, R.drawable.logo_sohumicroblog, R.drawable.logo_twitter,
			R.drawable.logo_vkontakte, R.drawable.logo_yixin, R.drawable.logo_yixinmoments };

	public ChannelManager(Context context) {
		// TODO Auto-generated constructor stub
	}

	public List<Channel> getChannelList() {
		List<Channel> list = new ArrayList<Channel>();
		for (int i = 0; i < mChannelNameArray.length; i++) {
			Channel channel = new Channel();
			channel.setChannelName(mChannelNameArray[i]);
			channel.setImgResourceId(mResourceId[i]);
			list.add(channel);
		}
		return list;
	}
}
