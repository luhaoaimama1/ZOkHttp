package com.zone.okhttp.interceptor;

/**
 * Created by Administrator on 2016/6/18.
 * Dangerous interceptor that rewrites the server's cache-control header.
 */
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Dangerous interceptor that rewrites the server's cache-control header.
 */
public class Rewrite_cache_control_interceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "max-age=60")
                .build();
    }
};