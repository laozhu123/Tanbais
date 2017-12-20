package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;


import xgn.com.basesdk.utils.ScreenUtil;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivitySuggestionResponse;


/**
 * Created by Yefeng on 2017/8/29.
 * Modified by xxx
 */

public class VHolderCommitImage extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private ImageView mRemove;
    private final Context mContext;
    private int mHeight;

    public VHolderCommitImage(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageView) itemView.findViewById(R.id.image);
        mRemove = (ImageView) itemView.findViewById(R.id.iv_remove);

        if (mHeight == 0) {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            mHeight = (dm.widthPixels - ScreenUtil.dip2px(mContext, 20f) * 2 - ScreenUtil.dip2px(mContext, 15f)
                    * 2) / ActivitySuggestionResponse.SPAN_COUNT;
        }
//        RelativeLayout layout =itemView.findViewById(R.id.root_layout);
//        layout.setLayoutParams(new RecyclerView.LayoutParams(ScreenUtil.dip2px(mContext, 72f), ScreenUtil.dip2px(mContext, 72f)));
    }

    public void onBindViewHolder(String url, final RemoveDeliveredImage removeCreatePostImage, final ShowDeliverImage showDeliverImage, final int position) {
        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCreatePostImage.remove(position);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeliverImage.show(position);
            }
        });

//        ImageLoader.with(mContext)
//                .url(url)
//                .placeHolder(R.color.image_bg)
//                .error(R.mipmap.loading_error_icon)
//                .thumbnail(0.1f)
//                .into(mImageView);
    }

    /**
     * 删除图像接口
     */
    public interface RemoveDeliveredImage {
        void remove(int position);
    }

    public interface ShowDeliverImage {
        void show(int position);
    }

}
