package com.mino.urltask5.ui.common.binding;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mino.urltask5.utils.ViewUtil;

public class SwipeItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable drawableLeft, drawableRight;
    private Paint paintLeft, paintRight;
    private OnItemSwipeListener onItemSwipeLeftListener, onItemSwipeRightListener;
    private boolean swipeEnabled;

    private SwipeItemTouchHelperCallback(final int dragDirs,
                                         final int swipeDirs) {

        super(dragDirs, swipeDirs);
    }

    private SwipeItemTouchHelperCallback(final Builder builder) {
        this(builder.dragDirs, builder.swipeDirs);
        setPaintColor(paintLeft = new Paint(Paint.ANTI_ALIAS_FLAG), builder.bgColorSwipeLeft);
        setPaintColor(paintRight = new Paint(Paint.ANTI_ALIAS_FLAG), builder.bgColorSwipeRight);
        drawableLeft = builder.drawableSwipeLeft;
        drawableRight = builder.drawableSwipeRight;
        swipeEnabled = builder.swipeEnabled;
        onItemSwipeLeftListener = builder.onItemSwipeLeftListener;
        onItemSwipeRightListener = builder.onItemSwipeRightListener;
    }

    private void setPaintColor(Paint paint, int color) {
        paint.setColor(color);
    }


    @Override public boolean isItemViewSwipeEnabled() {
        return swipeEnabled;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            onItemSwipeLeftListener.onItemSwiped(position);
        } else if (direction == ItemTouchHelper.RIGHT) {
            onItemSwipeRightListener.onItemSwiped(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if (dX > 0) {
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                RectF iconDest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                c.drawRect(background, paintLeft);
                c.drawBitmap(ViewUtil.getBitmap(drawableLeft), null, iconDest, paintLeft);
            } else {
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                RectF iconDest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                c.drawRect(background, paintRight);
                c.drawBitmap(ViewUtil.getBitmap(drawableRight), null, iconDest, paintRight);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnItemSwipeListener {
        void onItemSwiped(int position);
    }

    public static final class Builder {
        private int dragDirs, swipeDirs;
        private Drawable drawableSwipeLeft, drawableSwipeRight;
        private int bgColorSwipeLeft, bgColorSwipeRight;
        private OnItemSwipeListener onItemSwipeLeftListener, onItemSwipeRightListener;
        private boolean swipeEnabled;

        public Builder(int dragDirs, int swipeDirs) {
            this.dragDirs = dragDirs;
            this.swipeDirs = swipeDirs;
        }

        public Builder drawableSwipeLeft(Drawable val) {
            drawableSwipeLeft = val;
            return this;
        }

        public Builder drawableSwipeRight(Drawable val) {
            drawableSwipeRight = val;
            return this;
        }

        public Builder bgColorSwipeLeft(int val) {
            bgColorSwipeLeft = val;
            return this;
        }

        public Builder bgColorSwipeRight(int val) {
            bgColorSwipeRight = val;
            return this;
        }

        public Builder onItemSwipeLeftListener(OnItemSwipeListener val) {
            onItemSwipeLeftListener = val;
            return this;
        }

        public Builder onItemSwipeRightListener(OnItemSwipeListener val) {
            onItemSwipeRightListener = val;
            return this;
        }

        public Builder setSwipeEnabled(boolean val) {
            swipeEnabled = val;
            return this;
        }

        public SwipeItemTouchHelperCallback build() {
            return new SwipeItemTouchHelperCallback(this);
        }
    }
}