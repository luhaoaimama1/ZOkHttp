package com.zone.zokhttptest;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zone.okhttp.RequestParams;
import com.zone.okhttp.callback.SimpleProgressCallback;
import com.zone.okhttp.ok;
import com.zone.zokhttptest.entity.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String UrlPath = "http://101.39.62.207:8089/Test/log";
    Map<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_test);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        okHttp(v);
    }

    private void okHttp(View v) {
        switch (v.getId()) {
            case R.id.bt_okGet:
                //创建okHttpClient对象
//				OkHttpUtils.get(UrlPath + "?un=8&kb=ga").executeSync(okListener);
                ok.get("http://www.baidu.com", okListener).tag(this).executeSync();
                break;
            case R.id.bt_okPost:
                ok.post(UrlPath, new RequestParams().put("platform", "android")
                        .put("name", "bug").put("subject", 123 + ""), okListener).tag(this).executeSync();
                break;
            case R.id.json:
                String urlTest = "http://dev.shenxian.com:80/account-app/user/create.json";
                ok.postJson(urlTest,
                        new RequestParams().setJsonStr("{\"id\":\"11\",\"token\":\"22\"}"), okListener)
                        .tag(this).executeSync();
                ok.post(urlTest, new RequestParams().put("id", "11")
                        .put("token", "22"), okListener).tag(this).executeSync();
                break;
            case R.id.bt_okPost_Bg:
                ok.get("http://www.baidu.com", okListener).tag(this)
                        .backgroundThread()
                        .tag(this).executeSync();
                break;
            case R.id.bt_Https:
//                ok.get("https://kyfw.12306.cn/otn/", okListener).tag(this).executeSync();
                File a = FileUtils.getFile("", "360.exe");
                delete(a);
                break;

            case R.id.bt_okUpload:
                File f = new File(FileUtils.getFile(""), "高达 - 00.mp3");
                File f2 = new File(FileUtils.getFile("DCIM", "Camera"), "20150619_091758.jpg");
                map.put("String_uid", "love");
                ok.post(UrlPath, new RequestParams().put("String_uid", "love")
                        .put("mFile", f).put("subject", "1327.jpg", f2), okListener).tag(this).executeSync();
                break;
            case R.id.bt_downLoad:
                ok.downLoad("http://down.360safe.com/360/inst.exe", FileUtils.getFile("","360.exe"), okListener).tag(this).executeSync();
                break;
            default:
                break;
        }
    }

    public void delete(File b) {
        if (b.exists()) {
            System.out.println("path:"+b.getAbsolutePath()+"删除成功："+b.delete());
        }else{
            System.out.println("path:"+b.getAbsolutePath()+"不存在~");
        }
    }

    SimpleProgressCallback okListener = new SimpleProgressCallback() {

        @Override
        public void onError(Call call, IOException e) {
            super.onError(call, e);
            System.err.println("IOException >>" + e.getMessage());
        }

        @Override
        public void onLoading(long total, long current, long networkSpeed, boolean isDone) {
            super.onLoading(total, current, networkSpeed, isDone);
            System.out.println(" progress" + ((int) (current * 100 / total)) + "  \t networkSpeed:" + networkSpeed +
                    "  \t total:" + total + " \t current:" + current + " \t isDone:" + isDone + "");
//            tvOkHttp.setText("progress:"+mLoadingParams.progress);
        }

        @Override
        public void onSuccess(String result, Call call, Response response) {
            super.onSuccess(result, call, response);
            System.out.println("current Thread Id:"+Thread.currentThread().getId());
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("UI Thread Id:"+Thread.currentThread().getId());
                }
            });
            System.out.println("onSuccess result>>" + result);
            Gson g = new Gson();
            try {
                Data data = g.fromJson(result, Data.class);
                System.out.println("code:" + data.getCode());
            } catch (JsonSyntaxException e) {
            }

        }

        @Override
        public void onStart() {
            super.onStart();
            System.out.println("OkHttpSimpleListener  onStart>>");
        }

        @Override
        public void onFinished() {
            super.onFinished();
            System.out.println("onFinished");
        }
    };

}
