package android.slc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

/**
 * @author slc
 * @date 2020-07-07 17:04
 */
public class NoClickTextInputEditText extends TextInputEditText {

    public NoClickTextInputEditText(@NonNull Context context) {
        super(context);
    }

    public NoClickTextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClickTextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isClickable() && super.onTouchEvent(event);
    }
}
