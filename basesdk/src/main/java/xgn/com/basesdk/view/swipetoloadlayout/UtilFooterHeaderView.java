package xgn.com.basesdk.view.swipetoloadlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xgn.com.basesdk.R;


/**
 * Created by huluzi on 2017/6/14.
 */

public class UtilFooterHeaderView {

    /**
     * 获取下拉刷新样式
     */
    public static View getClassicHeader(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_classic_header, viewGroup, false);
    }

    public static View getGoogleHookHeader(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_google_hook_header, viewGroup, false);
    }

    public static View getGoogleHeader(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_google_header, viewGroup, false);
    }

    public static View getJdHeader(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_jd_header, viewGroup, false);
    }

    /**
     * 获取上拉加载样式
     */
    public static View getClassicFooter(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_classic_footer, viewGroup, false);
    }

    public static View getGoogleHookFooter(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_google_hook_footer, viewGroup, false);
    }

    public static View getGoogleFooter(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_google_footer, viewGroup, false);
    }


    public static View getJdFooter(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.layout_jd_footer, viewGroup, false);
    }
}
