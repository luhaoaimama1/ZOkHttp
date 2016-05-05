/*
 * Copyright (C) 2015 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.zone.okhttp.wrapper;
import com.zone.okhttp.entity.LoadingParams;
import com.zone.okhttp.utils.MainHandlerUtils;

import java.io.IOException;

import com.zone.okhttp.callback.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Desction:Packaging of the request body, processing progress
 * Author:pengjianbo(Borrow under~)
 * Date:15/12/10 下午5:31
 */
public class ProgressRequestBody extends RequestBody{
    //Actual request for packing
    private final RequestBody requestBody;
    //Schedule callback interface
    private final Callback.ProgressCallback mProgressCallback;
    //Packaging completed bufferedsink
    private BufferedSink bufferedSink;
    //Start to download time, the user to calculate the load speed
    private long mPreviousTime;

    private LoadingParams mLoadingParams;
    private boolean upLoadingOver;

    /**
     * Constructor, assignment
     * @param requestBody Request to be packed
     */
    public ProgressRequestBody(RequestBody requestBody, Callback.ProgressCallback mProgressCallback) {
        this.requestBody = requestBody;
        this.mProgressCallback=mProgressCallback;
//        this.progressListener = progressListener;
        mLoadingParams=new LoadingParams();
    }

    /**
     * Rewrite the call to the actual contenttype of the response body
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * Rewrite the call to the actual contentlength of the response body
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * Rewriting to write
     * @param sink BufferedSink
     * @throws IOException exception
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //packing
            bufferedSink = Okio.buffer(sink(sink));
        }
        //write
        requestBody.writeTo(bufferedSink);
        //Must call flush, or the last part of the data may not be written
        bufferedSink.flush();

    }

    /**
     * Write, callback schedule interface
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        mPreviousTime = System.currentTimeMillis();
        return new ForwardingSink(sink) {

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                //call-back
                if (mProgressCallback!=null) {
                    if ( mLoadingParams.total == 0) {
                        //Get the value of contentlength, the follow-up is no longer called
                        mLoadingParams.total= contentLength();
                    }
                    //Increases the number of bytes written by the current
                    mLoadingParams.current += byteCount;

                    //calculate  networkSpeed
                    long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
                    if ( totalTime == 0 ) {
                        totalTime += 1;
                    }
                    mLoadingParams.networkSpeed =  mLoadingParams.current / totalTime;
                    mLoadingParams.progress = (int)( mLoadingParams.current * 100 /  mLoadingParams.total);
                    mLoadingParams.isUploading=mLoadingParams.current != mLoadingParams.total;
                    if (!upLoadingOver) {
                        if (mLoadingParams.progress == 100 && !mLoadingParams.isUploading)
                            upLoadingOver = true;
                        MainHandlerUtils.onLoading(mProgressCallback, mLoadingParams.total, mLoadingParams.current,
                                mLoadingParams.networkSpeed, mLoadingParams.isUploading);
                    }
                }
            }
        };
    }

}
