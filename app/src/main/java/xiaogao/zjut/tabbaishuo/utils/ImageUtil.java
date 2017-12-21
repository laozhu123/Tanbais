package xiaogao.zjut.tabbaishuo.utils;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import xiaogao.zjut.tabbaishuo.config.GlobalConfig;
import xiaogao.zjut.tabbaishuo.config.SingleConfig;

public class ImageUtil {
    public ImageUtil() {
    }

    public static SingleConfig.BitmapListener getBitmapListenerProxy(final SingleConfig.BitmapListener listener) {
        return (SingleConfig.BitmapListener) Proxy.newProxyInstance(SingleConfig.class.getClassLoader(), listener.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
                ImageUtil.runOnUIThread(new Runnable() {
                    public void run() {
                        try {
                            Object var1 = method.invoke(listener, args);
                        } catch (IllegalAccessException var2) {
                            var2.printStackTrace();
                        } catch (InvocationTargetException var3) {
                            var3.printStackTrace();
                        }

                    }
                });
                return null;
            }
        });
    }

    public static void runOnUIThread(Runnable runnable) {
        GlobalConfig.getMainHandler().post(runnable);
    }

    public static boolean shouldSetPlaceHolder(SingleConfig config) {
        return config.getPlaceHolderResId() <= 0?false:config.getResId() <= 0 && TextUtils.isEmpty(config.getFilePath()) && !GlobalConfig.getLoader().isCached(config.getUrl());
    }

    public static int dip2px(float dipValue) {
        float scale = GlobalConfig.context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, int targetWidth, int targeHeight) {
        float sourceWidth = (float)bitmap.getWidth();
        float sourceHeight = (float)bitmap.getHeight();
        float scaleWidth = (float)targetWidth / sourceWidth;
        float scaleHeight = (float)targeHeight / sourceHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if(needRecycle) {
            bitmap.recycle();
        }

        return bm;
    }

    public static String getRealType(File file) {
        FileInputStream is = null;

        String var4;
        try {
            String type;
            try {
                is = new FileInputStream(file);
                byte[] b = new byte[4];

                try {
                    is.read(b, 0, b.length);
                } catch (IOException var21) {
                    var21.printStackTrace();
                    var4 = "";
                    return var4;
                }

                type = bytesToHexString(b).toUpperCase();
                if(type.contains("FFD8FF")) {
                    var4 = "jpg";
                    return var4;
                }

                if(type.contains("89504E47")) {
                    var4 = "png";
                    return var4;
                }

                if(type.contains("47494638")) {
                    var4 = "gif";
                    return var4;
                }

                if(type.contains("49492A00")) {
                    var4 = "tif";
                    return var4;
                }

                if(!type.contains("424D")) {
                    var4 = type;
                    return var4;
                }

                var4 = "bmp";
            } catch (Exception var22) {
                var22.printStackTrace();
                type = "";
                return type;
            }
        } finally {
            try {
                is.close();
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

        return var4;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if(src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if(hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static Uri buildUriByType(SingleConfig config) {
        Log.e("builduri:", "url: " + config.getUrl() + " ---filepath:" + config.getFilePath() + "--content:" + config.getContentProvider());
        String content;
        if(!TextUtils.isEmpty(config.getUrl())) {
            content = appendUrl(config.getUrl());
            return Uri.parse(content);
        } else if(config.getResId() > 0) {
            return Uri.parse("res://imageloader/" + config.getResId());
        } else {
            if(!TextUtils.isEmpty(config.getFilePath())) {
                File file = new File(config.getFilePath());
                if(file.exists()) {
                    return Uri.fromFile(file);
                }
            }

            if(!TextUtils.isEmpty(config.getContentProvider())) {
                content = config.getContentProvider();
                if(!content.startsWith("content")) {
                    content = "content://" + content;
                }

                return Uri.parse(content);
            } else {
                return null;
            }
        }
    }

    public static String appendUrl(String url) {
        String newUrl = url;
        if(TextUtils.isEmpty(url)) {
            return url;
        } else {
            boolean hasHost = url.contains("http:") || url.contains("https:");
            if(!hasHost && !TextUtils.isEmpty(GlobalConfig.baseUrl)) {
                newUrl = GlobalConfig.baseUrl + url;
            }

            return newUrl;
        }
    }

    public static OkHttpClient getClient(boolean ignoreCertificateVerify) {
        return ignoreCertificateVerify?getAllPassClient():getNormalClient();
    }

    private static OkHttpClient getAllPassClient() {
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[])null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (KeyManagementException var5) {
            var5.printStackTrace();
        }

        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        OkHttpClient client = (new OkHttpClient.Builder()).sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(DO_NOT_VERIFY).readTimeout(0L, TimeUnit.SECONDS).connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(0L, TimeUnit.SECONDS).build();
        return client;
    }

    private static OkHttpClient getNormalClient() {
        OkHttpClient client = (new OkHttpClient.Builder()).readTimeout(0L, TimeUnit.SECONDS).connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(0L, TimeUnit.SECONDS).build();
        return client;
    }

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
    }
}
