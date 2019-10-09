package com.mino.urltask5.ui.common.binding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mino.urltask5.R;

import static com.mino.urltask5.utils.Constants.URL_AVAILABLE;
import static com.mino.urltask5.utils.Constants.URL_LOADING;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class BindingAdaptors {

    @BindingAdapter("app:urlIconSrc")
    public static void loadUrlIcon(final ImageView imageView,
                                   final int availability) {

        switch (availability) {
            case URL_LOADING: {
                imageView.setImageResource(R.drawable.progress);
                break;
            }
            case URL_AVAILABLE: {
                imageView.setImageResource(R.drawable.available);
                break;
            }
            case URL_UNAVAILABLE: {
                imageView.setImageResource(R.drawable.unavailable);
                break;
            }
        }
    }

    @BindingAdapter(value = {"swipeEnabled", "drawableSwipeLeft", "drawableSwipeRight", "bgColorSwipeLeft", "bgColorSwipeRight", "onItemSwipeLeft", "onItemSwipeRight"}, requireAll = false)
    public static void setItemSwipeToRecyclerView(RecyclerView recyclerView, boolean swipeEnabled, Drawable drawableSwipeLeft, Drawable drawableSwipeRight, int bgColorSwipeLeft, int bgColorSwipeRight,
                                                  SwipeItemTouchHelperCallback.OnItemSwipeListener onItemSwipeLeft, SwipeItemTouchHelperCallback.OnItemSwipeListener onItemSwipeRight) {

        ItemTouchHelper.Callback swipeCallback = new SwipeItemTouchHelperCallback
                .Builder(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
                .bgColorSwipeLeft(bgColorSwipeLeft)
                .bgColorSwipeRight(bgColorSwipeRight)
                .drawableSwipeLeft(drawableSwipeLeft)
                .drawableSwipeRight(drawableSwipeRight)
                .setSwipeEnabled(swipeEnabled)
                .onItemSwipeLeftListener(onItemSwipeLeft)
                .onItemSwipeRightListener(onItemSwipeRight)
                .build();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
