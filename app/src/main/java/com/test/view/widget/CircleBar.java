package com.test.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.test.view.R;
import com.test.view.util.ViewUtil;


public class CircleBar extends View {

    private RectF mColorWheelRectangle = new RectF();//定义一个矩形,包含矩形的四个单精度浮点坐标
    private Paint mColorWheelPaint;//进度条的画笔
    private Paint mColorWheelPaintCentre;
    private Paint mTextPaint;
    private float circleStrokeWidth;
    private float circleBgStrokeWidth;
    private float mSweepAnglePer;
    private int progress = 0;
    private BarAnimation anim;
    private int stepnumbermax = 100;// 默认最大时间

    private Context context;

    private boolean showProgressTxt = true;
    //    是否是正向
    private boolean forward;
    //    开始绘制的角度
    private int startAngle;

    public CircleBar(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mColorWheelPaint = new Paint();
        mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心,只绘制轮廓线
        mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
        mColorWheelPaint.setAntiAlias(true);// 去锯齿
        mColorWheelPaint.setColor(getResources().getColor(R.color.color_ffd0423d));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);// 空心,只绘制轮廓线
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
        mTextPaint.setAntiAlias(true);// 去锯齿
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.x28));
        mTextPaint.setColor(getResources().getColor(R.color.color_ff2d2e35));

        mColorWheelPaintCentre = new Paint();
        mColorWheelPaintCentre.setColor(getResources().getColor(R.color.color_ffe1e1e1));
        mColorWheelPaintCentre.setStyle(Paint.Style.STROKE);
        mColorWheelPaintCentre.setStrokeCap(Paint.Cap.ROUND);
        mColorWheelPaintCentre.setAntiAlias(true);

        anim = new BarAnimation();
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleBar);
            int contentColor = ta.getColor(R.styleable.CircleBar_CircleBar_color, 0);
            progress = ta.getInt(R.styleable.CircleBar_CircleBar_progress, 0);
            showProgressTxt = ta.getBoolean(R.styleable.CircleBar_CircleBar_showProgress_txt, true);
            circleStrokeWidth = ta.getDimension(R.styleable.CircleBar_CircleBar_StrokeWidth, getResources().getDimension(R.dimen.x8));
            circleBgStrokeWidth = ta.getDimension(R.styleable.CircleBar_CircleBar_bg_StrokeWidth, getResources().getDimension(R.dimen.x3));
            int StrokeColorBg = ta.getColor(R.styleable.CircleBar_CircleBar_bg_StrokeColor, 0);
            forward = ta.getBoolean(R.styleable.CircleBar_CircleBar_forward, false);
            startAngle = ta.getInt(R.styleable.CircleBar_CircleBar_start_angle, 0);
            if (StrokeColorBg != 0) {
                mColorWheelPaintCentre.setColor(StrokeColorBg);
            }
            float x = 360 / 100f;
            mSweepAnglePer = progress * x;
            if (contentColor != 0) {
                mColorWheelPaint.setColor(contentColor);
            }
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mColorWheelRectangle, 0, 359, false, mColorWheelPaintCentre);
        if (!forward) {
            canvas.drawArc(mColorWheelRectangle, startAngle, -mSweepAnglePer, false, mColorWheelPaint);
        } else {
            canvas.drawArc(mColorWheelRectangle, startAngle, mSweepAnglePer, false, mColorWheelPaint);
        }
        if (showProgressTxt) {
            int width = getWidth();
            int height = getHeight();
            int x = (int) (mSweepAnglePer / 360 * 100);
            String value = x + "%";
            float leftTxtHeight = Math.abs(ViewUtil.getFontHeight(mTextPaint)) / 2;
            float textWidth = Math.abs(mTextPaint.measureText(value)) / 2;
            canvas.drawText(value, width / 2 - textWidth, height / 2 + leftTxtHeight, mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
        float pressExtraStrokeWidth = Textscale(2, min);// 圆弧离矩形的距离
        mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
                circleStrokeWidth + pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth);// 设置矩形
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
        mColorWheelPaintCentre.setStrokeWidth(circleBgStrokeWidth);
    }

    /**
     * 进度条动画
     */
    public class BarAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                mSweepAnglePer = interpolatedTime * progress * 360 / stepnumbermax;
            } else {
                mSweepAnglePer = progress * 360 / stepnumbermax;
            }
            postInvalidate();
        }
    }

    /**
     * 根据控件的大小改变绝对位置的比例
     */
    public float Textscale(float n, float m) {
        return n / 500 * m;
    }

    /**
     * 更新步数和设置一圈动画时间
     */
    public void update(int progress, int time) {
        this.progress = progress;
        anim.setDuration(time);
        //setAnimationTime(time);
        this.startAnimation(anim);
    }

    /**
     * 设置最大值
     */
    public void setMaxstepnumber(int Maxstepnumber) {
        stepnumbermax = Maxstepnumber;
    }

    /**
     * 设置进度条颜色
     */
    public void setColor(int red, int green, int blue) {
        mColorWheelPaint.setColor(Color.rgb(red, green, blue));
    }

    public void setBackgroundColor(int color) {
        mColorWheelPaintCentre.setColor(color);
    }

    public void setColor(int color) {
        mColorWheelPaint.setColor(color);
    }

    public void setShader(int[] color) {
        Shader s = new SweepGradient(0, 0, color, null);
        mColorWheelPaint.setShader(s);
    }

    /**
     * 设置动画时间
     */
    public void setAnimationTime(int time) {
        anim.setDuration(time * progress / stepnumbermax);// 按照比例设置动画执行时间
        this.startAnimation(anim);
    }

}
