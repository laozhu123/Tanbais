package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xiaogao.zjut.tabbaishuo.R;

/**
 * Created by Administrator on 2018/1/13.
 */

public class UltraPagerAdapter extends PagerAdapter {

    private List<Integer> mLs;
    private Context mContext;
    private List<ImageView> mImgs;

    public UltraPagerAdapter(List<Integer> mLs, Context mContext) {
        this.mLs = mLs;
        this.mContext = mContext;
        mImgs = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mLs.size();
    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
//    @Override
//    public void destroyItem(ViewGroup view, int position, Object object) {
//        view.removeView(mImgs.get(position));
//    }
//
//    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
//    @Override
//    public Object instantiateItem(ViewGroup view, int position) {
//        ImageView scaleView = new ImageView(mContext);
////        scaleView.setImageResource(mLs.get(position));
//        mImgs.set(position, scaleView);
//        view.addView(scaleView);
//        return scaleView;
//    }




    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        //new LinearLayout(container.getContext());
        ImageView img = (ImageView) linearLayout.findViewById(R.id.img);
        img.setImageResource(mLs.get(position));
        container.addView(linearLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
