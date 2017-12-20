package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.neteaseyx.image.imageview.toolbox.GestureBaseImageView;
import com.neteaseyx.image.ugallery.activity.ActivityPreviewImage;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;

import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityShowPicture;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;

/**
 * Created by Administrator on 2017/12/18.
 */

public class PreviewPictureAdapter extends PagerAdapter {

    private Context mContext;

    private List<Picture> mPhotoInfos;

    public PreviewPictureAdapter(Context context, List<Picture> photoInfos) {
        mPhotoInfos = photoInfos;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GestureBaseImageView gestureBaseImageView = new GestureBaseImageView(mContext);
        gestureBaseImageView.setImageResource(mPhotoInfos.get(position).resid);
        (container).addView(gestureBaseImageView);
        gestureBaseImageView.setId(position);
        gestureBaseImageView.setRotateEnabled(false);
        gestureBaseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发生单次点击时更新界面ui
                ActivityShowPicture activityPreviewImage = (ActivityShowPicture) mContext;
                activityPreviewImage.showAnimation();
            }
        });
        return gestureBaseImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return mPhotoInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
