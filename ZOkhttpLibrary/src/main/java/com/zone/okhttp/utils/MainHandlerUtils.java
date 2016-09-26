package com.zone.okhttp.utils;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.zone.okhttp.entity.ThreadMode;
import com.zone.okhttp.ok;
import com.zone.okhttp.callback.Callback;
import com.zone.okhttp.wrapper.RequestBuilderProxy;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Zone on 2016/3/17.
 */
public class MainHandlerUtils {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void onStart(final Callback.CommonCallback listener, ThreadMode threadMode) {
        handleThreadMode(threadMode, new Runnable() {
            @Override
            public void run() {
                listener.onStart();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void handleThreadMode(ThreadMode threadMode, Runnable runable) {
        switch (threadMode) {
            case MAIN:
                ok.getmHandler().post(runable);
                break;
            case BACKGROUND:
                AsyncTask.THREAD_POOL_EXECUTOR.execute(runable);
                break;
            case SERIAL_BACKGROUND:
                AsyncTask.SERIAL_EXECUTOR.execute(runable);
                break;

        }
    }

    public static void onFailure(final Callback.CommonCallback listener, final Call call, final IOException e, ThreadMode threadMode) {
        handleThreadMode(threadMode, new Runnable() {
            @Override
            public void run() {
                listener.onError(call, e);
            }
        });
    }

    public static void onFinished(final Callback.CommonCallback listener, ThreadMode threadMode) {
        handleThreadMode(threadMode, new Runnable() {
            @Override
            public void run() {
                listener.onFinished();
            }
        });
    }

    public static void onResponse(final Callback.CommonCallback listener, final Call call, final Response response, final String result, ThreadMode threadMode) {
        handleThreadMode(threadMode, new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(result, call, response);
            }
        });
    }

    public static void onLoading(final Callback.ProgressCallback listener, final long total, final long current, final long networkSpeed, final boolean isDownloading) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onLoading(total, current, networkSpeed, isDownloading);
            }
        });
    }
}
