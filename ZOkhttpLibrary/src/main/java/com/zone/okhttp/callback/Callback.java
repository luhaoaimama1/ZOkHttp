package com.zone.okhttp.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface Callback {
    public interface CommonCallback {
        void onStart();
        void onSuccess(String result,Call call, Response response);
        void onError(Call call, IOException e);
        //onSuccess  onError last call this method
        void onFinished();
    }

    public interface ProgressCallback extends CommonCallback {
        //todo How to do the progress bar
        void onLoading(long total, long current, long networkSpeed,boolean isDownloading);
//        void onProgress(int progress, boolean isDone, long networkSpeed);
    }
}
