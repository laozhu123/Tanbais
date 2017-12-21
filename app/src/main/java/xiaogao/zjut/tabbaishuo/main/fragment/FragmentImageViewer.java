package xiaogao.zjut.tabbaishuo.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.views.PhotoView;


/**
 * 查看商家大图
 * Created by XiaoGai on 2017/6/7
 */

public class FragmentImageViewer extends Fragment {

    private static final String URL = "url";

    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments() != null ? getArguments().getString(URL) : null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image_viewer, container, false);
        PhotoView pv = (PhotoView) root.findViewById(R.id.photoView);
        final ProgressBar pb = (ProgressBar) root.findViewById(R.id.progressBar);

        if (TextUtils.isEmpty(mUrl)) {
            pv.setImageResource(R.drawable.placehold);
        } else {
            pb.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mUrl)
                    .error(R.drawable.placehold)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(pv);
        }

        return root;
    }

    public static FragmentImageViewer newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(URL, url);
        FragmentImageViewer fragment = new FragmentImageViewer();
        fragment.setArguments(args);
        return fragment;
    }
}
