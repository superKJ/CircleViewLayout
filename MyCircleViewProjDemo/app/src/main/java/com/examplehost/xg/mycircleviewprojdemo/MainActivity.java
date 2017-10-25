package com.examplehost.xg.mycircleviewprojdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.examplehost.xg.mycircleviewprojdemo.util.Util;
import com.examplehost.xg.mycircleviewprojdemo.view.TurnplateView;

public class MainActivity extends FragmentActivity implements TurnplateView.OnTurnplateListener {
    private int ponit_num = 11;
    private Bitmap[] smallIcons;
    private Bitmap[] bigIcons;
    private int offset;
    private int angleDivide;
    /**
     * 箭头的图片高度
     */
    private int arrowOffSet;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initCache();
        initView();
    }

    private void initCache() {
        smallIcons = new Bitmap[ponit_num];
        bigIcons = new Bitmap[ponit_num];
        //
        arrowOffSet = 100;
        angleDivide = 20;
        offset = 50;
        loadSmallBitmaps();
        loadBigBitmaps();
    }

    private void initView() {
        turnplateView = (TurnplateView) findViewById(R.id.sport_detail_page_turn_plate_view);
        turnplateView.initTurnPlateView(smallIcons, bigIcons, ponit_num, Util.getDeviceWith(this) / 2,
                Util.getDeviceWith(this) / 2, angleDivide, offset, arrowOffSet);
        // 设置主题橙色还是蓝色
        turnplateView.setTheme(Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_arrow_icon),
                R.color.light_yellow);
        turnplateView.setOnTurnplateListener(this);

        // 中间内容区域
        detailInfoLayout = (LinearLayout) findViewById(R.id.sport_detail_page_frag_layout);
        FrameLayout.LayoutParams lpfrag = (FrameLayout.LayoutParams) detailInfoLayout.getLayoutParams();
        // 内容显示区域的宽高为圆形视图半径的7/10
        lpfrag.width = (int) (turnplateView.backgroudCircleRadius * 2 / Math.sqrt(2));
        lpfrag.height = lpfrag.width;
//        turnplateView.backgroudCircleRadius * 2 * 7 / 10;
        lpfrag.setMargins(0, turnplateView.mPointY - lpfrag.width / 2, 0, 0);
        // turnplateView的布局必须设置高度，否则无法滚动
        turnplateViewLayout = (LinearLayout) findViewById(R.id.sport_detail_page_turnplateview_layout);
        FrameLayout.LayoutParams turnplateViewLayoutParams = (FrameLayout.LayoutParams) turnplateViewLayout
                .getLayoutParams();
        turnplateViewLayoutParams.height = turnplateView.mPointY + turnplateView.backgroudCircleRadius;
        turnplateView.setChooseBn(0);
    }

    /**
     * 加载小图标
     */
    public void loadSmallBitmaps() {

        smallIcons[0] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x0);
        smallIcons[1] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x1);
        smallIcons[2] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x2);
        smallIcons[3] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x3);
        smallIcons[4] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x4);
        smallIcons[5] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x5);
        smallIcons[6] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x6);
        smallIcons[7] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x7);
        smallIcons[8] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x8);
        smallIcons[9] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x9);
        smallIcons[10] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_x10);
    }

    /**
     * 加载大图标
     */
    public void loadBigBitmaps() {
        bigIcons[0] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d0);
        bigIcons[1] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d1);
        bigIcons[2] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d2);
        bigIcons[3] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d3);
        bigIcons[4] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d4);
        bigIcons[5] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d5);
        bigIcons[6] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d6);
        bigIcons[7] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d7);
        bigIcons[8] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d8);
        bigIcons[9] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d9);
        bigIcons[10] = Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_d10);
    }

    /**
     * 初始化界面和点击Item的时候都会回调这个方法
     */
    @Override
    public void onPointTouch(int flag) {
        if (flag > 6) {
            turnplateView.setTheme(
                    Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_arrow_icon_blue),
                    R.color.circle_blue);
            // 设置蓝色主题
        } else {
            turnplateView.setTheme(
                    Util.Drawable2Bitmap(this, R.mipmap.sport_detail_page_arrow_icon),
                    R.color.light_yellow);
        }
    }
}
