package com.examplehost.xg.mycircleviewprojdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.examplehost.xg.mycircleviewprojdemo.util.Util;
import com.examplehost.xg.mycircleviewprojdemo.view.TurnPlateViewScrowView;

public class MainActivity extends FragmentActivity {
    private int ponit_num = 11;
    private Bitmap[] smallIcons;
    private Bitmap[] bigIcons;

    private TurnPlateViewScrowView turnPlateViewScrowView;

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
        loadSmallBitmaps();
        loadBigBitmaps();
    }

    private void initView() {
        turnPlateViewScrowView = (TurnPlateViewScrowView) findViewById(R.id.activity_main_page_turnplatescrowwview);
        turnPlateViewScrowView.initTurnPlateViewScrowView(smallIcons, bigIcons);
        turnPlateViewScrowView.setChoosenPosition(2);
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
}
