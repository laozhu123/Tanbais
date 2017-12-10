package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.FunctionListAdapter;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;
import xiaogao.zjut.tabbaishuo.utils.SizeChange;

/**
 * Created by Administrator on 2017/11/19.
 */

public class PictureItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private int type = 0;  //0是普通类型  1是相册类型
    private int itemHeight;

    public PictureItemViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void renderView(Picture picture, final int index) {

//        img.setImageResource(picture.url);  show picture
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img.getLayoutParams();
        if (type == 0) {
            if (index == 0) {
                lp.setMargins(SizeChange.Dp2Px(img.getContext(), 15), 0, 0, 0);
            } else {
                lp.setMargins(SizeChange.Dp2Px(img.getContext(), 10), 0, 0, 0);
            }
        }
        if (type == 1) {
            lp.setMargins(SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3), SizeChange.Dp2Px(img.getContext(), 3));
            lp.height = itemHeight;
        }
        img.setLayoutParams(lp);
        img.setImageResource(R.mipmap.helo);
    }

}
