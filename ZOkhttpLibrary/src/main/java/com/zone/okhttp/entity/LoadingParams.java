package com.zone.okhttp.entity;

/**
 * Created by Zone on 2016/2/10.
 */
public class LoadingParams {
    public int progress;

    public long networkSpeed, total, current;
    //false:End for upload   true:Upload in
    public boolean isUploading=true;

    @Override
    public String toString() {
        return new  String("super:"+ super.toString()+"\t progress"+progress+"  \t networkSpeed:"+networkSpeed+
                        "  \t total:"+total+" \t current:"+current+" \t isUploading:"+isUploading+"");
    }
}
