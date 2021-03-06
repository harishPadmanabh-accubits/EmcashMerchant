# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#
#
#
##------------------retrofit2------------
## Platform calls Class.forName on types which do not exist on Android to determine platform.
#-dontnote retrofit2.Platform
## Platform used when running on Java 8 VMs. Will not be used at runtime.
#-dontwarn retrofit2.Platform$Java8
## Retain generic type information for use by reflection by converters and adapters.
#-keepattributes Signature
## Retain declared checked exceptions for use by a Proxy instance.
#-keepattributes Exceptions
#
#
##------------------retrofit model class------------
#
#-keep class com.app.emcashmerchant.data.model.modelrequest** { *; }
#-keep class com.app.emcashmerchant.data.model.models** { *; }
#-keep class com.app.emcashmerchant.data.model.models** { *; }
#-keep class com.app.emcashmerchant.ui.transactionHistory.model** { *; }
#-keep public enum com.app.emcashmerchant.ui.transactionHistory.screenEnumHandler.**{
#    *;
#}
#
##------------------Glide------------
#
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
##------------------Glide------------
#
##------------------corutines------------
#-keep class kotlinx.coroutines.android.AndroidExceptionPreHandler
#-keep class kotlinx.coroutines.android.AndroidDispatcherFactory
##------------------corutines------------


