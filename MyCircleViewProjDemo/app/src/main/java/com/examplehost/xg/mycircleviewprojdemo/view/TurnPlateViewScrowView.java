package com.examplehost.xg.mycircleviewprojdemo.view;

/**
 * Created by XG on 2017/10/25.
 * author by liuchao
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class TurnPlateViewScrowView extends ScrollView {

    public TurnPlateViewScrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
