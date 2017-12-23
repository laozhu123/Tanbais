package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.CardAdapter;
import xiaogao.zjut.tabbaishuo.bean.BaseCardItem;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CardItemViewHolder {
    public ImageView img;
    public TextView nickName;
    public View vip;
    public TextView ageLW;
    public TextView piPD;
    public CardAdapter.OnItemClickListener onItemClickListener;

    public CardItemViewHolder(View view) {
        img = (ImageView) view.findViewById(R.id.img);
        nickName = (TextView) view.findViewById(R.id.nickName);
        vip = view.findViewById(R.id.vip);
        ageLW = (TextView) view.findViewById(R.id.ageLocateWork);
        piPD = (TextView) view.findViewById(R.id.piPD);
    }

    public void renderView(BaseCardItem item, final int index) {
        img.setImageResource(R.mipmap.helo);
        nickName.setText("千千厥歌");
        ageLW.setText("24岁·现在杭州·演员");
        piPD.setText("匹配度80%");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(index);
            }
        });
    }
}
