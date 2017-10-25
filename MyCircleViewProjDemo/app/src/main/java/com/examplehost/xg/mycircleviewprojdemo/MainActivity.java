package com.examplehost.xg.mycircleviewprojdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.examplehost.xg.mycircleviewprojdemo.util.BitmapUtil;
import com.examplehost.xg.mycircleviewprojdemo.view.TurnplateView;

public class MainActivity extends FragmentActivity implements TurnplateView.OnTurnplateListener {
    private int ponit_num = 11;
    private Bitmap[] smallIcons;
    private Bitmap[] bigIcons;
    private int smallSize;
    private int bigSize;
    private int offset;
    private int angleDivide;
    /**
     * 控件距离上边缘的距离
     */
    private int marginTop;
    /**
     * 箭头的图片高度
     */
    private int arrowOffSet;
    /**
     * 圆形布局
     */
    private TurnplateView turnplateView;
    /**
     * turnplateView的圆心Y坐标
     */
    private int turnplateView_Y_center;
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
        initCache();
        initView();
    }

    private void initCache() {
        smallIcons = new Bitmap[ponit_num];
        bigIcons = new Bitmap[ponit_num];
        //
        arrowOffSet = 100;
        marginTop = 40;
        angleDivide = 20;
        offset = 50;
        loadSmallBitmaps();
        loadBigBitmaps();
        smallSize = smallIcons[0].getHeight();
        bigSize = bigIcons[0].getHeight();
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        setContentView(R.layout.activity_main);
        turnplateView = (TurnplateView) findViewById(R.id.sport_detail_page_turn_plate_view);
        turnplateView_Y_center = width / 2 + offset + bigSize + marginTop + arrowOffSet;
        turnplateView.initTurnPlateView(smallIcons, bigIcons, ponit_num, smallSize, bigSize, width / 2,
                turnplateView_Y_center, width / 2, angleDivide, offset, arrowOffSet);
        // 设置主题橙色还是蓝色
        turnplateView.setTheme(getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon)),
                R.color.light_yellow);
        turnplateView.setOnTurnplateListener(this);

        // 中间内容区域
        detailInfoLayout = (LinearLayout) findViewById(R.id.sport_detail_page_frag_layout);
        RelativeLayout.LayoutParams lpfrag = (RelativeLayout.LayoutParams) detailInfoLayout.getLayoutParams();
        // 内容显示区域的宽高为圆形视图半径的7/10
        lpfrag.width = turnplateView.backgroudCircleRadius * 2 * 7 / 10;
        lpfrag.height = turnplateView.backgroudCircleRadius * 2 * 7 / 10;
        lpfrag.setMargins(0, turnplateView_Y_center - lpfrag.width / 2 - 40, 0, 0);
        // turnplateView的布局必须设置高度，否则无法滚动
        turnplateViewLayout = (LinearLayout) findViewById(R.id.sport_detail_page_turnplateview_layout);
        FrameLayout.LayoutParams turnplateViewLayoutParams = (FrameLayout.LayoutParams) turnplateViewLayout
                .getLayoutParams();
        turnplateViewLayoutParams.height = turnplateView_Y_center + turnplateView.backgroudCircleRadius + 50;
        turnplateView.setChooseBn(0);
    }

    /**
     * 加载小图标
     */
    public void loadSmallBitmaps() {
        Resources r = getResources();

        smallIcons[0] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x0, r);
        smallIcons[1] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x1, r);
        smallIcons[2] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x2, r);
        smallIcons[3] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x3, r);
        smallIcons[4] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x4, r);
        smallIcons[5] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x5, r);
        smallIcons[6] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x6, r);
        smallIcons[7] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x7, r);
        smallIcons[8] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x8, r);
        smallIcons[9] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x9, r);
        smallIcons[10] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_x10, r);
    }

    /**
     * 加载大图标
     */
    public void loadBigBitmaps() {
        Resources r = getResources();
        bigIcons[0] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d0, r);
        bigIcons[1] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d1, r);
        bigIcons[2] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d2, r);
        bigIcons[3] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d3, r);
        bigIcons[4] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d4, r);
        bigIcons[5] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d5, r);
        bigIcons[6] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d6, r);
        bigIcons[7] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d7, r);
        bigIcons[8] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d8, r);
        bigIcons[9] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d9, r);
        bigIcons[10] = BitmapUtil.Drawable2Bitmap(R.mipmap.sport_detail_page_d10, r);
    }

    private Bitmap getDrawableBitmap(Drawable d) {
        BitmapDrawable bd_b = (BitmapDrawable) d;
        Bitmap b = bd_b.getBitmap();
        return b;
    }

    /**
     * 初始化界面和点击Item的时候都会回调这个方法
     */
    @Override
    public void onPointTouch(int flag) {
        if (ponit_num == 11 && flag > 6) {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon_blue)),
                    R.color.circle_blue);
            // 设置蓝色主题
        } else if (ponit_num == 4) {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon_blue)),
                    R.color.circle_blue);

        } else {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon)),
                    R.color.light_yellow);
        }
    }
}
