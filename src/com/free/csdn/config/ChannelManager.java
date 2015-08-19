package com.free.csdn.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.free.csdn.R;
import com.free.csdn.bean.Channel;

/**
 * 频道管理
 * 
 * @author tangqi
 * @data 2015年8月9日下午2:27:17
 */

public class ChannelManager {
	
	String[] mChannelNameArray = { "移动开发", "Web前端", "架构设计", "编程语言", "互联网", "数据库", "系统运维", "云计算", "研发管理" };

	int[] mResourceId = { R.drawable.logo_dropbox, R.drawable.logo_evernote, R.drawable.logo_googleplus,
			R.drawable.logo_neteasemicroblog, R.drawable.logo_yixinmoments, R.drawable.logo_pinterest,
			R.drawable.logo_sohumicroblog, R.drawable.logo_twitter, R.drawable.logo_vkontakte, R.drawable.logo_yixin };

	String[] mUrls = { CategoryManager.CATEGORY_MOBIE_URL, CategoryManager.CATEGORY_WEB_URL,
			CategoryManager.CATEGORY_ENTERPRISE_URL, CategoryManager.CATEGORY_CODE_URL, CategoryManager.CATEGORY_WWWW_URL,
			CategoryManager.CATEGORY_DATABASE_URL, CategoryManager.CATEGORY_SYSTEM_URL, CategoryManager.CATEGORY_CLOUD_URL,
			CategoryManager.CATEGORY_SOFTWARE_URL, };

	public ChannelManager(Context context) {
		// TODO Auto-generated constructor stub
	}

	public List<Channel> getChannelList() {
		List<Channel> list = new ArrayList<Channel>();
		for (int i = 0; i < mChannelNameArray.length; i++) {
			Channel channel = new Channel();
			channel.setChannelName(mChannelNameArray[i]);
			channel.setImgResourceId(mResourceId[i]);
			channel.setUrl(mUrls[i]);
			list.add(channel);
		}
		return list;
	}
}
