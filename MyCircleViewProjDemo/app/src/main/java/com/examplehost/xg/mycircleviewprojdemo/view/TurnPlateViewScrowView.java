package com.examplehost.xg.mycircleviewprojdemo.view;

/**
 * Created by XG on 2017/10/25.
 * author by liuchao
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.examplehost.xg.mycircleviewprojdemo.R;
import com.examplehost.xg.mycircleviewprojdemo.util.Util;

public class TurnPlateViewScrowView extends ScrollView implements TurnplateView.OnTurnplateListener {
    /**
     * 上下文
     */
    private Context context;
    private int ponit_num = 11;
    private Bitmap[] smallIcons;
    private Bitmap[] bigIcons;
    private int offset = 50;
    private int angleDivide = 20;
    /**
     * 箭头的图片高度
     */
    private int arrowOffSet = 100;
    /**
     * 圆形布局
     */
    private TurnplateView turnplateView;
    /**
     * 中间显示内容区域的布局
     */
    private LinearLayout detailInfoLayout;
    /**
     * turnplateview的布局
     */
    private LinearLayout turnplateViewLayout;
    /**
     * 内容布局
     */
    private View contenView;

    public TurnPlateViewScrowView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TurnPlateViewScrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        contenView = LayoutInflater.from(context).inflate(R.layout.turn_plate_view_scrow_view_layout, null);
        addView(contenView);
        //圆环布局
        turnplateView = (TurnplateView) contenView.findViewById(R.id.sport_detail_page_turn_plate_view);
        // 中间内容区域
        detailInfoLayout = (LinearLayout) contenView.findViewById(R.id.sport_detail_page_frag_layout);
        turnplateView.setOnTurnplateListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void onPointTouch(int flag) {
        if (flag > 6) {
            turnplateView.setTheme(
                    Util.Drawable2Bitmap(context, R.mipmap.sport_detail_page_arrow_icon_blue),
                    R.color.circle_blue);
            // 设置蓝色主题
        } else {
            turnplateView.setTheme(
                    Util.Drawable2Bitmap(context, R.mipmap.sport_detail_page_arrow_icon),
                    R.color.light_yellow);
        }
    }

    public void initTurnPlateViewScrowView(Bitmap[] smallIcons, Bitmap[] bigIcons) {
        if (turnplateView == null) {
            return;
        }
        turnplateView.initTurnPlateView(smallIcons, bigIcons, ponit_num, Util.getDeviceWith((Activity) context) / 2,
                Util.getDeviceWith((Activity) context) / 2, angleDivide, offset, arrowOffSet);
        // 设置主题橙色还是蓝色
        turnplateView.setTheme(Util.Drawable2Bitmap((Activity) context, R.mipmap.sport_detail_page_arrow_icon),
                R.color.light_yellow);
        //根据图片大小重新设置布局大小
        FrameLayout.LayoutParams lpfrag = (FrameLayout.LayoutParams) detailInfoLayout.getLayoutParams();
        // 内容显示区域的宽高为圆形视图半径的7/10
        lpfrag.width = (int) (turnplateView.backgroudCircleRadius * 2 / Math.sqrt(2));
        lpfrag.height = lpfrag.width;
        lpfrag.setMargins(0, turnplateView.mPointY - lpfrag.width / 2, 0, 0);
        // turnplateView的布局必须设置高度，否则无法滚动
        turnplateViewLayout = (LinearLayout) findViewById(R.id.sport_detail_page_turnplateview_layout);
        FrameLayout.LayoutParams turnplateViewLayoutParams = (FrameLayout.LayoutParams) turnplateViewLayout
                .getLayoutParams();
        turnplateViewLayoutParams.height = turnplateView.mPointY + turnplateView.backgroudCircleRadius;

    }

    public void setChoosenPosition(int position) {
        if (turnplateView != null) {
            turnplateView.setChooseBn(position);
        }
    }
}
