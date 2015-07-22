package com.free.csdn.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author tangqi
 * @data 2015年7月9日下午4:41:18
 */

public class BloggerManager {

	/**
	 * 初始化博主信息
	 * 
	 * @param context
	 */
	public static void init(Context context, BloggerDB bloggerDB) {
		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("initDB", 0);
		if (!Boolean
				.valueOf(localSharedPreferences.getBoolean("isfirst", true))
				.booleanValue())
			return;

		String[] arrayOfString1 = new String[6];
		arrayOfString1[0] = "leixiaohua1020";
		arrayOfString1[1] = "雷霄骅(leixiaohua1020)的专栏";
		arrayOfString1[2] = "一个广院工科生的视音频技术笔记";
		arrayOfString1[3] = "http://avatar.csdn.net/A/7/6/1_leixiaohua1020.jpg";
		arrayOfString1[4] = "http://blog.csdn.net/leixiaohua1020";
		arrayOfString1[5] = "android";
		bloggerDB.insertDB(arrayOfString1);

		String[] arrayOfString2 = new String[6];
		arrayOfString2[0] = "guolin_blog";
		arrayOfString2[1] = "郭霖的专栏";
		arrayOfString2[2] = "每当你在感叹，如果有这样一个东西就好了的时候，请注意，其实这是你的机会";
		arrayOfString2[3] = "http://avatar.csdn.net/8/B/B/1_sinyu890807.jpg";
		arrayOfString2[4] = "http://blog.csdn.net/guolin_blog";
		arrayOfString2[5] = "android";
		bloggerDB.insertDB(arrayOfString2);

		String[] arrayOfString3 = new String[6];
		arrayOfString3[0] = "lyq8479";
		arrayOfString3[1] = "柳峰的专栏";
		arrayOfString3[2] = "推荐关注微信公众号“小q机器人”(微信号：xiaoqrobot)、“微趣闻”（微信号：laoyao_89），QQ交流群：315010949";
		arrayOfString3[3] = "http://avatar.csdn.net/1/4/A/1_lyq8479.jpg";
		arrayOfString3[4] = "http://blog.csdn.net/lyq8479";
		arrayOfString3[5] = "android";
		bloggerDB.insertDB(arrayOfString3);

		String[] arrayOfString4 = new String[6];
		arrayOfString4[0] = "ricohzhanglong";
		arrayOfString4[1] = "张龙（风中叶）的专栏";
		arrayOfString4[2] = "探寻未知";
		arrayOfString4[3] = "http://avatar.csdn.net/1/2/E/1_ricohzhanglong.jpg";
		arrayOfString4[4] = "http://blog.csdn.net/ricohzhanglong";
		arrayOfString4[5] = "android";
		bloggerDB.insertDB(arrayOfString4);

		String[] arrayOfString5 = new String[6];
		arrayOfString5[0] = "xiaanming";
		arrayOfString5[1] = "Mobile Internet developer";
		arrayOfString5[2] = "专注移动互联网开发，注重实战，是软件开发的一柄利剑";
		arrayOfString5[3] = "http://avatar.csdn.net/4/F/6/1_xiaanming.jpg";
		arrayOfString5[4] = "http://blog.csdn.net/xiaanming";
		arrayOfString5[5] = "android";
		bloggerDB.insertDB(arrayOfString5);

		String[] arrayOfString6 = new String[6];
		arrayOfString6[0] = "MoreWindows";
		arrayOfString6[1] = "MoreWindows Blog";
		arrayOfString6[2] = "求真求实，大气大为，立志成为一名优秀的系统架构师！";
		arrayOfString6[3] = "http://avatar.csdn.net/C/F/7/1_morewindows.jpg";
		arrayOfString6[4] = "http://blog.csdn.net/MoreWindows";
		arrayOfString6[5] = "android";
		bloggerDB.insertDB(arrayOfString6);

		String[] arrayOfString7 = new String[6];
		arrayOfString7[0] = "sxhelijian";
		arrayOfString7[1] = "迂者-贺利坚的专栏";
		arrayOfString7[2] = "为IT菜鸟起飞铺跑道，和学生一起享受快乐和激情的大学";
		arrayOfString7[3] = "http://avatar.csdn.net/0/0/D/1_sxhelijian.jpg";
		arrayOfString7[4] = "http://blog.csdn.net/sxhelijian";
		arrayOfString7[5] = "android";
		bloggerDB.insertDB(arrayOfString7);

		String[] arrayOfString8 = new String[6];
		arrayOfString8[0] = "LoveLion";
		arrayOfString8[1] = "刘伟技术博客";
		arrayOfString8[2] = "专注软件架构、设计模式、重构、UML和OOAD！";
		arrayOfString8[3] = "http://avatar.csdn.net/C/8/3/1_lovelion.jpg";
		arrayOfString8[4] = "http://blog.csdn.net/LoveLion";
		arrayOfString8[5] = "android";
		bloggerDB.insertDB(arrayOfString8);

		String[] arrayOfString9 = new String[6];
		arrayOfString9[0] = "dog250";
		arrayOfString9[1] = "Netfilter/iptables:-(";
		arrayOfString9[2] = "我不会编程，但也不是一点都不会，我稍微会一些 :-)";
		arrayOfString9[3] = "http://avatar.csdn.net/B/A/B/1_dog250.jpg";
		arrayOfString9[4] = "http://blog.csdn.net/dog250";
		arrayOfString9[5] = "android";
		bloggerDB.insertDB(arrayOfString9);

		String[] arrayOfString10 = new String[6];
		arrayOfString10[0] = "zouxy09";
		arrayOfString10[1] = "zouxy09的专栏";
		arrayOfString10[2] = "悲喜枯荣如是本无分别，当来则来，当去则去，随心，随性，随缘！-zouxy09@qq.com";
		arrayOfString10[3] = "http://avatar.csdn.net/9/0/6/1_zouxy09.jpg";
		arrayOfString10[4] = "http://blog.csdn.net/zouxy09";
		arrayOfString10[5] = "android";
		bloggerDB.insertDB(arrayOfString10);

		String[] arrayOfString11 = new String[6];
		arrayOfString11[0] = "changemyself";
		arrayOfString11[1] = "心有灵犀鬼才心";
		arrayOfString11[2] = "个人需求是第一生产力！";
		arrayOfString11[3] = "http://avatar.csdn.net/E/4/B/1_changemyself.jpg";
		arrayOfString11[4] = "http://blog.csdn.net/changemyself";
		arrayOfString11[5] = "android";
		bloggerDB.insertDB(arrayOfString11);

		String[] arrayOfString12 = new String[6];
		arrayOfString12[0] = "wwj_748";
		arrayOfString12[1] = "巫_1曲待续";
		arrayOfString12[2] = "一个人走到任何境地全都是因为自己。";
		arrayOfString12[3] = "http://avatar.csdn.net/C/C/8/1_wwj_748.jpg";
		arrayOfString12[4] = "http://blog.csdn.net/wwj_748";
		arrayOfString12[5] = "android";
		bloggerDB.insertDB(arrayOfString12);

		String[] arrayOfString13 = new String[6];
		arrayOfString13[0] = "work_for_money";
		arrayOfString13[1] = "会飞的懒猫";
		arrayOfString13[2] = "java总结";
		arrayOfString13[3] = "http://avatar.csdn.net/5/F/2/1_work_for_money.jpg";
		arrayOfString13[4] = "http://blog.csdn.net/work_for_money";
		arrayOfString13[5] = "android";
		bloggerDB.insertDB(arrayOfString13);

		String[] arrayOfString14 = new String[6];
		arrayOfString14[0] = "yanzi1225627";
		arrayOfString14[1] = "yanzi1225627的专栏";
		arrayOfString14[2] = "";
		arrayOfString14[3] = "http://avatar.csdn.net/1/4/F/1_yanzi1225627.jpg";
		arrayOfString14[4] = "http://blog.csdn.net/yanzi1225627";
		arrayOfString14[5] = "android";
		bloggerDB.insertDB(arrayOfString14);

		String[] arrayOfString15 = new String[6];
		arrayOfString15[0] = "ctthuangcheng";
		arrayOfString15[1] = "ctthuangcheng";
		arrayOfString15[2] = "自学成才。。。。加油！";
		arrayOfString15[3] = "http://avatar.csdn.net/8/9/6/1_ctthunagchneg.jpg";
		arrayOfString15[4] = "http://blog.csdn.net/ctthuangcheng";
		arrayOfString15[5] = "android";
		bloggerDB.insertDB(arrayOfString15);

		String[] arrayOfString16 = new String[6];
		arrayOfString16[0] = "Luoshengyang";
		arrayOfString16[1] = "老罗的Android之旅";
		arrayOfString16[2] = "爱生活，爱Android";
		arrayOfString16[3] = "http://avatar.csdn.net/5/6/E/1_luoshengyang.jpg";
		arrayOfString16[4] = "http://blog.csdn.net/Luoshengyang";
		arrayOfString16[5] = "android";
		bloggerDB.insertDB(arrayOfString16);

		String[] arrayOfString17 = new String[6];
		arrayOfString17[0] = "fansy1990";
		arrayOfString17[1] = "fansy1990的专栏";
		arrayOfString17[2] = "一日一积累";
		arrayOfString17[3] = "http://avatar.csdn.net/A/8/A/1_fansy1990.jpg";
		arrayOfString17[4] = "http://blog.csdn.net/fansy1990";
		arrayOfString17[5] = "android";
		bloggerDB.insertDB(arrayOfString17);
		SharedPreferences.Editor localEditor = localSharedPreferences.edit();
		localEditor.putBoolean("isfirst", false);
		localEditor.commit();
	}
}
