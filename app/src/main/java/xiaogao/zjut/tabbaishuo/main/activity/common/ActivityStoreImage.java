package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.activity.BaseBindPresenterActivity;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.main.fragment.FragmentImageViewer;
import xiaogao.zjut.tabbaishuo.views.CircleIndicator;

/**
 * 查看商家大图
 * Created by XiaoGai on 2017/6/7
 */

public class ActivityStoreImage extends BaseBindPresenterActivity {

    private static final String KEY_URLS = "urls";
    private static final String INDEX = "index";

    @Bind(R.id.image_back)
    ImageView mImageBack;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;

    String[] mUrls;
    int curIndex = 0;

    public static void start(Context context, String[] urls) {
        Intent intent = new Intent(context, ActivityStoreImage.class);
        intent.putExtra(KEY_URLS, urls);
        context.startActivity(intent);
    }

    public static void start(Context context, String[] urls, int index) {
        Intent intent = new Intent(context, ActivityStoreImage.class);
        intent.putExtra(KEY_URLS, urls);
        intent.putExtra(INDEX, index);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_store_image;
    }

    @Override
    protected void initActivity(View pView) {
        ButterKnife.bind(this);
        mUrls = getIntent().getStringArrayExtra(KEY_URLS);
        curIndex = getIntent().getIntExtra(INDEX, 0);

        getToolbar().setVisibility(View.GONE);
        Drawable d = DrawableCompat.wrap(mImageBack.getDrawable().mutate());
        DrawableCompat.setTint(d, Color.WHITE);
        mImageBack.setImageDrawable(d);
        mViewPager.setAdapter(new ImageViewerAdapter(getSupportFragmentManager()));
        if (mUrls.length >= 2) {
            mIndicator.setViewPager(mViewPager);
            mViewPager.setCurrentItem(curIndex);
        }
    }

    @OnClick(R.id.image_back)
    public void onBackButtonClick() {
        finish();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    class ImageViewerAdapter extends FragmentStatePagerAdapter {

        public ImageViewerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentImageViewer.newInstance(mUrls[position]);
        }

        @Override
        public int getCount() {
            return mUrls == null ? 0 : mUrls.length;
        }
    }

}
