package com.mino.urltask5.ui.common.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.mino.urltask5.R;

import static com.mino.urltask5.utils.Constants.URL_AVAILABLE;
import static com.mino.urltask5.utils.Constants.URL_LOADING;
import static com.mino.urltask5.utils.Constants.URL_UNAVAILABLE;

public class BindingAdaptors {

    @BindingAdapter("app:urlIconSrc")
    public static void loadUrlIcon(final ImageView imageView,
                     final int availability) {

        switch (availability) {
            case URL_LOADING : {
                imageView.setImageResource(R.drawable.progress);
                break;
            }
            case URL_AVAILABLE : {
                imageView.setImageResource(R.drawable.available);
                break;
            }
            case URL_UNAVAILABLE : {
                imageView.setImageResource(R.drawable.unavailable);
                break;
            }
        }
    }
}
