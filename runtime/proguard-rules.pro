# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\develope_tools\android_sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
 -dontusemixedcaseclassnames
 -dontskipnonpubliclibraryclasses
 -dontpreverify
 -verbose
 -keepattributes Signature

-ignorewarnings

#-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#-libraryjars libs/android-support-v4.jar
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }

 -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
	native <methods>;
}

-keepclasseswithmembernames class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
	public static **[] values();
	public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

-dontwarn com.seaway.android.java.toolkit.**
-keep class com.seaway.android.java.toolkit.** { *;}

-keep class javax.security.**{*;}
-dontwarn javax.security.**

-keep class org.json.** { *; }
-keep class com.seaway.ky.biz.webview.util.** { *;}
-keep class com.seaway.ky.model.webview.data.** { *;}
-keep class com.seaway.ky.model.common.data.**{*;}

-keep class com.seaway.android.sdk.security.Security {
    <fields>;
    <methods>;
}


#WebView混淆
#-keepclassmembers class com.seaway.icomm.common.widget.webview.fragment.UIWebViewFragment$WebViewJavascriptBridge {
#  public *;
#}
-keepclassmembers class * extends android.webkit.WebChromeClient{
   		public void openFileChooser(...);
}

#WebView混淆
-keepclassmembers class com.seaway.ky.biz.webview.presenter {
   public *;
 }

#-keep class com.seaway.aggregate.web.bridge.vo.** { *;}

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-dontwarn com.covics.zxingscanner.**
-dontwarn com.google.zxing.**
-keep class com.covics.zxingscanner.** { *;}
-keep class com.google.zxing.** { *;}
#pos设备混淆
#-dontwarn com.landicorp.android.**
#-keep class com.landicorp.android.** { *;}

-dontwarn com.zj.btsdk.**
-keep class com.zj.btsdk.** { *;}

-keep public class com.seaway.ky.BuildConfig {
    public *;
}

-dontwarn com.xiao.**
-keep class com.xiao.nicevideoplayer.** { *;}

#pos设备打印混淆
-dontwarn com.nexgo.oaf.apiv3.**
-keep class com.nexgo.oaf.apiv3.** { *;}
-dontwarn com.nexgo.**
-keep class com.nexgo.** { *;}
-keep class com.xinguodu.** { *;}

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

-dontwarn android.content.pm.**
-keep class android.content.pm.** { *; }


