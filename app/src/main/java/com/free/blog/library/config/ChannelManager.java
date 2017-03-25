package com.free.blog.library.config;

import com.free.blog.R;
import com.free.blog.model.entity.Channel;
import com.free.blog.library.config.CategoryManager.CategoryName;
import com.free.blog.library.config.CategoryManager.ColumnUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道管理
 *
 * @author tangqi
 * @since 2015年8月9日下午2:27:17
 */

public class ChannelManager {

    private String[] mChannelNameArray = {
            CategoryName.ANDROID,
            CategoryName.MOBILE,
            CategoryName.WEB,
            CategoryName.ENTERPRISE,
            CategoryName.CODE,
            CategoryName.WWW,
            CategoryName.DATABASE,
            CategoryName.SYSTEM,
            CategoryName.CLOUD,
            CategoryName.SOFTWARE};

    private int[] mResourceId = {
            R.drawable.logo_dropbox,
            R.drawable.logo_evernote,
            R.drawable.logo_googleplus,
            R.drawable.logo_neteasemicroblog,
            R.drawable.logo_yixinmoments,
            R.drawable.logo_pinterest,
            R.drawable.logo_sohumicroblog,
            R.drawable.logo_twitter,
            R.drawable.logo_vkontakte,
            R.drawable.logo_yixin};

    private String[] mUrls = {
            ColumnUrl.ANDROID,
            ColumnUrl.MOBILE,
            ColumnUrl.WEB,
            ColumnUrl.ENTERPRISE,
            ColumnUrl.CODE,
            ColumnUrl.WWW,
            ColumnUrl.DATABASE,
            ColumnUrl.SYSTEM,
            ColumnUrl.CLOUD,
            ColumnUrl.SOFTWARE,};

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
