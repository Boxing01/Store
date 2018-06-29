package boxing.com.store.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import boxing.com.store.utils.UIUtils;


/**
 * List RecyclerView 分割线
 * Created by liuyuli on 2017/6/1 0001.
 */

public class RecyclerItemDivider extends RecyclerView.ItemDecoration {
    private int orientation;
    private int height;
    private int colorRes;
    private Drawable mDivider;
    private boolean only;

    /**
     * 单色分隔条
     *
     * @param colorRes 分割条颜色
     * @param height   分隔条高度 单位px
     * @param orientation   方向
     */
    public RecyclerItemDivider(@ColorRes int colorRes, int height, int orientation) {
        only = true;
        this.colorRes = colorRes;
        this.height = height;
        this.orientation = orientation;
    }

    /**
     * 图片分隔条
     *
     * @param mDivider 分割条颜色
     * @param height   分隔条高度 单位px
     * @param orientation   方向
     */
    public RecyclerItemDivider(Drawable mDivider, int height, int orientation) {
        only = false;
        this.mDivider = mDivider;
        this.height = height;
        this.orientation = orientation;
    }


    /**
     * 纵向布局
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayout.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 纵向布局
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + height;

            if (only) {
//                单色分隔条
                Paint paint = new Paint();
                paint.setColor(UIUtils.getColor(colorRes));
                c.drawRect(left, top, right, bottom, paint);
            } else {
//                图片分隔条
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


    /**
     * 横向布局
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();

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

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount();
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (orientation == LinearLayout.VERTICAL) {
            if (position == itemCount - 1) {
                // 最后一行的分割线
                outRect.set(0, 0, 0, height);
            } else {
                outRect.set(0, 0, 0, height);
            }
        } else {
            if (position == itemCount - 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, height, 0);
            }
        }
    }
}
