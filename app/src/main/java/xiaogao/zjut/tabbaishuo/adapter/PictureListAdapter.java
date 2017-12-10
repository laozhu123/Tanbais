package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.FunctionItemViewHolder;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.PictureItemViewHolder;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;

/**
 * Created by Administrator on 2017/11/19.
 */

public class PictureListAdapter extends RecyclerView.Adapter<PictureItemViewHolder> {

    private Context context;
    private List<Picture> lists;
    private int resLayoutId = 0;
    private int type = 0;
    private int itemHeight;

    public PictureListAdapter(Context context, List<Picture> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setResLayoutId(int resLayoutId) {
        this.resLayoutId = resLayoutId;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public PictureItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (resLayoutId == 0)
            view = LayoutInflater.from(context).inflate(R.layout.item_list_picture, parent, false);
        else
            view = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        PictureItemViewHolder holder = new PictureItemViewHolder(view);
        holder.setType(type);
        holder.setItemHeight(itemHeight);
        return holder;
    }

    @Override
    public void onBindViewHolder(PictureItemViewHolder holder, int position) {
        holder.renderView(lists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}
