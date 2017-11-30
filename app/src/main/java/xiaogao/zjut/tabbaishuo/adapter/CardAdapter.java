package xiaogao.zjut.tabbaishuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beyondsw.lib.widget.StackCardsView;

import java.util.ArrayList;
import java.util.List;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.CardItemViewHolder;
import xiaogao.zjut.tabbaishuo.bean.BaseCardItem;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CardAdapter extends StackCardsView.Adapter {

    private List<BaseCardItem> mItems;
    private Context mContext;

    public CardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void appendItems(List<BaseCardItem> items) {
        int size = items == null ? 0 : items.size();
        if (size == 0) {
            return;
        }
        if (mItems == null) {
            mItems = new ArrayList<>(size);
        }
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CardItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card, parent, false);
            viewHolder = new CardItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CardItemViewHolder) convertView.getTag();
        }
        viewHolder.renderView(mItems.get(position));
        return convertView;
    }

//    @Override
//    public int getSwipeDirection(int position) {
//        //这里控制每张卡的支持滑动超过一定距离消失的方向
//        BaseCardItem item = mItems.get(position);
//        return item.swipeDir;
//    }
//
//    @Override
//    public int getDismissDirection(int position) {
//        //这里控制每张卡的支持滑动超过一定距离消失的方向
//        BaseCardItem item = mItems.get(position);
//        return item.dismissDir;
//    }
//
//    @Override
//    public boolean isFastDismissAllowed(int position) {
//        //这里控制每张卡的支持快速滑动消失的方向
//        BaseCardItem item = mItems.get(position);
//        return item.fastDismissAllowed;
//    }
//
//    @Override
//    public int getMaxRotation(int position) {
//        //这里控制每张卡的最大旋转角
//        BaseCardItem item = mItems.get(position);
//        return item.maxRotation;
//    }
}