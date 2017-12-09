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
    public TextView sayS;
    public TextView piPD;
    public TextView description;
    public CardAdapter.OnItemClickListener onItemClickListener;

    public CardItemViewHolder(View view) {
        img = (ImageView) view.findViewById(R.id.img);
        nickName = (TextView) view.findViewById(R.id.nickName);
        vip = view.findViewById(R.id.vip);
        ageLW = (TextView) view.findViewById(R.id.ageLocateWork);
        sayS = (TextView) view.findViewById(R.id.sayS);
        piPD = (TextView) view.findViewById(R.id.piPD);
        description = (TextView) view.findViewById(R.id.description);
    }

    public void renderView(BaseCardItem item, final int index) {
        img.setImageResource(R.mipmap.helo);
        nickName.setText("千千厥歌");
        ageLW.setText("24岁·现在杭州·演员");
        sayS.setText("期望两年内结婚");
        piPD.setText("匹配度80%");
        description.setText("我从8岁开始就学习表演");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(index);
            }
        });
    }
}
