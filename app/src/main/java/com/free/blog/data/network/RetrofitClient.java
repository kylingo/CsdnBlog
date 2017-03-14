package com.free.blog.data.network;

import com.free.blog.domain.config.AppConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author tangqi on 17-3-14.
 */
public class RetrofitClient {
    private static final RetrofitClient sInstance = new RetrofitClient();
    private INetApi mNetAPi;

    public static RetrofitClient getInstance() {
        return sInstance;
    }

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.CSDN_BASE_URL)
                .client(OkHttpFactory.getsInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNetAPi = retrofit.create(INetApi.class);
    }

    public INetApi getNetApi() {
        return mNetAPi;
    }

}
