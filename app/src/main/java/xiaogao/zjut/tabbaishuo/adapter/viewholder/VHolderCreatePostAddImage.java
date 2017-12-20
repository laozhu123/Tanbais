package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import xiaogao.zjut.tabbaishuo.R;


/**
 * Created by Yefeng on 2017/8/29.
 * Modified by xxx
 */

public class VHolderCreatePostAddImage extends RecyclerView.ViewHolder {

    private ImageView mAddImage;

    public VHolderCreatePostAddImage(View itemView) {
        super(itemView);
        mAddImage = (ImageView) itemView.findViewById(R.id.add_image);
//        mAddImage.setLayoutParams(new RecyclerView.LayoutParams(ScreenUtil.dip2px(mContext, 72f), ScreenUtil.dip2px(mContext, 72f)));
    }

    public void onBindViewHolder(View.OnClickListener onClickListener) {
        mAddImage.setOnClickListener(onClickListener);
    }
}
