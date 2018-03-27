package com.test.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.test.view.R;


/**
 * author：WangLei
 * date:2018/3/20.
 * QQ:619321796
 * 涟漪View
 */

public class RippleView extends View {
    /**
     * 三个圆的画笔
     */
    private Paint mLinePaint1;
    private Paint mLinePaint2;
    private Paint mLinePaint3;
    /**
     * 最内圆的坐标
     */
    private RectF mRectF1 = new RectF();
    /**
     * 第二个圆
     */
    private RectF mRectF2 = new RectF();
    /**
     * 最外圆
     */
    private RectF mRectF3 = new RectF();
    /**
     * 动画
     */
    private RippleAnimation rippleAnimation;
    /**
     * 外面两个圆绘制进度
     */
    private float progress = 0f;
    /**
     * view的宽度
     */
    private int mWidth = 0;

    public class RippleAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            progress = interpolatedTime;
            postInvalidate();
        }
    }

    public RippleView(Context context) {
        super(context);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化画笔和动画
     */
    private void initView() {
        initPaint();
        rippleAnimation = new RippleAnimation();
        rippleAnimation.setRepeatCount(ValueAnimator.INFINITE);//动画模式无限循环
        rippleAnimation.setRepeatMode(ValueAnimator.RESTART);
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        rippleAnimation.setDuration(1000);
        this.startAnimation(rippleAnimation);
    }

    /**
     * 初始化三个圆对应的画笔颜色、线条宽度
     */
    private void initPaint() {
        mLinePaint1 = new Paint();
        mLinePaint1.setStyle(Paint.Style.STROKE);
        mLinePaint1.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint1.setAntiAlias(true);
        mLinePaint1.setColor(getResources().getColor(R.color.color_ffffffff));
        mLinePaint1.setStrokeWidth(getResources().getDimension(R.dimen.x5));

        mLinePaint2 = new Paint();
        mLinePaint2.setStyle(Paint.Style.STROKE);
        mLinePaint2.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint2.setAntiAlias(true);
        mLinePaint2.setColor(getResources().getColor(R.color.color_99ffffff));
        mLinePaint2.setStrokeWidth(getResources().getDimension(R.dimen.x2));

        mLinePaint3 = new Paint();
        mLinePaint3.setStyle(Paint.Style.STROKE);
        mLinePaint3.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint3.setAntiAlias(true);
        mLinePaint3.setColor(getResources().getColor(R.color.color_3dffffff));
        mLinePaint3.setStrokeWidth(getResources().getDimension(R.dimen.x1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int middle = mWidth / 2;//获取View的中心点
        float radius175 = getResources().getDimension(R.dimen.x175);//第一个圆的半径175px
        float radius80 = getResources().getDimension(R.dimen.x80);//第二个圆距离第一个圆的半径80px
        float radius160 = getResources().getDimension(R.dimen.x160);//第三个圆距离第一个圆的半径80px

        float leftAndTop = middle - radius175;//第一个圆左 上 边的坐标
        float rightAndBottom = middle + radius175;//第一个圆右 下 边的坐标

        float leftAndTop_1 = leftAndTop - radius80 * progress;//第二个圆左 上 边的坐标 根据progress进度动态绘制
        float rightAndBottom_1 = rightAndBottom + radius80 * progress;//第二个圆右 下 边的坐标

        float leftAndTop_2 = leftAndTop - radius160 * progress;//第三个圆左 上 边的坐标 根据progress进度动态绘制
        float rightAndBottom_2 = rightAndBottom + radius160 * progress;//第三个圆右 下 边的坐标

        mRectF1.set(leftAndTop, leftAndTop, rightAndBottom, rightAndBottom);
        mRectF2.set(leftAndTop_1, leftAndTop_1, rightAndBottom_1, rightAndBottom_1);
        mRectF3.set(leftAndTop_2, leftAndTop_2, rightAndBottom_2, rightAndBottom_2);

        mLinePaint2.setAlpha((int) (255 - progress * 255));
        mLinePaint3.setAlpha((int) (255 - progress * 255));
        canvas.drawArc(mRectF1, 0, 359, false, mLinePaint1);
        canvas.drawArc(mRectF2, 0, 359, false, mLinePaint2);
        canvas.drawArc(mRectF3, 0, 359, false, mLinePaint3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(mWidth, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
    }

}
