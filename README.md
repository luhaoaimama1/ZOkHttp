# ZOkhttp

a okhttp util

#### [中文版文档](./README-cn.md)

### Declare
- [x] Cookie part borrow  hongyangAndroid !!!

### Solved problems 
- [x] Get, post (file JSON common post), put, delete, head, patch support 
- [x] Callback listener is in the UI thread(or background thread) and the speed calculation 
- [x] Files can be uploaded directly to the put file 
- [x] Global request parameters can be set 
- [x] support https
- [x] support cookie
- [x] support easy download 
###  Unsolved problems 

# Usage

### Jcenter
gradle

    compile 'com.zone:zokhttp:1.1.2'
pom.xml

     <dependency>
     <groupId>com.zone</groupId>
     <artifactId>zokhttp</artifactId>
     <version>1.1.0</version>
     <type>pom</type>
     </dependency>
    
    
# Easy use:
1.get
  
    ok.get("http://www.baidu.com", okListener).tag(this).executeSync();

2.post

    ok.post(UrlPath, new RequestParams().put("platform", "android")
                        .put("name", "bug").put("subject", 123 + ""), okListener).tag(this).executeSync();
3.file

    ok.post(UrlPath, new RequestParams().put("String_uid", "love")
                        .put("mFile", f).put("subject", "1327.jpg", f2), okListener).tag(this).executeSync();
4.Global configuration contain Https and cookie.
      
         //try {
        Map<String, String> commonParamMap = new HashMap<>();
        commonParamMap.put("commonParamMap", "param_Common");
        Map<String, String> commonHeaderMap = new HashMap<>();
        commonHeaderMap.put("commonHeaderMap", "header_Common");
        Map<String, String> commonHeaderReMap = new HashMap<>();
        commonHeaderReMap.put("commonHeaderMap", "header_CommonReplace");
         //try {
        ok.initConfig(new HttpConfig().setCommonHeaderAddMap(commonHeaderMap)
                        .setCommonHeaderReplaceMap(commonHeaderReMap).setCommonParamsMap(commonParamMap)
                          .cookieJar(cookieJar)//cookie example
         //                  .hostnameVerifier(new SkirtHttpsHostnameVerifier())//https skirt check
         //					.Certificates(CER_12306)
         //					.Certificates(getAssets().open("srca.cer")
        );


5.Tag:Take care:not use Activity  , should be use this Class Name .maybe stop memory leakage;

6.下载的例子  注意如果第二个是 文件夹  则target name 来自url 此例为inst.exe   如果第二个是文件 则target 为该文件

         ok.downLoad("http://down.360safe.com/360/inst.exe", FileUtils.getFile("DCIM", "Camera","360.exe"), okListener).tag(this).executeSync();
   
## Cookie(contain Session)(HongYang 's cookie Document)
Current projects include: 

* PersistentCookieStore //Persistent  cookie
* SerializableHttpCookie //Persistent  cookie
* MemoryCookieStore //cookie memory

If you encounter problems, welcome feedback, of course, you can achieve their own cookiejar interface, the preparation of cookie management related code. 

In addition, the persistence of cookie can also be used [https://github.com/franmontiel/PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar).

#Change Log:
version 1.1.1, add backgroundThread Mode; but onload callback all run main; other depend on setting;
 
      ok.get("http://www.baidu.com", okListener).tag(this)
                            .backgroundThread()
                            .tag(this).executeSync();

# Reference&Thanks：

https://github.com/pengjianbo/OkHttpFinal

https://github.com/hongyangAndroid/okhttp-utils