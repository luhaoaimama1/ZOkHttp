package com.zone.okhttp.utils;

import com.zone.okhttp.callback.Callback;
import com.zone.okhttp.entity.ThreadMode;
import com.zone.okhttp.wrapper.RequestBuilderProxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/18.
 */
public class DownLoadUtils {
    static String urlPath="http://down.360safe.com/360/inst.exe";

    public static void saveFile(Callback.ProgressCallback listener, Response response, File saveFile, ThreadMode threadMode) throws IOException {
        if(response == null || !response.isSuccessful())
             MainHandlerUtils.onFailure(listener, null, new IOException("response may be null or isnot successful~"), threadMode);
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;

        try {
            long mPreviousTime = System.currentTimeMillis();
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long current = 0;

            if (saveFile.isDirectory()){
                String fileName=getFileNameByUrl(response.request().url().toString());
                fos = new FileOutputStream(new File(saveFile,fileName));
            }else
                fos = new FileOutputStream(saveFile);

            while ((len = is.read(buf)) != -1) {
                current += len;
                fos.write(buf, 0, len);

                if (listener!=null) {
                    //calculate  networkSpeed
                    long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
                    if ( totalTime == 0 )
                        totalTime += 1;
                    MainHandlerUtils.onLoading(listener,total, current,current / totalTime,current==total);
                }
            }
            fos.flush();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)  fos.close();
            } catch (IOException e) {
            }
        }
    }

    public static  String getFileNameByUrl(String urlString) {
        String[] lin = urlString.split("[/]");
        for (int i = lin.length - 1; i >= 0; i--) {
            if (lin[i].contains("."))
                return lin[i];
        }
        throw new IllegalStateException("not found  file name!");
    }
}
