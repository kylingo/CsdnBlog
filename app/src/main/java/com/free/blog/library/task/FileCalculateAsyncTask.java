package com.free.blog.library.task;

import android.os.AsyncTask;

import com.free.blog.library.util.FileUtils;

import java.io.File;


/**
 * 计算文件目录大小-Task
 *
 * @author tangqi
 * @since 2015年8月15日下午9:38:04
 */
public class FileCalculateAsyncTask extends AsyncTask<File, Void, Long> {

	public FileCalculateAsyncTask() {

	}

	private OnResponseListener onResponseListener;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Long doInBackground(File... params) {
		return FileUtils.getFileSize(params[0]);
	}

	@Override
	protected void onPostExecute(Long result) {
		super.onPostExecute(result);
		if (null != onResponseListener) {
			onResponseListener.onResponse(String.valueOf(result));
		}
	}

	public void setOnResponseListener(OnResponseListener onResponseListener) {
		this.onResponseListener = onResponseListener;
	}
}
