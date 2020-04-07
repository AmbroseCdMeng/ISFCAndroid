package com.maci.foxconn.utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DrawableUtil {
    /**
     * 0 LEFT
     * 1 TOP
     * 2 RIGHT
     * 3 BOTTOM
     */
    private final int LEFT = 0;
    private final int TOP = 1;
    private final int RIGHT = 2;
    private final int BOTTOM = 3;

    private OnDrawableListener listener;
    private View mView;

    public DrawableUtil(View view, OnDrawableListener listener) {
        mView = view;
        mView.setOnTouchListener(mOnTouchListener);
        this.listener = listener;
    }

    public interface OnDrawableListener {
        void onLeft(View view, Drawable left);

        void onRight(View view, Drawable right);

        void onTop(View view, Drawable top);

        void onBottom(View view, Drawable bottom);

    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (listener != null) {
                        /* 强转 TextView */
                        Drawable dLeft = ((EditText) mView).getCompoundDrawables()[LEFT];
                        if (dLeft != null && event.getRawX() <= (mView.getLeft() + dLeft.getBounds().width())) {
                            listener.onLeft(v, dLeft);
                            return true;
                        }

                        Drawable dRight = ((EditText) mView).getCompoundDrawables()[RIGHT];
                        if (dRight != null && event.getRawX() >= (mView.getLeft() + dRight.getBounds().width())) {
                            listener.onRight(v, dRight);
                            return true;
                        }
                    }
                    break;
            }
            return false;
        }
    };
}
