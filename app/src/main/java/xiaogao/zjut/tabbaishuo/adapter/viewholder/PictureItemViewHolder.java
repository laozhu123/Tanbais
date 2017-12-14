package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.FunctionListAdapter;
import xiaogao.zjut.tabbaishuo.adapter.PictureListAdapter;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;
import xiaogao.zjut.tabbaishuo.utils.SizeChange;

/**
 * Created by Administrator on 2017/11/19.
 */

public class PictureItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private View delete;
    private int type = 0;  //0是普通类型  1是相册类型
    private int itemHeight;
    private PictureListAdapter.OnItemClickListener onItemClickListener;
    private boolean showDelete = false;


    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public void setOnItemClickListener(PictureListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PictureItemViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        delete = itemView.findViewById(R.id.delete);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void renderView(Picture picture, final int index) {

//        img.setImageResource(picture.url);  show picture
        if (type == 0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img.getLayoutParams();
            if (index == 0) {
                lp.setMargins(SizeChange.Dp2Px(img.getContext(), 15), 0, 0, 0);
            } else {
                lp.setMargins(SizeChange.Dp2Px(img.getContext(), 10), 0, 0, 0);
            }
            img.setLayoutParams(lp);
        }
        if (type == 1) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) img.getLayoutParams();
            lp.setMargins(SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3));
            lp.height = itemHeight;
            img.setLayoutParams(lp);
            if (showDelete)
                delete.setVisibility(View.VISIBLE);
            else
                delete.setVisibility(View.GONE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.delete(index);
                }
            });
        }

        img.setImageResource(R.mipmap.helo);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(index);
            }
        });
    }

}
