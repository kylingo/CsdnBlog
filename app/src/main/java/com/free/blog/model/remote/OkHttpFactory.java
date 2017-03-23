package com.free.blog.model.remote;

import com.free.blog.library.util.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author tangqi on 17-3-14.
 */
public class OkHttpFactory {

    private static final String TAG = "OkHttpFactory";
    private static final int TIMEOUT_CONNECT = 15;
    private static OkHttpClient.Builder sBuilder;
    private static final OkHttpFactory sInstance = new OkHttpFactory();

    public static OkHttpFactory getsInstance() {
        return sInstance;
    }

    private OkHttpFactory() {
        sBuilder = new OkHttpClient.Builder();
        sBuilder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.log(TAG, message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
    }

    public OkHttpClient getOkHttpClient() {
        return sBuilder.build();
    }

}
