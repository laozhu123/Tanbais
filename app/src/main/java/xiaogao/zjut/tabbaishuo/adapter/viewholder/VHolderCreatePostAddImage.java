package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import xgn.com.basesdk.utils.ScreenUtil;
import xiaogao.zjut.tabbaishuo.R;


/**
 * Created by Yefeng on 2017/8/29.
 * Modified by xxx
 */

public class VHolderCreatePostAddImage extends RecyclerView.ViewHolder {

    private RelativeLayout mAddImage;
    private int itemHeight;
    private Context mContext;

    public VHolderCreatePostAddImage(View itemView) {
        super(itemView);
        mAddImage = (RelativeLayout) itemView.findViewById(R.id.add_image);
        mContext = itemView.getContext();
//        mAddImage.setLayoutParams(new RecyclerView.LayoutParams(ScreenUtil.dip2px(mContext, 72f), ScreenUtil.dip2px(mContext, 72f)));
    }

    public void onBindViewHolder(View.OnClickListener onClickListener) {
        mAddImage.setOnClickListener(onClickListener);

    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void renderView(){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mAddImage.getLayoutParams();
        lp.height = itemHeight;
        lp.setMargins(ScreenUtil.dip2px(mContext, 3f),ScreenUtil.dip2px(mContext, 6f),ScreenUtil.dip2px(mContext, 3f),ScreenUtil.dip2px(mContext, 3f));
        mAddImage.setLayoutParams(lp);
    }
}
