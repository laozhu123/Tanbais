package xgn.com.basesdk.commonui.config;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Process;

import java.util.Iterator;

import xgn.com.basesdk.base.app.CoreApplication;
import xgn.com.basesdk.commonui.view.SPUtils;

/**
 * Created by huluzi on 2017/8/11.
 */

public class ConfigManager {
    public static final String SP_NAME = "tbb_sp";
    public static final String ServerConfig = "server_env";
    private static final String KEY_RESTART_INTENT = "phoenix_restart_intent";
    private static final String[] ENV_ARR = new String[]{"开发环境", "测试环境", "预发环境", "线上测试环境", "线上生产环境"};
    public static int DEFAULT_ENV = 0;

    public ConfigManager() {
    }

    public static void setDefaultEnv(int env) {
        DEFAULT_ENV = env;
    }

    public static void saveServerEnv(int which) {
        SPUtils.getInstance("tbb_sp").put("server_env", which);
    }

    public static int getServerEnv() {
        return SPUtils.getInstance("tbb_sp").getInt("server_env", DEFAULT_ENV);
    }

    public static String getCurrentEnvironmentName() {
        try {
            return ENV_ARR[SPUtils.getInstance("tbb_sp").getInt("server_env")];
        } catch (IndexOutOfBoundsException var1) {
            var1.printStackTrace();
            return "";
        }
    }

    public static void showChooseDialog(final Context context, final int... pids) {
        (new AlertDialog.Builder(context)).setTitle("请选择您需要的环境").setItems(ENV_ARR, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(SPUtils.getInstance("tbb_sp").getInt("server_env") == which) {
                    dialog.dismiss();
                } else {
                    ConfigManager.saveServerEnv(which);
                    dialog.dismiss();
                    ConfigManager.exitApp(context, pids);
                }
            }
        }).setNegativeButton("取消", (DialogInterface.OnClickListener)null).show();
    }

    public static void showModifyDialog(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText httpEditText = generateEditText(context, "http url");
        linearLayout.addView(httpEditText);
        final EditText httpsEditText = generateEditText(context, "https url");
        linearLayout.addView(httpsEditText);
        (new AlertDialog.Builder(context)).setTitle("请输入环境").setView(linearLayout).setNegativeButton("取消", (DialogInterface.OnClickListener)null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String httpStr = ConfigManager.getEditTextStr(httpEditText);
                String httpsStr = ConfigManager.getEditTextStr(httpsEditText);
                if(!TextUtils.isEmpty(httpStr) && !TextUtils.isEmpty(httpsStr)) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(CoreApplication.getInstance(), "http or https url can not be null", Toast.LENGTH_LONG).show();
                }

            }
        }).show();
    }

    private static void exitApp(Context context, int... pids) {
//        EventBus.getDefault().post(new LoginOutEvent());
        //添加退出事件
        restart(context, pids);
    }

    private static void restart(Context context, int... pids) {
        triggerRebirth(context, pids);
    }

    private static void triggerRebirth(Context context, int... pids) {
        triggerRebirth(context, getRestartIntent(context), pids);
    }

    private static void triggerRebirth(Context context, Intent nextIntent, int... pids) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.putExtra("phoenix_restart_intent", nextIntent);
        context.startActivity(nextIntent);
        if(context instanceof Activity) {
            ((Activity)context).finish();
        }

        if(pids != null && pids.length != 0) {
            int[] var4 = pids;
            int var5 = pids.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                int pid = var4[var6];
                Process.killProcess(pid);
            }
        }

        Runtime.getRuntime().exit(0);
    }

    private static Intent getRestartIntent(Context context) {
        Intent defaultIntent = new Intent("android.intent.action.MAIN", (Uri)null);
        defaultIntent.addFlags(268468224);
        defaultIntent.addCategory("android.intent.category.LAUNCHER");
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        Iterator var4 = packageManager.queryIntentActivities(defaultIntent, 0).iterator();

        ActivityInfo activityInfo;
        do {
            if(!var4.hasNext()) {
                throw new IllegalStateException("Unable to determine default activity for " + packageName + ". Does an activity specify the DEFAULT category in its intent filter?");
            }

            ResolveInfo resolveInfo = (ResolveInfo)var4.next();
            activityInfo = resolveInfo.activityInfo;
        } while(!activityInfo.packageName.equals(packageName));

        defaultIntent.setComponent(new ComponentName(packageName, activityInfo.name));
        return defaultIntent;
    }

    private static String getEditTextStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    private static EditText generateEditText(Context context, String hint) {
        EditText editText = new EditText(context);
        editText.setHint(hint);
        editText.setFadingEdgeLength(0);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return editText;
    }
}
