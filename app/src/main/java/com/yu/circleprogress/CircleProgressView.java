package com.yu.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created by yu on 2018/3/5.
 */

public class CircleProgressView extends View {

    private float currentDegree;

    public void setProgress(float progress) {
        if (progress > 1)
            this.progress = 1;
        else
            this.progress = progress;
    }

    private float progress;

    private static final float circleWidth = 20;
    private static final float circleStartDegree = 125;
    private static final float circleEndDegree = 290;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float scale = dm.scaledDensity;
        RectF rectf = new RectF(circleWidth / 2 * scale, circleWidth / 2 * scale, canvas.getWidth() - circleWidth / 2 * scale, canvas.getHeight() - circleWidth / 2 * scale);

        Paint bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(circleWidth * scale);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.parseColor("#f4f4f6"));

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(circleWidth * scale);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        int[] colors = {
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryLight),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent)
        };
        float[] positions = {
                0.0f,
                0.40f,
                0.80f,
                1.0f
        };

        SweepGradient sg = new SweepGradient(rectf.centerX(), rectf.centerY(), colors, positions);
        Matrix m = new Matrix();
        m.postRotate(90, rectf.centerX(), rectf.centerY());
        sg.setLocalMatrix(m);
        paint.setShader(sg);
        canvas.drawArc(rectf, circleStartDegree, circleEndDegree, false, bgPaint);
        canvas.drawArc(rectf, circleStartDegree, currentDegree, false, paint);
    }

    public void barAnimation() {
        BarAnimation barAnimation = new BarAnimation();
        barAnimation.setDuration(1000);
        barAnimation.setFillAfter(true);
        barAnimation.setInterpolator(new BounceInterpolator());
        this.startAnimation(barAnimation);
    }

    public class BarAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                currentDegree = (int) (circleEndDegree * progress * interpolatedTime);
                postInvalidate();
            } else {
                currentDegree = circleEndDegree * progress;
            }
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            currentDegree = 0;

        }
    }
}
