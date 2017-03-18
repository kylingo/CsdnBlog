package com.free.blog.library.task;

import android.content.Context;
import android.os.AsyncTask;

import com.free.blog.library.util.HttpUtils;


/**
 * Http请求
 * 
 * @author Frank
 *
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

	public HttpAsyncTask(Context context) {
		
	}

	private OnResponseListener onResponseListener;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected String doInBackground(String... params) {
		return HttpUtils.httpGet(params[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (null != onResponseListener) {
			if (null == result) {
				onResponseListener.onResponse(null);
			} else {
				onResponseListener.onResponse(result);
			}
		}
	}

	public void setOnResponseListener(OnResponseListener onResponseListener) {
		this.onResponseListener = onResponseListener;
	}
}
