package com.free.blog.domain.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.free.blog.domain.bean.Blogger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BloggerDB extends SQLiteOpenHelper {
	private static final String TAG = "BLOGGER DB";

	private static final String DATABASE_NAME = "bloger.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLES_NAME = "bloggerTable";

	public BloggerDB(Context context) {
		// 创建数据库
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.e(TAG, "create database");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e(TAG, "oncreate DB");
		db.execSQL("CREATE TABLE " + TABLES_NAME + " (" + "_ID"
				+ " TEXT PRIMARY KEY," + "title" + " TEXT," + "description"
				+ " TEXT," + "img" + " TEXT," + "link" + " TEXT," + "type"
				+ " TEXT" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e(TAG, "delete DB");
		db.execSQL("DROP TABLE IF EXISTS bloggerTable");
		onCreate(db);
	}

	/**
	 * 插入数据
	 */
	public void insert(List<Blogger> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (Blogger item : list) {
			ContentValues values = new ContentValues();
			values.put("_ID", item.getUserId());
			values.put("title", item.getTitle());
			values.put("description", item.getDescription());
			values.put("img", item.getImgUrl());
			values.put("link", item.getLink());
			values.put("type", item.getType());

			db.insert(TABLES_NAME, null, values);
		}
	}

	/**
	 * 插入数据
	 */
	public void insert(Blogger blogger) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_ID", blogger.getUserId());
		values.put("title", blogger.getTitle());
		values.put("description", blogger.getDescription());
		values.put("img", blogger.getImgUrl());
		values.put("link", blogger.getLink());
		values.put("type", blogger.getType());

		db.insert(TABLES_NAME, null, values);
	}

	public void insertDB(String[] paramArrayOfString) {
		getWritableDatabase().execSQL(
				"replace into bloggerTable values(?,?, ?, ?, ?,?)",
				paramArrayOfString);
	}

	/**
	 * 删除数据
	 * 
	 * @param userId
	 *            博主ID号
	 */
	public void delete(String userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from bloggerTable where _ID = '" + userId + "'");
	}

	/**
	 * 查询数据
	 */
	public List<Blogger> query(String userId) {
		List<Blogger> list = new ArrayList<Blogger>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from bloggerTable where _ID = '" + userId + "'";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			do {
				Blogger item = new Blogger();
				item.setTitle(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				item.setImgUrl(cursor.getString(3));
				item.setLink(cursor.getString(4));
				item.setType(cursor.getString(5));
				item.setUserId(userId);

				list.add(item);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}

	/**
	 * 查询所有的博主
	 */
	public List<Blogger> SelectAllBlogger() {
		ArrayList<Blogger> blogerList = new ArrayList<Blogger>();
		Cursor localCursor = getReadableDatabase().rawQuery(
				"select * from bloggerTable order by type", null);
		localCursor.moveToFirst();
		if (localCursor.getCount() > 0)
			do {
				Blogger blogger = new Blogger();
				blogger.setUserId(localCursor.getString(0));
				blogger.setTitle(localCursor.getString(1));
				blogger.setDescription(localCursor.getString(2));
				blogger.setImgUrl(localCursor.getString(3));
				blogger.setLink(localCursor.getString(4));
				blogger.setType(localCursor.getString(5));
				blogerList.add(blogger);
			} while (localCursor.moveToNext());
		localCursor.close();
		return blogerList;
	}
}
