package com.zone.okhttp.wrapper;
import com.zone.okhttp.RequestParams;
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

    //todo  listener not have is OK?I don't know!
    public Response execute() {
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
            MainHandlerUtils.onStart(mOkHttpListener);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mOkHttpListener != null) {
                    MainHandlerUtils.onFailure(mOkHttpListener, call, e);
                    MainHandlerUtils.onFinished(mOkHttpListener);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mOkHttpListener != null) {
                    if(requestParams.isDownLoad()){
                        if(mOkHttpListener instanceof com.zone.okhttp.callback.Callback.ProgressCallback)
                            DownLoadUtils.saveFile((com.zone.okhttp.callback.Callback.ProgressCallback)
                                    mOkHttpListener,response,requestParams.getTarget());
                        else
                            DownLoadUtils.saveFile(null,response,requestParams.getTarget());

                    }else{
                        String result = response.body().string();
                        MainHandlerUtils.onResponse(mOkHttpListener, call, response, result);
                    }
                    MainHandlerUtils.onFinished(mOkHttpListener);
                }else
                    if(requestParams.isDownLoad())
                        DownLoadUtils.saveFile(null,response,requestParams.getTarget());
            }
        });
        return call;
    };



    public com.zone.okhttp.callback.Callback.CommonCallback getmOkHttpListener() {
        return mOkHttpListener;
    }

    public void setmOkHttpListener(com.zone.okhttp.callback.Callback.CommonCallback mOkHttpListener) {
        this.mOkHttpListener = mOkHttpListener;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

}
