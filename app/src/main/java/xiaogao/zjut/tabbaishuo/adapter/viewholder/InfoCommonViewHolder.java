package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.InfoCommonAdapter;
import xiaogao.zjut.tabbaishuo.bean.InfoCommonBean;

/**
 * Created by Administrator on 2017/12/7.
 */

public class InfoCommonViewHolder extends RecyclerView.ViewHolder {
    private TextView leftTx;
    private TextView rightTx;
    private View container;
    private View img;
    private InfoCommonAdapter.OnItemClickListener mOnItemClickListner;

    public InfoCommonViewHolder(View itemView) {
        super(itemView);
        leftTx = (TextView) itemView.findViewById(R.id.leftTx);
        rightTx = (TextView) itemView.findViewById(R.id.rightTx);
        img = itemView.findViewById(R.id.arrow);
        container = itemView.findViewById(R.id.container);
    }

    public void setmOnItemClickListner(InfoCommonAdapter.OnItemClickListener mOnItemClickListner) {
        this.mOnItemClickListner = mOnItemClickListner;
    }

    public void renderView(InfoCommonBean bean, final int index) {
        leftTx.setText(bean.left);

        switch (index) {
            case 0:
            case 5:
                if (bean.right.equals("")) {
                    rightTx.setText("请填写");
                    rightTx.setTextColor(rightTx.getContext().getResources().getColor(R.color.color_bbccd4));
                    img.setVisibility(View.VISIBLE);
                } else {
                    rightTx.setText(bean.right);
                    rightTx.setTextColor(rightTx.getContext().getResources().getColor(R.color.color_282828));
                    img.setVisibility(View.GONE);
                }
                break;
            case 1:
                rightTx.setText(bean.right);
                rightTx.setTextColor(rightTx.getContext().getResources().getColor(R.color.color_bbccd4));
                img.setVisibility(View.GONE);
                break;

            default:
                if (bean.right.equals("")) {
                    rightTx.setText("请选择");
                    rightTx.setTextColor(rightTx.getContext().getResources().getColor(R.color.color_bbccd4));
                    img.setVisibility(View.VISIBLE);
                } else {
                    rightTx.setText(bean.right);
                    rightTx.setTextColor(rightTx.getContext().getResources().getColor(R.color.color_282828));
                    img.setVisibility(View.VISIBLE);
                }
                break;
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListner.onItemClick(index);
            }
        });
    }
}
