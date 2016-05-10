# ZOkhttp

a okhttp util

#### [中文版文档](./README-cn.md)

### Solved problems 
- [x] Get, post (file JSON common post), put, delete, head, patch support 
- [x] Callback listener is in the UI thread and the speed calculation 
- [x] Files can be uploaded directly to the put file 
- [x] Global request parameters can be set 
- [x] support https

###  Unsolved problems 
- [x] cook session 's Keep

# Usage

### Jcenter
gradle

    compile 'com.zone:zokhttp:1.0.0'
pom.xml

     <dependency>
     <groupId>com.zone</groupId>
     <artifactId>zokhttp</artifactId>
     <version>1.0.0</version>
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
4.Global configuration 
      
         //try {
        Map<String, String> commonParamMap = new HashMap<>();
        commonParamMap.put("commonParamMap", "param_Common");
        Map<String, String> commonHeaderMap = new HashMap<>();
        commonHeaderMap.put("commonHeaderMap", "header_Common");
        Map<String, String> commonHeaderReMap = new HashMap<>();
        commonHeaderReMap.put("commonHeaderMap", "header_CommonReplace");
         //OkHttpUtils.setClient(OkHttpUtils.Certificates(getAssets().open("srca.cer")).perform());
         //try {
        ok.initConfig(new HttpConfig().setCommonHeaderAddMap(commonHeaderMap)
                        .setCommonHeaderReplaceMap(commonHeaderReMap).setCommonParamsMap(commonParamMap)
         //					.Certificates(CER_12306)
         //					.Certificates(getAssets().open("srca.cer")
        );


# Reference&Thanks：

https://github.com/pengjianbo/OkHttpFinal

https://github.com/hongyangAndroid/okhttp-utils