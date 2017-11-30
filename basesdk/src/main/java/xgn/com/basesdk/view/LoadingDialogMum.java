package xgn.com.basesdk.view;

import android.content.Context;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xgn.com.basesdk.R;


/**
 * Created by yefeng on 2016/12/13
 * Modified by xxx
 */
public class LoadingDialogMum extends RelativeLayout {

    private ProgressBar mProgressBar;
    private LinearLayout mLlLayout;
    private TextView mTvNote;

    public LoadingDialogMum(Context context) {
        super(context);
        init();
    }

    public LoadingDialogMum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingDialogMum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.view_loading_dialog_mum, this, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mLlLayout = (LinearLayout) view.findViewById(R.id.ll_layout);
        mTvNote = (TextView) view.findViewById(R.id.tv_note);
        addView(view);
    }

    public int getTextNoteLangth() {
        TextPaint paint = mTvNote.getPaint();
        int width = (int) Layout.getDesiredWidth(mTvNote.getText().toString(), 0,
                mTvNote.getText().length(), paint);
       // float text = paint.measureText(mTvNote.getText().toString());
        return width;
    }

    public void setNoteViewShow(String msg) {
        mTvNote.setText(msg);
        mProgressBar.setVisibility(View.GONE);
        mLlLayout.setVisibility(View.VISIBLE);
    }

}
