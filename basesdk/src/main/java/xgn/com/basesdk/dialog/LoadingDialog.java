package xgn.com.basesdk.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import xgn.com.basesdk.R;


/**
 * Created by Xiaogai on 2017/4/27.
 */

public class LoadingDialog extends AppCompatDialog {

    private TextView mTextView;

    public LoadingDialog(Context context) {
        super(context);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable());

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mTextView = (TextView) view. findViewById(R.id.text_message);

        setContentView(view);
    }

    public LoadingDialog setMessage(String message) {
        mTextView.setText(message);
        return this;
    }

    public LoadingDialog setMessage(@StringRes int resId) {
        setMessage(getContext().getString(resId));
        return this;
    }
}
