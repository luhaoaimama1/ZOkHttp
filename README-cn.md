# ZOkhttp

一个okhttp util

### 声明
- [x] Cookie 部分是 借鉴  hongyangAndroid 的!!!

### 已解决的问题
- [x] GET,POST(文件 json 普通post),PUT,DELETE,HEAD,PATCH的支持
- [x] 回调的监听都在UI线程(或者后台线程) 并且有网速计算
- [x] 文件上传的时候 直接put文件即可
- [x] 可以设置全局请求参数
- [x] 支持https
- [x] 支持cookie
- [x] 简单的文件下载

### 未解决的问题

# Usage


### JicPack
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
Step 2. Add the dependency

> compile 'com.github.luhaoaimama1:ZOkHttp:[Latest release](https://github.com/luhaoaimama1/ZOkHttp/releases)'
    
# Easy use:
1.get
  
    ok.get("http://www.baidu.com", okListener).tag(this).executeSync();

2.post

    ok.post(UrlPath, new RequestParams().put("platform", "android")
                        .put("name", "bug").put("subject", 123 + ""), okListener).tag(this).executeSync();
3.file

    ok.post(UrlPath, new RequestParams().put("String_uid", "love")
                        .put("mFile", f).put("subject", "1327.jpg", f2), okListener).tag(this).executeSync();

4.全局配置  包含 https  和cookie的例子
      
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
                        .cookieJar(cookieJar)//cookie 的例子
         //                  .hostnameVerifier(new SkirtHttpsHostnameVerifier())//https 跳过检查
         //					.Certificates(CER_12306)
         //					.Certificates(getAssets().open("srca.cer")
        );

5.Tag:注意别直接使用Activity,而是用其类的名字。这样或许可以防止内存泄露。

6.Download : Take care :if taget is Folder name  from url,else  target is file. name is target;

         ok.downLoad("http://down.360safe.com/360/inst.exe", FileUtils.getFile("DCIM", "Camera","360.exe"), okListener).tag(this).executeSync();

## 对于Cookie(包含Session)(HongYang 's cookie 文档 )

目前项目中包含：

* PersistentCookieStore //持久化cookie
* SerializableHttpCookie //持久化cookie
* MemoryCookieStore //cookie信息存在内存中

如果遇到问题，欢迎反馈，当然也可以自己实现CookieJar接口，编写cookie管理相关代码。

此外，对于持久化cookie还可以使用[https://github.com/franmontiel/PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar).

相当于框架中只是提供了几个实现类，你可以自行定制或者选择使用。

#更改日志:
版本 1.1.3, 添加了 切换后台线程处理回调的模式 ;但是onload回调 一直都是主线程,其他回调则依靠你的模式设置;

      ok.get("http://www.baidu.com", okListener).tag(this)
                            .backgroundThread()
                            .tag(this).executeSync();

# Reference&Thanks：

https://github.com/pengjianbo/OkHttpFinal

https://github.com/hongyangAndroid/okhttp-utils