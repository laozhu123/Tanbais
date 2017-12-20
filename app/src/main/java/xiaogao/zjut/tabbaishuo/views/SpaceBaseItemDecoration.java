package xiaogao.zjut.tabbaishuo.views;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Yefeng on 2017/8/29.
 * Modified by xxx
 */

public class SpaceBaseItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceBaseItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, view.getResources().getDisplayMetrics());
        int halfSpacing = spacing / 2;

        int childCount = parent.getChildCount();
        int childIndex = parent.getChildLayoutPosition(view);
        int spanCount = getTotalSpan(parent);
        int spanIndex = childIndex % spanCount;

    /* INVALID SPAN */
        if (spanCount < 1) {
            return;
        }

        outRect.top = halfSpacing;
        //        outRect.bottom = halfSpacing;
        outRect.left = halfSpacing;
        outRect.right = 0;

        if (isTopEdge(childIndex, spanCount)) {
            outRect.top = 0;
        }

        if (isLeftEdge(spanIndex)) {
            outRect.left = 0;
            outRect.right = 0;
        }

        if (isRightEdge(spanIndex, spanCount)) {
            outRect.right = 0;
            outRect.left = spacing;
        }

        if (isBottomEdge(childIndex, childCount, spanCount)) {
            outRect.bottom = 0;
        }
    }

    private int getTotalSpan(RecyclerView parent) {
        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
        }

        return -1;
    }

    private boolean isLeftEdge(int spanIndex) {

        return spanIndex == 0;
    }

    private boolean isRightEdge(int spanIndex, int spanCount) {

        return spanIndex == spanCount - 1;
    }

    private boolean isTopEdge(int childIndex, int spanCount) {

        return childIndex < spanCount;
    }

    private boolean isBottomEdge(int childIndex, int childCount, int spanCount) {

        return childIndex >= childCount - spanCount;
    }

}
