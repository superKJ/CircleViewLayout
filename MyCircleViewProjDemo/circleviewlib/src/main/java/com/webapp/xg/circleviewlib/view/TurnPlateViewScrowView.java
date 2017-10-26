package com.webapp.xg.circleviewlib.view;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.webapp.xg.circleviewlib.R;
import com.webapp.xg.circleviewlib.util.Util;


public class TurnPlateViewScrowView extends ScrollView implements TurnplateView.OnTurnplateListener {
    /**
     * 上下文
     */
    private Context context;
    private int ponit_num = 11;
    private int offset = 50;
    private int angleDivide = 20;
    /**
     * 箭头的图片高度
     */
    private int arrowOffSet = 100;
    /**
     * 圆形布局
     */
    public TurnplateView turnplateView;
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
    /**
     * 圆盘位置改变回调
     */
    private PositionListener positionListener;

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
        positionListener.positionListenerMethod(flag);
    }

    public void initTurnPlateViewScrowView(Bitmap[] smallIcons, Bitmap[] bigIcons, PositionListener positionListener) {
        if (turnplateView == null) {
            return;
        }
        this.positionListener = positionListener;
        turnplateView.initTurnPlateView(smallIcons, bigIcons, ponit_num, Util.getDeviceWith((Activity) context) / 2,
                Util.getDeviceWith((Activity) context) / 2, angleDivide, offset, arrowOffSet);
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

    public int getFragmentLayoutId() {
        return R.id.sport_detail_fragment;
    }

    public interface PositionListener {
        public void positionListenerMethod(int position);
    }
}
