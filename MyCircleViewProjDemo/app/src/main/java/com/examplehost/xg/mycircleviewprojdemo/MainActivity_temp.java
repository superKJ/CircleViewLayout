package com.examplehost.xg.mycircleviewprojdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.examplehost.xg.mycircleviewprojdemo.util.BitmapUtil;
import com.examplehost.xg.mycircleviewprojdemo.view.TurnplateView;

import java.util.HashMap;

public class MainActivity_temp extends FragmentActivity implements TurnplateView.OnTurnplateListener, View.OnClickListener {
    private int ponit_num;
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
     * 左侧箭头
     */
    private LinearLayout leftArrowIcon;
    /**
     * 右侧箭头
     */
    private LinearLayout rightArrowIcon;
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
    /**
     * 返回按钮
     */
    private ImageView backUpBt;
    /**
     * 大人项目与位置的对应关系
     */
    private static HashMap<Integer, Integer> indexMap_Man = new HashMap<Integer, Integer>();
    /**
     * 小孩项目与位置的对应关系
     */
    private static HashMap<Integer, Integer> indexMap_Child = new HashMap<Integer, Integer>();
    /**
     * 获取历史记录数据缓存
     */
    public static HashMap<String, String> getHisDataCache = new HashMap<String, String>();
    /**
     * 是大人还是小孩的标识。1大人0小孩
     */
    private int isChildOrManTag = -1;
    /**
     * 页面的主题是黄色还是蓝色 1黄色2蓝色
     */
    private int theme;
    /**
     * 箭头能点击的总数量
     */
    private int arrowCount;
    /**
     * 当前箭头的位置
     */
    private int arrowIndex;

    static {
        // 小孩
        // 肺活量
        indexMap_Child.put(2, 0);
        // 仰卧起坐
        indexMap_Child.put(5, 1);
        // 立定跳远
        indexMap_Child.put(8, 2);
        // 握力
        indexMap_Child.put(3, 3);
        // BMI
        indexMap_Child.put(1, 4);
        // 跳绳
        indexMap_Child.put(7, 5);
        // 坐位体前屈
        indexMap_Child.put(4, 6);
        // 卷腹
        indexMap_Child.put(101, 7);
        // 平板支撑
        indexMap_Child.put(103, 8);
        // 俯卧撑
        indexMap_Child.put(102, 9);
        // 深蹲
        indexMap_Child.put(100, 10);

        // 大人
        indexMap_Man.put(101, 0);
        // 平板支撑
        indexMap_Man.put(103, 1);
        // 俯卧撑
        indexMap_Man.put(102, 2);
        // 深蹲
        indexMap_Man.put(100, 3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initCache();
        initView();
        initData();
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

        leftArrowIcon = (LinearLayout) findViewById(R.id.sport_detail_page_left_arrow);
        leftArrowIcon.setOnClickListener(this);
        rightArrowIcon = (LinearLayout) findViewById(R.id.sport_detail_page_right_arrow);
        rightArrowIcon.setOnClickListener(this);
        // 设置箭头位置
        RelativeLayout.LayoutParams lpleft = (RelativeLayout.LayoutParams) leftArrowIcon.getLayoutParams();
        lpleft.setMargins(0, turnplateView_Y_center, 0, 0);
        leftArrowIcon.setLayoutParams(lpleft);
        // 设置箭头位置
        RelativeLayout.LayoutParams lpright = (RelativeLayout.LayoutParams) rightArrowIcon.getLayoutParams();
        lpright.setMargins(0, turnplateView_Y_center, 0, 0);
        rightArrowIcon.setLayoutParams(lpright);
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
    }

    private void initData() {
        HashMap<Integer, Integer> dataMap = isChildOrManTag == 0 ? indexMap_Child : indexMap_Man;
        try {
            turnplateView.setChooseBn(0);
        } catch (Exception e) {
        }

    }

    private void changeFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.sport_detail_fragment, f);
        ft.commit();
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
        HashMap<Integer, Integer> dataMap = isChildOrManTag == 0 ? indexMap_Child : indexMap_Man;
        if (ponit_num == 11 && flag > 6) {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon_blue)),
                    R.color.circle_blue);
            leftArrowIcon.setBackgroundDrawable(
                    getResources().getDrawable(R.mipmap.sport_detail_page_left_arrow_icon_blue));
            rightArrowIcon.setBackgroundDrawable(
                    getResources().getDrawable(R.mipmap.sport_detail_page_right_arrow_icon_blue));
            // 设置蓝色主题
            theme = 2;
        } else if (ponit_num == 4) {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon_blue)),
                    R.color.circle_blue);
            leftArrowIcon.setBackgroundDrawable(
                    getResources().getDrawable(R.mipmap.sport_detail_page_left_arrow_icon_blue));
            rightArrowIcon.setBackgroundDrawable(
                    getResources().getDrawable(R.mipmap.sport_detail_page_right_arrow_icon_blue));
            // 设置蓝色主题
            theme = 2;
        } else {
            turnplateView.setTheme(
                    getDrawableBitmap(getResources().getDrawable(R.mipmap.sport_detail_page_arrow_icon)),
                    R.color.light_yellow);
            leftArrowIcon
                    .setBackgroundDrawable(getResources().getDrawable(R.mipmap.sport_detail_page_left_arrow_icon));
            rightArrowIcon
                    .setBackgroundDrawable(getResources().getDrawable(R.mipmap.sport_detail_page_right_arrow_icon));
            // 设置橙色主题
            theme = 1;
        }
        // 设置箭头数量
        // 如果是大人，能点击三次
        if (isChildOrManTag == 1) {
            // 下标0，1，2
            arrowCount = 2;
        }
        // 如果是小孩，在黄色区域，能点击二次
        else if (isChildOrManTag == 0 && theme == 1) {
            // 下标0，1
            arrowCount = 1;
        }
        // 如果是小孩，在黄色区域，能点击三次
        else if (isChildOrManTag == 0 && theme == 2) {
            arrowCount = 2;
        } else {
            arrowCount = 1;
        }
        arrowIndex = 0;
        changeArrow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sport_detail_page_left_arrow:
                arrowIndex--;
                changeArrow();

                break;
            case R.id.sport_detail_page_right_arrow:
                arrowIndex++;
                changeArrow();
                break;
            default:
                break;
        }
    }

    private void changeArrow() {
        if (arrowIndex == 0) {
            leftArrowIcon.setVisibility(View.GONE);
            rightArrowIcon.setVisibility(View.VISIBLE);
        } else if (arrowIndex == arrowCount) {
            leftArrowIcon.setVisibility(View.VISIBLE);
            rightArrowIcon.setVisibility(View.GONE);
        } else {
            leftArrowIcon.setVisibility(View.VISIBLE);
            rightArrowIcon.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 页面关闭一定要清空缓存
     */
    @Override
    protected void onStop() {
        super.onStop();
        getHisDataCache.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
