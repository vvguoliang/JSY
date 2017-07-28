#在运行状态中出现报错现象
-keepattributes EnclosingMethod
#极光推送 
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#注解代码混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#指定代码的压缩级别
-optimizationpasses 5

#是否使用大小写混合
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#不优化输入的类文件
-dontoptimize

#混淆时是否记录日志
-verbose

#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#微信分享
-keep class com.tencent.mm.**{*;}
-keep public class com.tencent.** {*;}

#jni动态库
-keepclasseswithmembernames class * {
    native <methods>;
}

#资源文件
-keep public class com.sxsfinance.SXS.R$*{
   public static final int *;
}
#gson混淆配置
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature
-keepattributes *Annotation*
-keep public class com.project.mocha_patient.login.SignResponseData { private *; }


#友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.jsy.jsydemo.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#芝麻信用
-keep class com.alipayzhima.**{*;}
-keep class com.android.moblie.zmxy.antgroup.creditsdk.**{*;}
-keep class com.antgroup.zmxy.mobile.android.container.**{*;}
-keep class org.json.alipayzhima.**{*;}
#这个是警告，警告路过
-dontwarn okio.**

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory {*;}

-keep class com.unionpay.mobile.android.**{*;}

-keep public abstract interface com.asqw.android.Listener{
public protected <methods>;
}

-keep public class com.asqw.android{
public void Start(java.lang.String);
}

-keepclasseswithmembernames class * { native <methods>;
}

-keepclasseswithmembers class * { public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {public void *(android.view.View);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.sxsfinance.SXS.actvity.pay.**{
	*;
}

-keepattributes *Annotation*
#js交互
-keepattributes *JavascriptInterface*

-keep class com.sxsfinace.httplib.decode.**{
	*;
}

-keep class com.sxsfinace.httplib.entity.**{
	*;
}

-keep class com.sxsfinace.httplib.entity.base.**{
	*;
}

-keep class com.sxsfinace.httplib.entity.bean.**{
	*;
}
#
-keep class com.unionpay.mobile.android.**{
   *;
}
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keepattributes Signature
-ignorewarnings

#Support library
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#Support v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#Support v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
