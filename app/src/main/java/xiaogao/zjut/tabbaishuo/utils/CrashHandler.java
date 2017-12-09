package xiaogao.zjut.tabbaishuo.utils;

/**
 * Created by huangwenqiang on 2017-07-05.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;

import xgn.com.basesdk.utils.DeviceUtil;
import xiaogao.zjut.tabbaishuo.BuildConfig;
import xiaogao.zjut.tabbaishuo.app.MyApplication;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,会弹错误Dialog.
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    public static final int CAUSE_COUNT = 3;

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler instance;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init() {
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        Activity activity = CavalierApplication.getAppInstance().getCurrentActivity();
        handleException(thread, ex);
    }

    private boolean handleException(final Thread thread, final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                String stackTrace = CrashHandler.this.getStackTrace(ex);
                Activity activity = MyApplication.getMyAppInstance().getCurrentActivity();

                if (BuildConfig.DEBUG) {
                    showDialog(stackTrace, activity, thread, ex);
                }

//                if (activity != null) {
//                    String error = stackTrace + "Cause by " + ex.getClass().getSimpleName() + "  ; " + ex.getLocalizedMessage();
//                    MobclickAgent.reportError(activity.getApplicationContext(), "reportError Activity : " + activity.getClass().getSimpleName() + "; error : " + error);
//                }
                Looper.loop();
            }
        }.start();
        return true;
    }

    private String getStackTrace(Throwable ex) {
        StringBuilder stackTrace = new StringBuilder();
        int count = 0;

        while (ex != null && count < CAUSE_COUNT) {
            stackTrace.append("Cause by " + ex.getClass().getSimpleName() + "\n" + ex.getLocalizedMessage() + "\n\n\n");
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                stackTrace.append( ex.getStackTrace()[i].toString() + "\n");
            }
            stackTrace.append("\n\n\n");
            ex = ex.getCause();
            count++;
        }
        return stackTrace.toString();
    }

    private void showDialog(String message, Activity activity, final Thread thread, final Throwable ex) {
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("出错了")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mDefaultHandler.uncaughtException(thread, ex);
                        dialog.dismiss();
                    }
                }).create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mDefaultHandler.uncaughtException(thread, ex);
            }
        });
        dialog.show();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = DeviceUtil.getScreenWidth(activity);
        lp.height = DeviceUtil.getScreenHeight(activity);
        dialog.getWindow().setAttributes(lp);
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    }

}
