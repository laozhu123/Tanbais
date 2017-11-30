package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.FunctionListAdapter;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;

/**
 * Created by Administrator on 2017/11/19.
 */

public class PictureItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;

    public PictureItemViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
    }

    public void renderView(Picture picture, final int index) {
//        img.setImageResource(picture.url);  show picture
        img.setImageResource(R.mipmap.helo);
    }

}
