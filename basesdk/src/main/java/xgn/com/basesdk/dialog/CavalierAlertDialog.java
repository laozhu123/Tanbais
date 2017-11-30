package xgn.com.basesdk.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import xgn.com.basesdk.R;


/**
 * Created by XiaoGai on 2017/4/15
 */

public class CavalierAlertDialog extends AlertDialog {

    private View mRoot;
    private View mBtnDivider;
    private TextView mTextTitle;
    private TextView mTextMessage;
    private TextView mTextMessageOne;
    private TextView mTextMessageTwo;
    private Button mBtnLeft;
    private Button mBtnRight;
    private Button mBtnCenter;
    private Type mType;

    public CavalierAlertDialog(@NonNull Context context, Type type) {
        super(context);
        mType = type;
        init(context);
    }

    public CavalierAlertDialog setTitleText(@StringRes int resId) {
        if (mType != Type.NORMAL && resId == -1) {
            return this;
        }
        mTextTitle.setText(resId);
        return this;
    }

    public CavalierAlertDialog setMessageOne(@StringRes int resId) {
        if (mType != Type.ONLY_MESSAGE && resId == -1) {
            return this;
        }
        mTextMessageOne.setText(resId);
        return this;
    }

    public CavalierAlertDialog setMessageTwo(@StringRes int resId) {
        if (mType != Type.ONLY_MESSAGE && resId == -1) {
            return null;
        }
        mTextMessageTwo.setText(resId);
        return this;
    }

    public CavalierAlertDialog setMessageText(@StringRes int resId) {
        if (mType != Type.NORMAL && resId == -1) {
            return this;
        }
        mTextMessage.setText(resId);
        return this;
    }

    public CavalierAlertDialog setMessageText(String msg) {
        if (mType != Type.NORMAL) {
            return this;
        }
        mTextMessage.setText(msg);
        return this;
    }

    public CavalierAlertDialog setLeftButton(@StringRes int text, final OnActionListener listener) {
        if (text != -1) {
            mBtnLeft.setText(text);
        }
        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.actionPerformed();
                }
                dismiss();
            }
        });
        return this;
    }

    public CavalierAlertDialog setRightButton(@StringRes int text, final OnActionListener listener) {
        if (text != -1) {
            mBtnRight.setText(text);
        }
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.actionPerformed();
                }
                dismiss();
            }
        });
        return this;
    }

    public CavalierAlertDialog setCenterButton(@StringRes int text, final OnActionListener listener) {
        if (text != -1) {
            mBtnCenter.setVisibility(View.VISIBLE);
            mBtnCenter.setText(text);
            mBtnLeft.setVisibility(View.GONE);
            mBtnRight.setVisibility(View.GONE);
            mBtnCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.actionPerformed();
                    }
                    dismiss();
                }
            });
        }
        return this;
    }

    private void init(Context context) {
        mRoot = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null);
        ViewStub viewStub;
        if (mType == Type.NORMAL) {
            viewStub = (ViewStub) mRoot.findViewById(R.id.viewStub_alert_dialog_content);
            viewStub.inflate();

            mTextTitle = (TextView) mRoot.findViewById(R.id.text_dialog_title);
            mTextMessage = (TextView) mRoot.findViewById(R.id.text_dialog_message);
        } else {
            viewStub = (ViewStub) mRoot.findViewById(R.id.viewStub_alert_dialog_only_message);
            viewStub.inflate();

            mTextMessageOne = (TextView) mRoot.findViewById(R.id.text_dialog_message_1);
            mTextMessageTwo = (TextView) mRoot.findViewById(R.id.text_dialog_message_2);
        }
        mBtnDivider = mRoot.findViewById(R.id.v_easy_dialog_btn_divide);
        mBtnLeft = (Button) mRoot.findViewById(R.id.btn_dialog_left);
        mBtnRight = (Button) mRoot.findViewById(R.id.btn_dialog_right);
        mBtnCenter = (Button) mRoot.findViewById(R.id.btn_dialog_center);
        setView(mRoot);
    }

    public enum Type {
        NORMAL, ONLY_MESSAGE
    }

    public interface OnActionListener {
        void actionPerformed();
    }
}
