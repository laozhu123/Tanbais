package xgn.com.basesdk.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by huluzi on 2017/7/10.
 */

public class NetworkUtil {
    public static int NET_CNNT_BAIDU_OK = 1;
    public static int NET_CNNT_BAIDU_TIMEOUT = 2;
    public static int NET_NOT_PREPARE = 3;
    public static int NET_ERROR = 4;
    private static int TIMEOUT = 3000;

    public NetworkUtil() {
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == manager) {
            return false;
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            return null != info && info.isAvailable();
        }
    }

    public static String getLocalIpAddress() {
        String ret = "";

        try {
            Enumeration ex = NetworkInterface.getNetworkInterfaces();

            while(ex.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface)ex.nextElement();
                Enumeration enumIpAddr = intf.getInetAddresses();

                while(enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress()) {
                        ret = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static int getNetState(Context context) {
        try {
            ConnectivityManager e = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(e != null) {
                NetworkInfo networkinfo = e.getActiveNetworkInfo();
                if(networkinfo != null) {
                    if(networkinfo.isAvailable() && networkinfo.isConnected()) {
                        if(!connectionNetwork()) {
                            return NET_CNNT_BAIDU_TIMEOUT;
                        }

                        return NET_CNNT_BAIDU_OK;
                    }

                    return NET_NOT_PREPARE;
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return NET_ERROR;
    }

    private static boolean connectionNetwork() {
        boolean result = false;
        HttpURLConnection httpUrl = null;

        try {
            httpUrl = (HttpURLConnection)(new URL("http://www.baidu.com")).openConnection();
            httpUrl.setConnectTimeout(TIMEOUT);
            httpUrl.connect();
            result = true;
        } catch (IOException var6) {
            ;
        } finally {
            if(null != httpUrl) {
                httpUrl.disconnect();
            }

            httpUrl = null;
        }

        return result;
    }

    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 0;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static boolean is2G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && (activeNetInfo.getSubtype() == 2 || activeNetInfo.getSubtype() == 1 || activeNetInfo.getSubtype() == 4);
    }

    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED || mgrTel.getNetworkType() == 3;
    }
}
