package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.FunctionItemViewHolder;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;

/**
 * Created by Administrator on 2017/11/19.
 */

public class FunctionListAdapter extends RecyclerView.Adapter<FunctionItemViewHolder> {

    private Context context;
    private List<FunctionItemBean> lists;
    private OnItemClickListener mOnItemClickListener;

    public FunctionListAdapter(Context context, List<FunctionItemBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public FunctionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_function, null);
        FunctionItemViewHolder holder = new FunctionItemViewHolder(view);
        holder.setmOnItemClickListner(mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(FunctionItemViewHolder holder, int position) {
        holder.renderView(lists.get(position), position);
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
