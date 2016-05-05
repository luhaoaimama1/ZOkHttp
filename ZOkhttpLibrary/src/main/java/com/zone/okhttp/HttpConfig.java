package com.zone.okhttp;
import com.zone.okhttp.https.ClientBuilderHttpsWrapper;
import com.zone.okhttp.https.SkirtHttpsHostnameVerifier;
import com.zone.okhttp.utils.StringUtils;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import okhttp3.OkHttpClient;
import okio.Buffer;
/**
 * Created by Administrator on 2016/3/17.
 *To see the configuration parameters of the time required  {@link ok .getClient()}
 */
public class HttpConfig {
    //The default value is clientbuilder
    private OkHttpClient.Builder clientBuilder;
    private  String encoding = "utf-8";
    private  Map<String, String> commonParamsMap = new HashMap<String, String>();
    private  Map<String, String> commonHeaderAddMap = new HashMap<String, String>();
    private  Map<String, String> commonHeaderReplaceMap = new HashMap<String, String>();

    public HttpConfig() {
        clientBuilder = new OkHttpClient().newBuilder();
        //Default settings for time out
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
    }

    public HttpConfig readTimeout(long timeout, TimeUnit unit) {
        clientBuilder.readTimeout(timeout,unit);
        return this;
    }
    public HttpConfig writeTimeout(long timeout, TimeUnit unit) {
        clientBuilder.writeTimeout(timeout, unit);
        return this;
    }
    public HttpConfig connectTimeout(long timeout, TimeUnit unit) {
        clientBuilder.connectTimeout(timeout, unit);
        return this;
    }

    /**
     * {@link SkirtHttpsHostnameVerifier When the certificate is not set, HTTPS link trust. }
     * @param hostnameVerifier
     * @return
     */
    public HttpConfig hostnameVerifier(HostnameVerifier hostnameVerifier) {
        clientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }



     OkHttpClient build(){
        return clientBuilder.build();
    }


    //--------------------------------------------https begin------------------------------------------------------
    /**
     * Here is the support for HTTPS.
     */
    private  List<InputStream> mCertificateList;

    public HttpConfig Certificates(InputStream... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (InputStream inputStream : certificates) {
                if (inputStream != null)
                    mCertificateList.add(inputStream);
            }
        }
        return initCertificates();
    }

    public HttpConfig Certificates(String... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (String certificate : certificates) {
                if (!StringUtils.isEmptyTrim(certificate))
                    mCertificateList.add(new Buffer().writeUtf8(certificate).inputStream());
            }
        }
        return initCertificates();
    }

    private  void checkCertificateList_Init() {
        if (mCertificateList == null)
            mCertificateList = new ArrayList<InputStream>();
    }

    private HttpConfig initCertificates() {
        if (mCertificateList != null) {
            new ClientBuilderHttpsWrapper(clientBuilder).setCertificates(mCertificateList);
        }
       return this;
    }
//--------------------------------------------https end-----------------------------------------------------

    public  String getEncoding() {
        return encoding;
    }

    public  HttpConfig setEncoding(String encoding) {
        Charset charset = Charset.forName(encoding);
        if (charset!=null) {
            this.encoding = encoding;
        }
        return this;
    }
    public  Map<String, String> getCommonParamsMap() {
        return commonParamsMap;
    }

    public  HttpConfig setCommonParamsMap(Map<String, String> commonParamsMap) {
       this.commonParamsMap = commonParamsMap;
        return this;
    }

    public  Map<String, String> getCommonHeaderAddMap() {
        return commonHeaderAddMap;
    }

    public  HttpConfig setCommonHeaderAddMap(Map<String, String> commonHeaderAddMap) {
        this.commonHeaderAddMap = commonHeaderAddMap;
        return this;
    }

    public Map<String, String> getCommonHeaderReplaceMap() {
        return commonHeaderReplaceMap;
    }

    public HttpConfig setCommonHeaderReplaceMap(Map<String, String> commonHeaderReplaceMap) {
        this.commonHeaderReplaceMap = commonHeaderReplaceMap;
        return this;
    }
}
