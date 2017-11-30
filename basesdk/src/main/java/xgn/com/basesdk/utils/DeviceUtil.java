package xgn.com.basesdk.utils;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 系统硬件相关工具类
 */
@SuppressWarnings("unused")
public class DeviceUtil {

    /** 获取设备id（build.serial） */
    public static String getDeviceID() {
        return Build.SERIAL;
    }

    /** 获取手机 IMEI */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    /** 获取wifi地址 */
    public static String getWifiMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /** 判断是否是wifi连接 */
    public static boolean isWifiNetWork(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /** 判断是否有网络连接 */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean hasNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            Network[] networks = connectivity.getAllNetworks();
            if (networks != null) {
                for (Network network : networks) {
                    NetworkInfo info = connectivity.getNetworkInfo(network);
                    if (null != info && info.isAvailable() && info.isConnectedOrConnecting()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /** 获取屏幕宽度，单位为px */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.widthPixels;
        } else {
            return 0;
        }
    }

    /** 获取屏幕高度，单位为px */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.heightPixels;
        } else {
            return 0;
        }
    }

    /** 获取屏幕尺寸密度值 */
    public static float getDensity(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.density;
        } else {
            return 0;
        }
    }

    /** 获取系统字体sp密度值 */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.scaledDensity;
        } else {
            return 0;
        }
    }

    /** dip转pixel */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /** pixel转dip */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 获取DisplayMetrics对象 */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            return null;
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

}
