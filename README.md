# ZOkhttp
一个okhttp util
### 已解决的问题
- [x] GET,POST(文件 json 普通post),PUT,DELETE,HEAD,PATCH的支持
- [x] 回调的监听都在UI线程 并且有网速计算
- [x] 文件上传的时候 直接put文件即可
- [x] 可以设置全局请求参数
- [x] 支持https

### 未解决的问题
- [x] cook session 's Keep

# Easy use:
1.get
  
    ok.get("http://www.baidu.com", okListener).tag(this).executeSync();

2.post

    ok.post(UrlPath, new RequestParams().put("platform", "android")
                        .put("name", "bug").put("subject", 123 + ""), okListener).tag(this).executeSync();
3.file

    ok.post(UrlPath, new RequestParams().put("String_uid", "love")
                        .put("mFile", f).put("subject", "1327.jpg", f2), okListener).tag(this).executeSync();
4.全局配置
      
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