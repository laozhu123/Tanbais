package xgn.com.basesdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import xgn.com.basesdk.R;
import xgn.com.basesdk.utils.ScreenUtil;


/**
 * Created by Administrator on 2016/12/20.
 */

public class EasyAlertDialog extends Dialog {
    private Context context;

    public static final int NO_TEXT_COLOR = -99999999;

    public static final int NO_TEXT_SIZE = -99999999;

    private View titleView;

    private TextView titleTV;

    private TextView messageTV;
    private EditText messageET;

    private Button positiveBtn, negativeBtn;

    private View btnDivideView;
    //增加了一个自定义VIEW
    private View mDialogContentView;
    private ViewGroup mDialogContentLay;


    private ImageView tipImage;

    private int imageResourceId = -1;

    private CharSequence extraMsg = "";

    private CharSequence title = "", message = "",hint = "", positiveBtnTitle = "", negativeBtnTitle = "";

    private int titleTextColor = NO_TEXT_COLOR, msgTextColor = NO_TEXT_COLOR,
            positiveBtnTitleTextColor = NO_TEXT_COLOR, negativeBtnTitleTextColor = NO_TEXT_COLOR;

    private float titleTextSize = NO_TEXT_SIZE, msgTextSize = NO_TEXT_SIZE, positiveBtnTitleTextSize = NO_TEXT_SIZE,
            negativeBtnTitleTextSize = NO_TEXT_SIZE;

    private int resourceId;

    private boolean isPositiveBtnVisible = true;
    private boolean isNegativeBtnVisible = false;
    private boolean isMessageVisble = true;
    private boolean isEditMessageVisble = false;
    private boolean isTitleVisible = false;

    private View.OnClickListener positiveBtnListener, negativeBtnListener;

    private HashMap<Integer, View.OnClickListener> mViewListener = new HashMap<Integer, View.OnClickListener>();

    public EasyAlertDialog(Context context, int resourceId, int style) {
        super(context, style);
        this.context = context;
        if (-1 != resourceId) {
            setContentView(resourceId);
            this.resourceId = resourceId;
        }
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = WindowManager.LayoutParams.MATCH_PARENT;
        Params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes( Params);
    }

    public EasyAlertDialog(Context context, int style) {
        this(context, -1, style);
        resourceId = R.layout.layout_easy_alert_dialog_default;
    }

    public EasyAlertDialog(Context context) {
        this(context, R.style.DialogStyle);
        resourceId = R.layout.layout_easy_alert_dialog_default;
    }

    public EasyAlertDialog(Context context, View view) {
        this(context, R.style.DialogStyle);
        resourceId = R.layout.layout_easy_alert_dialog_default;
        if(null != view){
            mDialogContentView = view;
        }
    }


    public void setTitle(CharSequence title) {
        isTitleVisible = TextUtils.isEmpty(title) ? false : true;
        setTitleVisible(isTitleVisible);
        if (null != title) {
            this.title = title;
            if (null != titleTV)
                titleTV.setText(title);
        }
    }

    public void setTitleVisible(boolean visible){
        isTitleVisible = visible;
        if(titleView != null){
            titleView.setVisibility(isTitleVisible ? View.VISIBLE : View.GONE);
        }
    }

    public void setTitleTextColor(int color) {
        titleTextColor = color;
        if (null != titleTV && NO_TEXT_COLOR != color)
            titleTV.setTextColor(color);
    }

    public void setMessageTextColor(int color) {
        msgTextColor = color;
        if (null != messageTV && NO_TEXT_COLOR != color)
            messageTV.setTextColor(color);

    }

    public void setMessageTextSize(float size) {
        msgTextSize = size;
        if (null != messageTV && NO_TEXT_SIZE != size)
            messageTV.setTextSize(size);
    }

    public void setTitleTextSize(float size) {
        titleTextSize = size;
        if (null != titleTV && NO_TEXT_SIZE != size)
            titleTV.setTextSize(size);
    }

    public void setMessageVisible(boolean visible){
        isMessageVisble = visible;
        if(messageTV != null){
            messageTV.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public void setMessage(CharSequence message) {
        if (null != message) {
            this.message = message;
            if (null != messageTV)
                messageTV.setText(message);
        }
    }

    public void setMessage(@StringRes int resId) {
        if (-1 != resId) {
            setMessage(context.getString(resId));
        }
    }

    public void setEditHint(CharSequence msg) {
        if (null != msg) {
            hint = msg;
            if(null != messageET){
                messageET.setHint(msg);
            }
        }
    }

    public void setEditMessageVisibility(boolean visibility) {
        isEditMessageVisble = visibility;
        if(messageET != null){
            messageET.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public String getEditMessage() {
        if(isEditMessageVisble && messageET != null){
            return messageET.getText().toString();
        }
        return null;
    }


    public void setImageResourceId(int resourceId) {
        if(resourceId != -1) {
            this.imageResourceId = resourceId;
            if(null != tipImage) {
                tipImage.setVisibility(View.VISIBLE);
                tipImage.setImageResource(imageResourceId);
            }
        }
    }

    public void addPositiveButton(CharSequence title, int color, float size,
                                  View.OnClickListener positiveBtnListener) {
        isPositiveBtnVisible = true;
        positiveBtnTitle = TextUtils.isEmpty(title) ? context
                .getString(R.string.ok) : title;
        positiveBtnTitleTextColor = color;
        positiveBtnTitleTextSize = size;
        this.positiveBtnListener = positiveBtnListener;

        if (positiveBtn != null) {
            positiveBtn.setText(positiveBtnTitle);
            positiveBtn.setTextColor(positiveBtnTitleTextColor);
            positiveBtn.setTextSize(positiveBtnTitleTextSize);
            positiveBtn.setOnClickListener(positiveBtnListener);
        }
    }

    public void addNegativeButton(CharSequence title, int color, float size,
                                  View.OnClickListener negativeBtnListener) {
        isNegativeBtnVisible = true;
        negativeBtnTitle = TextUtils.isEmpty(title) ? context
                .getString(R.string.cancel) : title;
        negativeBtnTitleTextColor = color;
        negativeBtnTitleTextSize = size;
        this.negativeBtnListener = negativeBtnListener;

        if (negativeBtn != null) {
            negativeBtn.setText(negativeBtnTitle);
            negativeBtn.setTextColor(negativeBtnTitleTextColor);
            negativeBtn.setTextSize(negativeBtnTitleTextSize);
            negativeBtn.setOnClickListener(negativeBtnListener);
        }
    }

    public void addPositiveButton(CharSequence title,
                                  View.OnClickListener positiveBtnListener) {
        addPositiveButton(title, NO_TEXT_COLOR, NO_TEXT_SIZE,
                positiveBtnListener);
    }

    public void addPositiveButton(@StringRes int resId,
                                  View.OnClickListener positiveBtnListener) {
        addPositiveButton(getContext().getString(resId), positiveBtnListener);
    }

    public void addNegativeButton(CharSequence title,
                                  View.OnClickListener negativeBtnListener) {
        addNegativeButton(title, NO_TEXT_COLOR, NO_TEXT_SIZE,
                negativeBtnListener);
    }

    public void addNegativeButton(@StringRes int resId,
                                  View.OnClickListener negativeBtnListener) {
        addNegativeButton(getContext().getString(resId), negativeBtnListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resourceId);
        try {
            ViewGroup root = (ViewGroup) findViewById(R.id.layout_easy_alert_dialog);
            if (root != null) {
                ViewGroup.LayoutParams params = root.getLayoutParams();
                params.width = (int) ScreenUtil.getDialogWidth(getContext());
                root.setLayoutParams(params);
            }

            titleView = findViewById(R.id.layout_easy_dialog_title);
            if (titleView != null) {
                setTitleVisible(isTitleVisible);
            }

            titleTV = (TextView) findViewById(R.id.tv_easy_dialog_title);
            if (titleTV != null) {
                titleTV.setText(title);
                if (NO_TEXT_COLOR != titleTextColor)
                    titleTV.setTextColor(titleTextColor);
                if (NO_TEXT_SIZE != titleTextSize)
                    titleTV.setTextSize(titleTextSize);
            }

            messageTV = (TextView) findViewById(R.id.tv_easy_dialog_message);
            if (messageTV != null) {
                messageTV.setText(message);
                setMessageVisible(isMessageVisble);
                if (NO_TEXT_COLOR != msgTextColor)
                    messageTV.setTextColor(msgTextColor);
                if (NO_TEXT_SIZE != msgTextSize)
                    messageTV.setTextSize(msgTextSize);
            }

            messageET = (EditText) findViewById(R.id.et_easy_dialog_message);
            if(messageET != null){
                setEditMessageVisibility(isEditMessageVisble);
                if(this.hint != null){
                    this.setEditHint(hint);
                }
                if (NO_TEXT_COLOR != msgTextColor)
                    messageET.setTextColor(msgTextColor);
                if (NO_TEXT_SIZE != msgTextSize)
                    messageET.setTextSize(msgTextSize);
            }
            if(null != mDialogContentView){
                mDialogContentLay = (ViewGroup) findViewById(R.id.dialog_content_lay);
                mDialogContentLay.addView(mDialogContentView);
            }

            positiveBtn = (Button) findViewById(R.id.btn_dialog_right);
            if (isPositiveBtnVisible && positiveBtn != null) {
                positiveBtn.setVisibility(View.VISIBLE);
                if (NO_TEXT_COLOR != positiveBtnTitleTextColor) {
                    positiveBtn.setTextColor(positiveBtnTitleTextColor);
                }
                if (NO_TEXT_SIZE != positiveBtnTitleTextSize) {
                    positiveBtn.setTextSize(positiveBtnTitleTextSize);
                }
                positiveBtn.setText(positiveBtnTitle);
                positiveBtn.setOnClickListener(positiveBtnListener);
            }

            negativeBtn = (Button) findViewById(R.id.btn_dialog_left);
            btnDivideView = findViewById(R.id.v_easy_dialog_btn_divide);
            if (isNegativeBtnVisible) {
                negativeBtn.setVisibility(View.VISIBLE);
                btnDivideView.setVisibility(View.VISIBLE);
                if (NO_TEXT_COLOR != this.negativeBtnTitleTextColor) {
                    negativeBtn.setTextColor(negativeBtnTitleTextColor);
                }
                if (NO_TEXT_SIZE != this.negativeBtnTitleTextSize) {
                    negativeBtn.setTextSize(negativeBtnTitleTextSize);
                }
                negativeBtn.setText(negativeBtnTitle);
                negativeBtn.setOnClickListener(negativeBtnListener);
            }

            if (mViewListener != null && mViewListener.size() != 0) {
                Iterator iter = mViewListener.entrySet().iterator();
                View view = null;
                while (iter.hasNext()) {
                    Map.Entry<Integer, View.OnClickListener> entry = (Map.Entry) iter.next();
                    view = findViewById(entry.getKey());
                    if(view != null && entry.getValue() != null) {
                        view.setOnClickListener(entry.getValue());
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Button getPositiveBtn() {
        return positiveBtn;
    }

    public Button getNegativeBtn() {
        return negativeBtn;
    }

    public void setViewListener(int viewId, View.OnClickListener listener) {
        mViewListener.put(viewId, listener);
    }
}

