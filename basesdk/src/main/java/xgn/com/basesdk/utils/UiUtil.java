package xgn.com.basesdk.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import xgn.com.basesdk.R;
import xgn.com.basesdk.dialog.CavalierAlertDialog;
import xgn.com.basesdk.dialog.EasyAlertDialog;

public class UiUtil {

    private static Toast mToast;
    private static TextView mTv_msg;
    private static TextView mTv_desc;

    /**
     * 该方法用来关闭相应VIEW的硬件加速渲染，因为小米手机在硬件加速开启的情况下，WEBVIEW和动画一起渲染有问题
     *
     * @param view view
     */
    public static void closeHardwareAccelerated(View view) {
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if (null != view) {
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return formatter.format(date);
    }

    public static boolean isChinese(String s) {
        String regex = "[\\\\u4e00-\\\\u9fa5]{1,8}";
        return s.matches(regex);
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2017-06-14 16-09"）返回时间戳
     */
    public static Long getLongData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        String times = null;
        String stf = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            stf = String.valueOf(l);
            // times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(stf);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, null);
    }

    /**
     * 显示 Toast 的便捷方法
     *
     * @param text 提示的信息
     * @param desc 详细的描述，如果不想设置该项可以传 null 或者调用 {@link #showToast(Context, String)}
     */
    public static void showToast(Context context, String text, String desc) {
        if (mToast != null) {
            if (!TextUtils.isEmpty(desc)) {
                mTv_desc.setVisibility(View.VISIBLE);
                mTv_desc.setText(desc);
            } else {
                mTv_desc.setVisibility(View.GONE);
            }
            mTv_msg.setText(text);
            mToast.show();
        } else {
            mToast = new Toast(context.getApplicationContext());
            View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast_layout, null);
            mTv_msg = (TextView) view.findViewById(R.id.text_message);
            mTv_desc = (TextView) view.findViewById(R.id.text_description);
            if (!TextUtils.isEmpty(desc)) {
                mTv_desc.setVisibility(View.VISIBLE);
                mTv_desc.setText(desc);
            }
            mTv_msg.setText(text);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    public static void showToast(Context context,
            @StringRes
                    int resId) {
        showToast(context, context.getString(resId), null);
    }

    /**
     * 显示 Toast 的便捷方法
     *
     * @param msgResId  提示的信息的资源 id
     * @param descResId 详细的描述资源 id ，如果不想设置该项可以传 null 或者调用 {@link #showToast(Context, int)}
     */
    public static void showToast(Context context,
            @StringRes
                    int msgResId,
            @StringRes
                    int descResId) {
        showToast(context, context.getString(msgResId), context.getString(descResId));
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            mTv_desc = null;
            mTv_msg = null;
        }
    }

    public static String getText(EditText v) {
        if (null != v) {
            return v.getText().toString();
        }
        return null;
    }

    /**
     * 检查密码的合法性，8-20个字符，数字、大小写字母
     */
    public static boolean isPasswordValids(String psw) {
        String regex = "^[a-zA-Z0-9]{8,20}$";
        return psw.matches(regex);
    }

    /**
     * 是否为电话号码
     */
    public static boolean isPhoneNum(String phone) {
        String regex = "^1[3|5|7|8|][0-9]{9}$";
        boolean matches = phone.matches(regex);
        return matches;
    }

    /** 检查密码的合法性，6-12个字符，需包含字母和数字 */
    public static boolean isPasswordValid(String psw) {
        boolean isDigit = false;
        boolean isLetter = false;
        for (int i = 0; i < psw.length(); i++) {
            if (Character.isDigit(psw.charAt(i))) {
                isDigit = true;
            }
            if (Character.isLetter(psw.charAt(i))) {
                isLetter = true;
            }
            if (isDigit && isLetter) {
                break;
            }
        }
        String regex = "^[a-zA-Z0-9]{8,20}$";
        boolean isRight = isDigit && isLetter && psw.matches(regex);
        return isRight;
    }

    public static boolean isComparePwdSuccess(String pwd, String confirmPwd) {
        if (pwd.equals(confirmPwd)) {
            return true;
        }
        return false;
    }

    public static boolean comparePwd(String pwd1, String pwd2) {
        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
            return false;
        }
        if (pwd1.equals(pwd2)) {
            return true;
        }
        return false;
    }

    public static void showConfirmDialog(Context context, String msg,
            final OnButtonActionListener buttonAction) {
        final EasyAlertDialog dialog = new EasyAlertDialog(context);
        dialog.setMessageVisible(true);
        dialog.setMessage(TextUtils.isEmpty(msg) ? "" : msg);
        dialog.addPositiveButton(R.string.ok, new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction.actionPerformed();
                dialog.dismiss();
            }
        });
        dialog.addNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static AlertDialog createCavalierBtnDialog(Context context, String title, String content, String leftStr, String rightStr,
            OnClickListener leftL, OnClickListener rightL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.view_custom_title_btn_dialog, null, false);
        TextView titleTv = (TextView) layout.findViewById(R.id.dialog_tltle);
        TextView leftBtn = (TextView) layout.findViewById(R.id.dialog_btn_left);
        TextView rightBtn = (TextView) layout.findViewById(R.id.dialog_btn_right);
        TextView contentTv = (TextView) layout.findViewById(R.id.dialog_content);

        contentTv.setText(content);
        titleTv.setText(title);
        leftBtn.setText(leftStr);
        if (leftL != null) {
            leftBtn.setOnClickListener(leftL);
        }

        rightBtn.setText(rightStr);
        if (rightL != null) {
            rightBtn.setOnClickListener(rightL);
        }

        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        return dialog;
    }

    public static AlertDialog createCavalierBtnDialog(Context context, String title, String leftStr, String rightStr,
            OnClickListener leftL, OnClickListener rightL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.view_custom_content_btn_dialog, null, false);
        TextView titleTv = (TextView) layout.findViewById(R.id.dialog_tltle);
        TextView leftBtn = (TextView) layout.findViewById(R.id.dialog_btn_left);
        TextView rightBtn = (TextView) layout.findViewById(R.id.dialog_btn_right);
        titleTv.setText(title);
        leftBtn.setText(leftStr);
        if (leftL != null) {
            leftBtn.setOnClickListener(leftL);
        }

        rightBtn.setText(rightStr);
        if (rightL != null) {
            rightBtn.setOnClickListener(rightL);
        }

        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        return dialog;
    }

    public static void showConfirmDialog(Context context,
            @StringRes
                    int resId,
            OnButtonActionListener buttonAction) {
        showConfirmDialog(context, context.getString(resId), buttonAction);
    }

    /**
     * 拨打电话请调用该方法
     */
    public static void showConfirmCallDialog(final Context context, final
    @StringRes
            int title,
            final String phone) {
        new CavalierAlertDialog(context, CavalierAlertDialog.Type.NORMAL)
                .setTitleText(title)
                .setMessageText(phone)
                .setLeftButton(R.string.cancel, null)
                .setRightButton(R.string.call, new CavalierAlertDialog.OnActionListener() {
                    @Override
                    public void actionPerformed() {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone));
                        context.startActivity(intent);
                    }
                })
                .show();
    }


    /**
     * 显示骑手未认证的提示框
     */
    public static void showUnAuthenticateDialog(Context context, CavalierAlertDialog.OnActionListener onPositiveListener) {
        new CavalierAlertDialog(context, CavalierAlertDialog.Type.ONLY_MESSAGE)
                .setMessageOne(R.string.authenticate_before_grab)
                .setMessageTwo(R.string.authenticate_now)
                .setLeftButton(R.string.cancel, null)
                .setRightButton(R.string.authenticate, onPositiveListener)
                .show();
    }

    /**
     * 显示骑手认证未通过提示框
     */
    public static void showAuthenticateFailedDialog(Context context, CavalierAlertDialog.OnActionListener onPositiveListener) {
        new CavalierAlertDialog(context, CavalierAlertDialog.Type.ONLY_MESSAGE)
                .setMessageOne(R.string.authenticate_failed)
                .setMessageTwo(R.string.authenticate_again)
                .setLeftButton(R.string.cancel, null)
                .setRightButton(R.string.re_authenticate, onPositiveListener)
                .show();
    }

    /**
     * 显示押金不足的提示框
     *
     * @param depositValue       正常的押金金额
     * @param onPositiveListener 点击充值按钮后执行的操作
     */
    public static void showDepositInsufficient(Context context, int depositValue, CavalierAlertDialog.OnActionListener onPositiveListener) {
        new CavalierAlertDialog(context, CavalierAlertDialog.Type.NORMAL)
                .setTitleText(R.string.deposit_insufficient)
                .setMessageText(context.getString(R.string.charge_deposit, depositValue))
                .setLeftButton(R.string.cancel, null)
                .setRightButton(R.string.charge, onPositiveListener)
                .show();
    }

    public static Dialog createSettingGpsDialog(Context context, CavalierAlertDialog.OnActionListener onPositiveListener) {
        return new CavalierAlertDialog(context, CavalierAlertDialog.Type.NORMAL)
                .setTitleText(R.string.open_gps)
                .setMessageText(R.string.setting_gps_tip)
                .setLeftButton(R.string.cancel, null)
                .setRightButton(R.string.setting_now, onPositiveListener);
    }

    public static boolean isIdCard(String idCardNum) {
        if (TextUtils.isEmpty(idCardNum)) {
            return false;
        }
        String regex
                = "^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])("
                + "(\\d{4})|\\d{3}[Xx]))$";
        return idCardNum.matches(regex);
    }

    /**
     * 判断是否有新版本
     *
     * @param v1 当前版本
     * @param v2 本地版本
     * @return 是否有新版本
     */
    public static boolean compareVersion(String v1, String v2) {
        String[] v1s = v1.split("\\.");
        String[] v2s = v2.split("\\.");
        int n = Math.min(v1s.length, v2s.length);
        for (int i = 0; i < n; ++i) {
            int v1Value = 0, v2Value = 0;
            try {
                v1Value = Integer.valueOf(v1s[i]);
                v2Value = Integer.valueOf(v2s[i]);
            } catch (NumberFormatException e) {
            }

            if (v2Value > v1Value) {
                return true;
            }else if(v1Value > v2Value) {
                return false;
            }

        }
        return false;
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName.trim();
        } catch (PackageManager.NameNotFoundException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public interface OnButtonActionListener {
        void actionPerformed();
    }

    public static String checkString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
