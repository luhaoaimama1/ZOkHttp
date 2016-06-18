package com.zone.okhttp.entity;

import java.io.File;

/**
 * Created by Zone on 2016/3/17.
 */
public enum HttpType {
    GET,POST,PUT,DELETE,HEAD,PATCH;

    public PostType postType=PostType.NORMAL ;
    public HttpType postJson(){
        postType=PostType.JSON;
        return this;
    }
    public enum PostType {
        NORMAL,JSON;
    }


    public boolean isDownLoad;
    public File target;
    public HttpType isDownLoad(File target){
        isDownLoad=true;
        this.target=target;
        return this;
    }
}
