package com.zone.okhttp.wrapper;
import com.zone.okhttp.RequestParams;
import com.zone.okhttp.entity.ThreadMode;
import com.zone.okhttp.ok;
import com.zone.okhttp.utils.DownLoadUtils;
import com.zone.okhttp.utils.MainHandlerUtils;

import java.io.IOException;
import java.net.URL;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created by Zone on 2016/2/10.
 */
public class RequestBuilderProxy extends Request.Builder {
    private com.zone.okhttp.callback.Callback.CommonCallback mOkHttpListener;
    private RequestParams requestParams;
    private ThreadMode threadMode=ThreadMode.MAIN;

    public RequestBuilderProxy() {
        super();
    }

    @Override
    public RequestBuilderProxy url(HttpUrl url) {
        super.url(url);
        return this;
    }

    @Override
    public RequestBuilderProxy url(String url) {
       super.url(url);
      return this;
    }

    @Override
    public RequestBuilderProxy url(URL url) {
        super.url(url);
        return this;
    }

    @Override
    public RequestBuilderProxy header(String name, String value) {
        super.header(name, value);
        return this;
    }

    @Override
    public RequestBuilderProxy addHeader(String name, String value) {
        super.addHeader(name, value);
        return this;
    }

    @Override
    public RequestBuilderProxy removeHeader(String name) {
        super.removeHeader(name);
        return this;
    }

    @Override
    public RequestBuilderProxy headers(Headers headers) {
        super.headers(headers);
        return this;
    }

    @Override
    public RequestBuilderProxy cacheControl(CacheControl cacheControl) {
        super.cacheControl(cacheControl);
        return this;
    }

    @Override
    public RequestBuilderProxy get() {
        super.get();
        return this;
    }

    @Override
    public RequestBuilderProxy head() {
        super.head();
        return this;
    }

    @Override
    public RequestBuilderProxy post(RequestBody body) {
        super.post(body);
        return this;
    }

    @Override
    public RequestBuilderProxy delete(RequestBody body) {
        super.delete(body);
        return this;
    }

    @Override
    public RequestBuilderProxy delete() {
        super.delete();
        return this;
    }

    @Override
    public RequestBuilderProxy put(RequestBody body) {
        super.put(body);
        return this;
    }

    @Override
    public RequestBuilderProxy patch(RequestBody body) {
        super.patch(body);
        return this;
    }

    @Override
    public RequestBuilderProxy method(String method, RequestBody body) {
        super.method(method, body);
        return this;
    }

    @Override
    public RequestBuilderProxy tag(Object tag) {
         super.tag(tag);
        return this;
    }

    //TODO In addition to build all return this
    @Override
    public Request build() {
        return super.build();
    }

    public Response execute() {
        if(mOkHttpListener!=null)
            throw new IllegalStateException("method:execute is not Sync so listener shouldn't exist!");
        Call call = ok.getClient().newCall(this.build());
        Response temp = null;
        try {
            temp = call.execute();
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("cause:" + e.getCause() + "\t message:" + e.getMessage());
        }
        return temp;
    };

    public Call executeSync() {
        Call call = ok.getClient().newCall(this.build());
        if(mOkHttpListener!=null)
            MainHandlerUtils.onStart(mOkHttpListener,threadMode);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mOkHttpListener != null) {
                    MainHandlerUtils.onFailure(mOkHttpListener, call, e,threadMode);
                    MainHandlerUtils.onFinished(mOkHttpListener,threadMode);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mOkHttpListener != null) {
                    if(requestParams.isDownLoad()){
                        if(mOkHttpListener instanceof com.zone.okhttp.callback.Callback.ProgressCallback)
                            DownLoadUtils.saveFile((com.zone.okhttp.callback.Callback.ProgressCallback)
                                    mOkHttpListener,response,requestParams.getTarget(), threadMode);
                        else
                            DownLoadUtils.saveFile(null,response,requestParams.getTarget(),threadMode);

                    }else{
                        String result = response.body().string();
                        MainHandlerUtils.onResponse(mOkHttpListener, call, response, result,threadMode);
                    }
                    MainHandlerUtils.onFinished(mOkHttpListener,threadMode);
                }else
                    if(requestParams.isDownLoad())
                        DownLoadUtils.saveFile(null,response,requestParams.getTarget(), threadMode);
            }
        });
        return call;
    };



    public com.zone.okhttp.callback.Callback.CommonCallback getmOkHttpListener() {
        return mOkHttpListener;
    }

    public RequestBuilderProxy setmOkHttpListener(com.zone.okhttp.callback.Callback.CommonCallback mOkHttpListener) {
        this.mOkHttpListener = mOkHttpListener;
        return this;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public RequestBuilderProxy setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }
    /**
     * only Onloading interface must be Main;
     * other state  depend on ThreadMode state;
     */
    public RequestBuilderProxy backgroundThread() {
        this.threadMode = ThreadMode.BACKGROUND;
        return this;
    }
    /**
     * only Onloading interface must be Main;
     * other state  depend on ThreadMode state;
     */
    public RequestBuilderProxy background_SERIAL_Thread() {
        this.threadMode = ThreadMode.SERIAL_BACKGROUND;
        return this;
    }

    /**
     * only Onloading interface must be Main;
     * other state  depend on ThreadMode state;
     */
    public RequestBuilderProxy mainThread() {
        this.threadMode = ThreadMode.MAIN;
        return this;
    }


}
