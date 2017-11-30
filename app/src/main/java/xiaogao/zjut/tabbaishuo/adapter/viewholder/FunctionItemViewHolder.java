package xiaogao.zjut.tabbaishuo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.FunctionListAdapter;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;

/**
 * Created by Administrator on 2017/11/19.
 */

public class FunctionItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private TextView tx;
    private View container;
    private FunctionListAdapter.OnItemClickListener mOnItemClickListner;

    public FunctionItemViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        tx = (TextView) itemView.findViewById(R.id.tx);
        container = itemView.findViewById(R.id.container);
    }

    public void setmOnItemClickListner(FunctionListAdapter.OnItemClickListener mOnItemClickListner) {
        this.mOnItemClickListner = mOnItemClickListner;
    }

    public void renderView(FunctionItemBean bean,final int index) {
        img.setImageResource(bean.getImgId());
        tx.setText(bean.getTxId());
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListner.onItemClick(index);
            }
        });
    }

}
