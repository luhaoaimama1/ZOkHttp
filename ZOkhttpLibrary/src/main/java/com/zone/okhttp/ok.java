package com.zone.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.zone.okhttp.entity.HttpType;
import com.zone.okhttp.utils.MediaTypeUtils;
import com.zone.okhttp.wrapper.ProgressRequestBody;
import com.zone.okhttp.wrapper.RequestBuilderProxy;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import com.zone.okhttp.callback.Callback;

/**
 * TODO ing:https not test
 * TODO Don't worry : 2. cook session 's Keep
 * TODO wondering: 1.I'm going to the frame cache if not change lane frame cache learning method
 * TODO wondering: 2. Back to the analysis I do not have to do in that framework
 * TODO wondering: 3.The end of the activity and the fragment life cycle after the termination request frame in the neighborhood is not here
 * Created by Zone on 2016/2/10.
 */
public class ok {//At that time z.ok () is the network to learn xutils I feel I should call zutils
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static HttpConfig httpConfig = new HttpConfig();
    private static OkHttpClient client = httpConfig.build();

    //-----------------------------get------------------------
    public static RequestBuilderProxy get(String urlString) {
        return get(urlString, null, null);
    }

    public static RequestBuilderProxy get(String urlString, Callback.CommonCallback listener) {
        return get(urlString, null, listener);
    }

    public static RequestBuilderProxy get(String urlString, RequestParams requestParams) {
        return get(urlString, requestParams, null);
    }

    public static RequestBuilderProxy get(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.GET, urlString, requestParams, listener);
    }

    //-----------------------------head------------------------
    public static RequestBuilderProxy head(String urlString) {
        return head(urlString, null, null);
    }

    public static RequestBuilderProxy head(String urlString, RequestParams requestParams) {
        return head(urlString, requestParams, null);
    }

    public static RequestBuilderProxy head(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.HEAD, urlString, requestParams, listener);
    }

    //-----------------------------delete------------------------
    public static RequestBuilderProxy delete(String urlString) {
        return delete(urlString, null, null);
    }

    public static RequestBuilderProxy delete(String urlString, RequestParams requestParams) {
        return delete(urlString, requestParams, null);
    }

    public static RequestBuilderProxy delete(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.DELETE, urlString, requestParams, listener);
    }

    //-----------------------------post------------------------
    public static RequestBuilderProxy post(String urlString, RequestParams requestParams) {
        return post(urlString, requestParams, null);
    }

    public static RequestBuilderProxy post(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.POST, urlString, requestParams, listener);
    }

    //-----------------------------put------------------------
    public static RequestBuilderProxy put(String urlString, RequestParams requestParams) {
        return put(urlString, requestParams, null);
    }

    public static RequestBuilderProxy put(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.PUT, urlString, requestParams, listener);
    }

    //-----------------------------patch------------------------
    public static RequestBuilderProxy patch(String urlString, RequestParams requestParams) {
        return patch(urlString, requestParams, null);
    }

    public static RequestBuilderProxy patch(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.PATCH, urlString, requestParams, listener);
    }

    //-----------------------------jsonStr------------------------
    //todo  Through the framework of the postjson seal to the parameters of the class
//    public static RequestBuilderProxy jsonStr(String urlString, String json) {
//        return jsonStr(urlString, json, httpConfig.getEncoding());
//    }
//
//    public static RequestBuilderProxy jsonStr(String urlString, String json, String encode) {
//        RequestBuilderProxy request = new RequestBuilderProxy();
//        RequestParams requestParams = new RequestParams();
//        requestParams.setmHttpType(HttpType.POST);
//        request = Helper.initCommonHeader(request, requestParams);
//
//        MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=" + encode);
//        request.url(urlString).post(RequestBody.create(MEDIA_TYPE_PLAIN, json));
//        return request;
//    }
    public static RequestBuilderProxy postJson(String urlString, RequestParams requestParams) {
        return postJson(urlString, requestParams, null);
    }

    public static RequestBuilderProxy postJson(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        if(requestParams==null)
            requestParams=new RequestParams();
        requestParams.setPostJson(true);
        return Helper.setHttpType(HttpType.POST, urlString, requestParams, listener);
    }

    //-----------------------------download easy-----------------------
    //if taget is Folder name  from url,else  target is file. name is target;
    public static RequestBuilderProxy downLoad(String urlString, File target) {
        return downLoad(urlString, null, target,null);
    }
    //if taget is Folder name  from url,else  target is file. name is target;
    public static RequestBuilderProxy downLoad(String urlString, File target, Callback.CommonCallback listener) {
        return downLoad(urlString, null, target,listener);
    }
    //if taget is Folder name  from url,else  target is file. name is target;
    public static RequestBuilderProxy downLoad(String urlString, RequestParams requestParams,File target) {
        return downLoad(urlString, requestParams,target, null);
    }
    //if taget is Folder name  from url,else  target is file. name is target;
    public static RequestBuilderProxy downLoad(String urlString, RequestParams requestParams,File target, Callback.CommonCallback listener) {
        if(requestParams==null)
            requestParams=new RequestParams();
        requestParams.isDownLoad(target);
        return Helper.setHttpType(HttpType.GET, urlString, requestParams, listener);
    }

    //Take care:not use Activity  , should be use this Class Name .to stop memory leakage;
    public static void cancelTag(Object tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public static void initConfig(HttpConfig config) {
        httpConfig = config;
        client = config.build();
    }

    final static class Helper {
        ;

        //--------------------------------------------------Internal tools -------------------------------------------------------
        private static RequestBuilderProxy setHttpType(HttpType httpType, String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
            if (requestParams == null)
                requestParams = new RequestParams();
            requestParams.setmHttpType(httpType);
            return requestCon(urlString, requestParams, listener);
        }

        //init head
        private static RequestBuilderProxy initCommonHeader(RequestBuilderProxy request, RequestParams requestParams) {
            if (requestParams.getHeaderReplaceMap() != null)
                for (Map.Entry<String, String> entry : requestParams.getHeaderReplaceMap().entrySet())
                    request.header(entry.getKey(), entry.getValue());
            if (requestParams.getHeaderAddMap() != null)
                for (Map.Entry<String, String> entry : requestParams.getHeaderAddMap().entrySet())
                    request.addHeader(entry.getKey(), entry.getValue());
            return request;
        }

        private static RequestBuilderProxy requestCon(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
            RequestBuilderProxy request = new RequestBuilderProxy();
            request.setmOkHttpListener(listener);
            request.setRequestParams(requestParams);
            initCommonHeader(request, requestParams);
            switch (requestParams.getmHttpType()) {
                case GET:
                    request.url(getUrlCon(urlString, requestParams)).get();
                    break;
                case HEAD:
                    request.url(getUrlCon(urlString, requestParams)).head();
                    break;
                case DELETE:
                    //todo There is a parameter.
                    request.url(getUrlCon(urlString, requestParams)).delete();
                    break;
                case POST:
                    if (!requestParams.isPostJson())
                        //normal
                        request.url(urlString).post(createRequestBody(requestParams, listener));
                    else{
                        //json
                        MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=" + requestParams.getEncoding());
                        request.url(urlString).post(RequestBody.create(MEDIA_TYPE_PLAIN, requestParams.getJsonStr()));
                    }
                    break;
                case PUT:
                    request.url(urlString).put(createRequestBody(requestParams, listener));
                    break;
                case PATCH:
                    request.url(urlString).patch(createRequestBody(requestParams, listener));
                    break;
                default:
                    break;
            }
            return request;

        }
        private static RequestBody createRequestBody(RequestParams requestParams, Callback.CommonCallback listener) {
            RequestBody formBody = null;
            if (requestParams.getFileMap() == null && requestParams.getFileNameMap() == null) {
                //have not file post
                FormBody.Builder form = new FormBody.Builder();
                for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet())
                    form.add(item.getKey(), item.getValue());
                formBody = form.build();
            } else {
                //have file post
                MultipartBody.Builder form = new MultipartBody.Builder();
                form.setType(MultipartBody.FORM);
                for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet())
                    form.addFormDataPart(item.getKey(), item.getValue());
                for (Map.Entry<String, File> item : requestParams.getFileMap().entrySet()) {
                    form.addFormDataPart(item.getKey(), requestParams.getFileNameMap().get(item.getKey()),
                            RequestBody.create(MediaType.parse(MediaTypeUtils.getFileSuffix(item.getValue())), item.getValue()));
                }

                //requestParams.getmProgressListener()  This method has already been processed to determine whether or not to open the

                if (Callback.ProgressCallback.class.isAssignableFrom(listener.getClass()))
                    formBody = new ProgressRequestBody(form.build(), (Callback.ProgressCallback) listener);
                else
                    formBody = new ProgressRequestBody(form.build(), null);
            }
            return formBody;
        }


        private static String getUrlCon(String urlString, RequestParams requestParams) {
            if (requestParams.getParamsMap() != null) {
                String get = "";
                for (Map.Entry<String, String> entry : requestParams.getParamsMap().entrySet()) {
                    get += entry.getKey() + "=" + entry.getValue() + "&";
                }
                urlString += "?" + get;
                return urlString.substring(0, urlString.length() - 1);
            }
            return urlString;
        }
    }

}
