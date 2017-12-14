package com.neteaseyx.image.ugallery.activity;

import java.io.Serializable;
import java.util.List;

import com.neteaseyx.image.R;
import com.neteaseyx.image.imageview.toolbox.GestureBaseImageView;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.adpter.AdapterPreviewImageViewPager;
import com.neteaseyx.image.ugallery.interfaces.IUIPreview;
import com.neteaseyx.image.ugallery.model.PhotoInfo;
import com.neteaseyx.image.ugallery.presenters.PresenterPreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图像预览  图片的大图
 *
 * @author yuhuibin
 * @date 2016-05-07
 */
public class ActivityPreviewImage extends ActivityUGalleryBase implements IUIPreview {
    public static final int FINISH = 1302;
    public static final int BACK = 1303;
    public static final String EXTRA_KEY_SELECT_PHOTOS = "extra_key_select_photos";
    public static final String EXTRA_KEY_CURRENT_PHOTOS = "extra_key_current_photos";
    public static final String EXTRA_KEY_CURRENT_POSITION = "extra_key_current_position";
    private static final String EXTRA_KEY_SELECT_MAX_IMAGE_SIZE = "extra_key_select_max_image_size";

    private static boolean mIsSingleImageSelect = false;

    private ViewPager mViewPager;
    private TextView mIndex;
    private TextView mSelect;

    private Context mContext;
    private PresenterPreview mPresenterPreview;
    private List<PhotoInfo> mCurrentFolderPhotos;

    private AdapterPreviewImageViewPager mAdpter;
    private RelativeLayout mRlTopLayout;
    private RelativeLayout mRlButtonLayout;
    private ImageView mToorBarBack;
    private TextView mTopBottomBtn;
    private boolean isShowFramOpen = false;
    private TranslateAnimation mShowTranslateAnimation;
    private AlphaAnimation mShowAlpaAnimation;
    private TranslateAnimation mHideTranslateAnimation;
    private AlphaAnimation mHideAlpaAnimation;

    public static void startActivity(Context context, List<PhotoInfo> currentPhotoInfos, List<PhotoInfo> selectPhotoInfos, int position,
            boolean isSingleImageSelect, int maxSize) {
        Intent intent = new Intent(context, ActivityPreviewImage.class);
        intent.putExtra(EXTRA_KEY_SELECT_PHOTOS, (Serializable) selectPhotoInfos);
        intent.putExtra(EXTRA_KEY_CURRENT_PHOTOS, (Serializable) currentPhotoInfos);
        intent.putExtra(EXTRA_KEY_CURRENT_POSITION, position);
        intent.putExtra(EXTRA_KEY_SELECT_MAX_IMAGE_SIZE, maxSize);
        mIsSingleImageSelect = isSingleImageSelect;
        ((Activity) context).startActivityForResult(intent, UGallery.PREVIEW_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        List<PhotoInfo> selectPhotos = (List<PhotoInfo>) getIntent().getSerializableExtra(EXTRA_KEY_SELECT_PHOTOS);
        mCurrentFolderPhotos = (List<PhotoInfo>) getIntent().getSerializableExtra(EXTRA_KEY_CURRENT_PHOTOS);
        mContext = this;
        //        setTitle("");
        //        setTitleBarRightButton(getResources().getString(R.string.select_finish));
        //        setTitleBarRightButtonEnable(true);
        //        setTitleBarBackOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //            mPresenterPreview.selectFinish(false);
        //            }
        //        });
        //设置辩题不显示
        setIsShowTitleBar(false);
        mPresenterPreview = new PresenterPreview(this, selectPhotos, mCurrentFolderPhotos, mIsSingleImageSelect,
                getIntent().getIntExtra(EXTRA_KEY_SELECT_MAX_IMAGE_SIZE, 9));
        initView();
        mPresenterPreview.imageScrollChangeState(getIntent().getIntExtra(EXTRA_KEY_CURRENT_POSITION, 0));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurrentFolderPhotos.clear();
        for (int i = 0; i < mViewPager.getChildCount(); i++) {
            GestureBaseImageView view = (GestureBaseImageView) mViewPager.getChildAt(i);
            Bitmap bitmap = view.getViewBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenterPreview.selectFinish(false);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onSelectImage(String state, int position, boolean isSelect, int selectIndex, int selectImageSize) {
        //  mIndex.setText(state);
        mViewPager.setCurrentItem(position);
        if (isSelect) {
            if (UGallery.getConfig().getGalleryImageSelectImage() == 0) {
                mSelect.setBackground(ContextCompat.getDrawable(mContext, R.drawable.image_icon_selected));
            } else {
                mSelect.setBackground(ContextCompat.getDrawable(mContext, UGallery.getConfig().getGalleryImageSelectImage()));
            }

            if (UGallery.getConfig().getImageSelectNumIsShow()) {
                mSelect.setText(selectIndex + 1 + "");
                mSelect.setVisibility(View.VISIBLE);
                System.out.println(selectIndex + 1 + "");
            } else {
                mSelect.setText("");
            }
        } else {
            if (UGallery.getConfig().getGalleryImageUnSelectImage() == 0) {
                mSelect.setBackground(ContextCompat.getDrawable(mContext, R.drawable.unselect_icon_iv));
            } else {
                mSelect.setBackground(ContextCompat.getDrawable(mContext, UGallery.getConfig().getGalleryImageUnSelectImage()));
            }
            mSelect.setText("");
        }
        if (selectImageSize == 0) {
            // setTitleBarRightButton(getResources().getString(R.string.select_finish));
        } else {
            if (!mIsSingleImageSelect) {
                // setTitleBarRightButton(getResources().getString(R.string.select_finish)+"("+selectImageSize+")");
                //   setTitleBarRightButton(getResources().getString(R.string.select_finish));
            }
        }
    }

    @Override
    public void onFinish(List<PhotoInfo> photoInfos, boolean finish) {
        Intent result = new Intent();
        result.putExtra(EXTRA_KEY_SELECT_PHOTOS, (Serializable) photoInfos);
        if (finish) {
            this.setResult(FINISH, result);
        } else {
            this.setResult(BACK, result);
        }
        finish();
    }

    @Override
    public void toast(String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mRlTopLayout = (RelativeLayout) findViewById(R.id.rl_top_layout);
        mRlButtonLayout = (RelativeLayout) findViewById(R.id.rl_button_layout);
        mToorBarBack = (ImageView) findViewById(R.id.toolbar_backs);
        mTopBottomBtn = (TextView) findViewById(R.id.tv_button_button);
        if (UGallery.getConfig().getTitleBarRightBtnColor() == 0) {
            mTopBottomBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            mTopBottomBtn.setTextColor(UGallery.getConfig().getTitleBarRightBtnColor());
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mAdpter = new AdapterPreviewImageViewPager(this, mCurrentFolderPhotos);
        mViewPager.setAdapter(mAdpter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenterPreview.imageScrollChangeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //  mIndex = (TextView)findViewById(R.id.index);
        mSelect = (TextView) findViewById(R.id.btn_seletct);
        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterPreview.imageSelectChangeState();
            }
        });

        //        setTitleBarRightClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                mPresenterPreview.selectFinish(true);
        //            }
        //        });

        mTopBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterPreview.selectFinish(true);
            }
        });

        mToorBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterPreview.selectFinish(false);
            }
        });

        mRlTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("finish1");
            }
        });

        mRlButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("finish2");
            }
        });

        showAnimations();
        hideAnimations();
    }

    /**
     * 点击实现动画的效果
     */
    public void showAnimation() {
        if (isShowFramOpen) {
            isShowFramOpen = false;
            mRlTopLayout.startAnimation(mShowTranslateAnimation);
            mRlButtonLayout.startAnimation(mShowAlpaAnimation);
        } else {
            isShowFramOpen = true;
            mRlTopLayout.startAnimation(mHideTranslateAnimation);
            mRlButtonLayout.startAnimation(mHideAlpaAnimation);
        }
    }

    private void showAnimations() {
        mShowTranslateAnimation = getTranslateAnimation(0, 0, -1, 0, 150);
        mShowAlpaAnimation = getAlpaAnimation(0, 1, 150);
    }

    private void hideAnimations() {
        mHideTranslateAnimation = getTranslateAnimation(0, 0, 0, -1, 150);
        mHideAlpaAnimation = getAlpaAnimation(1, 0, 150);
    }

    /**
     * 平移动画
     */
    private TranslateAnimation getTranslateAnimation(float fromXvelue, float toXvelue, float fromYValue, float toYValue, long duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromXvelue, Animation.RELATIVE_TO_SELF, toXvelue,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue
        );
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /**
     * 透明度动画
     */
    private AlphaAnimation getAlpaAnimation(float fromAlpha, float toAlpha, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

}
