package com.javayhu.kiss.views.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * 背景为圆角矩形的Span
 * <p>
 * Created by javayhu on 2017/3/2.
 */
public class RoundedBackgroundSpan extends ReplacementSpan {

    private int mBackgroundColor = Color.BLACK;
    private int mTextColor = Color.WHITE;
    private int mCornerRadius = 10;

    public RoundedBackgroundSpan(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public RoundedBackgroundSpan(int backgroundColor, int textColor, int cornerRadius) {
        this.mTextColor = textColor;
        this.mCornerRadius = cornerRadius;
        this.mBackgroundColor = backgroundColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return 0;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + paint.measureText(text, start, end), bottom);
        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, mCornerRadius, mCornerRadius, paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

}
