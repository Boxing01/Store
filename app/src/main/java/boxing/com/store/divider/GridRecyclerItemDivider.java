package boxing.com.store.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import boxing.com.store.utils.UIUtils;


/**
 * Grid RecyclerView 的分割线,水平分割线和垂直分割线高度一样
 * Created by liuyuli on 2017/6/1 0001.
 */

public class GridRecyclerItemDivider extends RecyclerView.ItemDecoration {
    private int height;
    private int colorRes;
    private Drawable mDivider;
    private boolean only;

    /**
     * 单色分隔条
     *
     * @param colorRes 分割条颜色
     * @param height   分隔条高度 单位px
     */
    public GridRecyclerItemDivider(@ColorRes int colorRes, int height) {
        only = true;
        this.colorRes = colorRes;
        this.height = height;
    }

    /**
     * 图片分隔条
     *
     * @param mDivider 分割条颜色
     * @param height   分隔条高度 单位px
     */
    public GridRecyclerItemDivider(Drawable mDivider, int height) {
        only = false;
        this.mDivider = mDivider;
        this.height = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    /**
     * @param parent
     * @return Grid 的列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 横向布局
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + height;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + height;

            if (only) {
                Paint paint = new Paint();
                paint.setColor(UIUtils.getColor(colorRes));
                c.drawRect(left, top, right, bottom, paint);
            } else {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    /**
     * 纵向布局
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + height;

            if (only) {
                Paint paint = new Paint();
                paint.setColor(UIUtils.getColor(colorRes));
                c.drawRect(left, top, right, bottom, paint);
            } else {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    private boolean isLastColum(RecyclerView parent, int position, int spanCount, int childCount) {
        // 如果是最后一列，则不需要绘制右边
        if ((position + 1) % spanCount == 0) {
            return true;
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int position, int spanCount, int childCount) {
        childCount = childCount - childCount % spanCount;
        // 如果是最后一行，则不需要绘制底部
        if (position >= childCount)
            return true;
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount();
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        int spanCount = getSpanCount(parent);
        if (isLastRaw(parent, position, spanCount, itemCount)) {
            // 如果是最后一行，则不需要绘制底部
            outRect.set(0, 0, height, 0);
        } else if (isLastColum(parent, position, spanCount, itemCount)) {
            // 如果是最后一列，则不需要绘制右边
            outRect.set(0, 0, 0, height);
        } else {
            outRect.set(0, 0, height, height);
        }
    }
}
