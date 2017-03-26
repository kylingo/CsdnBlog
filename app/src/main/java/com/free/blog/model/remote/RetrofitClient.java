package com.free.blog.model.remote;

import com.free.blog.library.config.Config;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author tangqi on 17-3-14.
 */
public class RetrofitClient {
    private static final RetrofitClient sInstance = new RetrofitClient();
    private IBlogApi mNetAPi;

    public static RetrofitClient getInstance() {
        return sInstance;
    }

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BLOG_HOST)
                .client(OkHttpFactory.getsInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNetAPi = retrofit.create(IBlogApi.class);
    }

    public IBlogApi getNetApi() {
        return mNetAPi;
    }

}
