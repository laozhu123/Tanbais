package xiaogao.zjut.tabbaishuo.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.VHolderCreatePostAddImage;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.VHolderCommitImage;

/**
 * Created by Yefeng on 2017/8/29.
 * Modified by xxx
 */
public class AdapterImageList extends RecyclerView.Adapter {
    private static final int IMAGE = 1;
    private static final int ADD_IMAGE = 2;
    private static final int MAX_IMAGE_SIZE = 6;
    private List<Uri> mImageUris;
    private View.OnClickListener mAddImageClickListener;
    private VHolderCommitImage.RemoveDeliveredImage mRemoveImageClickListener;
    private VHolderCommitImage.ShowDeliverImage mShowDeliverImage;

    public AdapterImageList(View.OnClickListener onAddClickListener,
                            VHolderCommitImage.RemoveDeliveredImage onRemoveClickListener,
                            VHolderCommitImage.ShowDeliverImage showDeliverImage, List<Uri> uris) {
        mImageUris = uris;
        mAddImageClickListener = onAddClickListener;
        mRemoveImageClickListener = onRemoveClickListener;
        mShowDeliverImage = showDeliverImage;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mImageUris.size()) {
            return IMAGE;
        } else {
            return ADD_IMAGE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ADD_IMAGE) {
            ((VHolderCreatePostAddImage) holder).onBindViewHolder(mAddImageClickListener);
        } else {
            ((VHolderCommitImage) holder).onBindViewHolder(mImageUris.get(position).toString(),
                    mRemoveImageClickListener, mShowDeliverImage, position);
        }
    }

    @Override
    public int getItemCount() {
        return mImageUris.size() >= MAX_IMAGE_SIZE ? MAX_IMAGE_SIZE : mImageUris.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE:
                View imageView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.dis_holder_create_post_image, parent, false);
                return new VHolderCommitImage(imageView);
            case ADD_IMAGE:
                View addView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.dis_holder_create_post_add_image, parent, false);
                return new VHolderCreatePostAddImage(addView);
        }
        return null;
    }


}
