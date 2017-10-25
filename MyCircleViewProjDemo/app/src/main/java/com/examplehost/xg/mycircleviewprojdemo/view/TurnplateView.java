package com.examplehost.xg.mycircleviewprojdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.examplehost.xg.mycircleviewprojdemo.R;

/**
 * Created by XG on 2017/10/25.
 * author by liuchao
 */

public class TurnplateView extends View implements OnTouchListener {
    private OnTurnplateListener onTurnplateListener;

    public void setOnTurnplateListener(OnTurnplateListener onTurnplateListener) {
        this.onTurnplateListener = onTurnplateListener;
    }

    /**
     * 画点的画笔
     */
    private Paint mPaint = new Paint();
    /**
     * 画圆的画笔
     */
    private Paint paintCircle = new Paint();
    /**
     * 小图图标数组
     */
    private Bitmap[] icons;
    /**
     * 大图图标数组
     */
    private Bitmap[] bigIcons;
    /**
     * 点对象数组
     */
    private Point[] points;
    /**
     * 图标总个数
     */
    private int ponit_num;
    /**
     * 圆心坐标
     */
    public int mPointX = 0, mPointY = 0;
    /**
     * 圆半径
     */
    private int mRadius = 0;
    /**
     * 图标间隔的角度
     */
    private int mDegreeDelta;
    /**
     * 换算缓存图标
     */
    private int tempDegree = 0;
    /**
     * 被选中的图标
     */
    private int chooseBtn;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 半径偏移量,达到圆圈比屏幕多一部分的效果
     */
    private int radiusOffset;
    /**
     * 背景圆的半径
     */
    public int backgroudCircleRadius;
    /**
     * 大图标的高
     */
    private int bigSize;
    /**
     * 箭头图片预留的高度
     */
    private int arrowOffset;
    /**
     * 箭头图片
     */
    private Bitmap arrowBitmap;
    /**
     * 圆圈的颜色
     */
    private int circleColor = R.color.light_yellow;
    /**
     * 控件距离上边缘的距离
     */
    private int marginTop = 40;

    public TurnplateView(Context context) {
        super(context);
        this.context = context;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(Color.WHITE);
        setBackgroundColor(Color.WHITE);
    }

    public TurnplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(Color.WHITE);
        setBackgroundColor(Color.WHITE);
    }

    public void initTurnPlateView(Bitmap[] icons, Bitmap[] bigIcons, int ponit_num, int px,
                                  int radius, int divideAngle, int radiusOffset, int arrowOffset) {
        this.arrowOffset = arrowOffset;
        this.ponit_num = ponit_num;
        this.icons = icons;
        this.bigIcons = bigIcons;
        this.mPointX = px;
        this.mPointY = px + radiusOffset + bigIcons[0].getHeight() + marginTop + arrowOffset;
        this.backgroudCircleRadius = radius + radiusOffset;
        this.mRadius = backgroudCircleRadius + arrowOffset + bigIcons[0].getHeight() / 2;
        this.mDegreeDelta = divideAngle;
        this.radiusOffset = radiusOffset;
        initPoints();
        computeCoordinates();
    }

    /**
     * 初始化每个点的坐标
     */
    private void initPoints() {
        points = new Point[ponit_num];
        Point point;
        int angle = -90;
        for (int index = 0; index < ponit_num; index++) {
            point = new Point();
            point.angle = angle;
            angle += mDegreeDelta;
            point.bitmap = icons[index];
            point.flag = index;
            points[index] = point;

        }
    }

    /**
     * 根据移动坐标计算每个点的角度
     *
     * @param x
     * @param y
     * @return
     */
    private boolean resetPointAngle(float x, float y) {
        int degree = computeMigrationAngle(x, y);
        float x1Devide = points[0].x - mPointX - 10;
        float y1Devide = points[0].y - mPointY;
        float x2Devide = points[ponit_num - 1].x - mPointX + 10;
        float y2Devide = points[ponit_num - 1].y - mPointY;
        if ((x1Devide >= 0) && (degree > 0) && (y1Devide < 0)) {
            return false;
        } else if ((x2Devide <= 0) && (degree < 0) && (y2Devide < 0)) {
            return false;
        }
        for (int index = 0; index < ponit_num; index++) {
            points[index].angle += degree;
            if (points[index].angle > 360) {
                points[index].angle -= 360;
            } else if (points[index].angle < 0) {
                points[index].angle += 360;
            }

        }
        return true;
    }

    /**
     * 根据移动的角度计算每个点的角度
     *
     * @param degree
     * @return
     */
    private boolean resetPointAngleDiff(int degree) {
        // 这里有10个像素的偏移量
        float x1Devide = points[0].x - mPointX - 10;
        float y1Devide = points[0].y - mPointY;
        float x2Devide = points[ponit_num - 1].x - mPointX + 10;
        float y2Devide = points[ponit_num - 1].y - mPointY;
        if ((x1Devide >= 0) && (degree > 0) && (y1Devide < 0)) {
            return false;
        } else if ((x2Devide <= 0) && (degree < 0) && (y2Devide < 0)) {
            return false;
        }
        for (int index = 0; index < ponit_num; index++) {
            points[index].angle += degree;
            if (points[index].angle > 360) {
                points[index].angle -= 360;
            } else if (points[index].angle < 0) {
                points[index].angle += 360;
            }

        }
        return true;
    }

    /**
     * 计算每个点的坐标
     */
    private void computeCoordinates() {
        Point point;
        for (int index = 0; index < ponit_num; index++) {
            point = points[index];
            point.x = mPointX + (float) (mRadius * Math.cos(point.angle * Math.PI / 180));
            point.y = mPointY + (float) (mRadius * Math.sin(point.angle * Math.PI / 180));
            point.x_c = mPointX + (point.x - mPointX) / 2;
            point.y_c = mPointY + (point.y - mPointY) / 2;
        }
    }

    /**
     * 计算每个点的角度
     *
     * @param x
     * @param y
     * @return
     */
    private int computeMigrationAngle(float x, float y) {
        int a = 0;
        float distance = (float) Math.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY) * (y - mPointY)));
        int degree = (int) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
        if (y < mPointY) {
            degree = -degree;
        }
        if (tempDegree != 0) {
            a = degree - tempDegree;
        }
        tempDegree = degree;
        return a;
    }

    /**
     * 计算触摸的位置与各个元点的距离 参数
     *
     * @param x
     * @param y
     */
    private void computeCurrentDistance(float x, float y) {
        for (Point point : points) {
            float distance = (float) Math.sqrt(((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y)));
            if (distance < 44) {
                chooseBtn = point.flag;
                resetChoiceIndex();
                break;
            }
        }
    }

    /**
     * 判断点击位置到item图标的距离
     *
     * @param x
     * @param y
     * @return
     */
    private boolean computeCurrentDistances(float x, float y) {
        for (Point point : points) {
            float distance = (float) Math.sqrt(((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y)));
            if (distance < 44) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /**
     * 判断点击图标
     *
     * @param event
     */
    private void switchScreen(MotionEvent event) {
        computeCurrentDistance(event.getX(), event.getY());
        onTurnplateListener.onPointTouch(chooseBtn);

    }

    /**
     * 手动设置被选中的图标
     *
     * @param chooseIndex
     */
    public void setChooseBn(int chooseIndex) {
        chooseBtn = chooseIndex;
        resetChoiceIndex();
        onTurnplateListener.onPointTouch(chooseBtn);
    }

    /**
     * 点击时间down的X坐标
     */
    private int downX;

    /**
     * 手势
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                boolean isDispatch = computeCurrentDistances(event.getX(), event.getY());
                if (!isDispatch) {
                    return false;
                } else {
                    downX = (int) event.getX();
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                if (!resetPointAngle(event.getX(), event.getY())) {
                    break;
                }
                computeCoordinates();
                getNearestIndex();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                //如果按下和抬起x距离小于100像素，认为是点击，否则是滑动
                if (Math.abs(downX - upX) < 100) {
                    // 点击执行
                    switchScreen(event);
                }

                // 滑动执行
                onTurnplateListener.onPointTouch(chooseBtn);
                tempDegree = 0;
                int diff = 270 - points[chooseBtn].angle;
                if (!resetPointAngleDiff(diff)) {
                    break;
                }
                computeCoordinates();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * 根据图标属性绘制图标
     */
    @Override
    public void onDraw(Canvas canvas) {
        // 画橙色的圈
        paintCircle.setColor(context.getResources().getColor(circleColor));
        canvas.drawCircle(mPointX, mPointY, backgroudCircleRadius, paintCircle);
        paintCircle.setColor(Color.WHITE);
        canvas.drawCircle(mPointX, mPointY, backgroudCircleRadius - 8, paintCircle);
        // 画箭头
        if (arrowBitmap != null) {
            canvas.drawBitmap(arrowBitmap, mPointX - arrowBitmap.getWidth() / 2,
                    (mPointY - backgroudCircleRadius) - arrowBitmap.getHeight(), null);
        }
        // canvas.drawLine(mPointX, 0, mPointX, mPointY, mPaint);
        for (int index = 0; index < ponit_num; index++) {
            if ((points[index].y - mPointY) < 0) {
                drawInCenter(canvas, index, points[index].x, points[index].y, points[index].flag);
            } else {
                continue;
            }
        }

    }

    /**
     * 根据图标属性绘制图标
     */
    void drawInCenter(Canvas canvas, int index, float left, float top, int flag) {
        canvas.drawPoint(left, top, mPaint);
        if (flag == chooseBtn) {
            canvas.drawBitmap(bigIcons[index], left - bigIcons[index].getWidth() / 2,
                    top - bigIcons[index].getHeight() / 2, null);
        } else {
            canvas.drawBitmap(icons[index], left - icons[index].getWidth() / 2, top - icons[index].getHeight() / 2,
                    null);
        }
    }

    /**
     * 图标属性类
     *
     * @author aa
     */
    class Point {

        int flag;
        Bitmap bitmap;
        int angle;
        float x;
        float y;
        float x_c;
        float y_c;
    }

    public static interface OnTurnplateListener {

        public void onPointTouch(int flag);

    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        return false;
    }

    /**
     * 计算距离圆心最近的图标
     */
    private void getNearestIndex() {
        int diff = Math.abs(points[0].angle - 270);
        for (int i = 0; i < ponit_num; i++) {
            if (diff > Math.abs(points[i].angle - 270) || diff == Math.abs(points[i].angle - 270)) {
                chooseBtn = points[i].flag;
                diff = Math.abs(points[i].angle - 270);
            }
        }
    }

    /**
     * 移动图标后复位
     */
    private void resetChoiceIndex() {
        int diff = 270 - points[chooseBtn].angle;
        if (!resetPointAngleDiff(diff)) {
            // 若手势超过了边界就强制复位
            int diffReset = 270 - points[0].angle;
            resetPointAngleDiff(diffReset);
        }

        computeCoordinates();
        invalidate();
    }

    /**
     * 设置箭头颜色和圈圈颜色
     */
    public void setTheme(Bitmap arraw, int circleColor) {
        this.arrowBitmap = arraw;
        this.circleColor = circleColor;
        invalidate();
    }

}
