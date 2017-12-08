package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.FunctionItemViewHolder;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.InfoCommonViewHolder;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.bean.InfoCommonBean;

/**
 * Created by Administrator on 2017/11/19.
 */

public class InfoCommonAdapter extends RecyclerView.Adapter<InfoCommonViewHolder> {

    private Context context;
    private List<String> lefts;
    private List<String> rights;
    private OnItemClickListener mOnItemClickListener;

    public InfoCommonAdapter(Context context, List<String> ls1, List<String> ls2) {
        this.context = context;
        this.lefts = ls1;
        this.rights = ls2;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public InfoCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_common_info, parent, false);
        InfoCommonViewHolder holder = new InfoCommonViewHolder(view);
        holder.setmOnItemClickListner(mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(InfoCommonViewHolder holder, int position) {
        holder.renderView(new InfoCommonBean(lefts.get(position), rights.get(position)), position);
    }


    @Override
    public int getItemCount() {
        return lefts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
