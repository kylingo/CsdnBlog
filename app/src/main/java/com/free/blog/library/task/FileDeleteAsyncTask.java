package com.free.blog.library.task;

import android.os.AsyncTask;

import com.free.blog.library.util.FileUtils;

import java.io.File;

/**
 * 删除文件-Task
 *
 * @author Frank
 */
public class FileDeleteAsyncTask extends AsyncTask<File, Void, Boolean> {

    public FileDeleteAsyncTask() {

    }

    private OnResponseListener onResponseListener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(File... params) {
        return FileUtils.delete(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (null != onResponseListener) {
            onResponseListener.onResponse(String.valueOf(result));
        }
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }
}
