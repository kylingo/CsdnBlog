package com.free.blog.data.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author tangqi on 17-3-14.
 */
public interface INetApi {

    @GET("{userId}")
    Observable<String> getBloggerInfo(@Path("userId") String userId);
}
