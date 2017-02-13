package com.javayhu.kiss.views.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * BadgeView base on android (
 * 相对简易的badgeview，支持绑定到任意view，但是不支持拖拽消除 (2-1)
 * <p>
 * 1.如果要支持拖拽消除，使用DraggableBadgeView，它是另一个版本的badgeview (1-1)
 * 2.如果数字是几位数，例如14，DraggableBadgeView默认是圆矩形，BadgeView显示出来的制定的形状，例如圆形
 * 3.在某些情况下BadgeView会显示在其他view的下面，例如给button设置BadgeView的时候BadgeView是在下面！这个是bug
 * <p>
 * https://github.com/AlexLiuSheng/BadgeView
 * <p>
 * Updated by hujiawei on 2017/2/12.
 * Created by Allen Liu on 2016/7/15.
 */
public class BadgeView extends View {
    /**
     * 数字paint
     */
    private Paint numberPaint;
    /**
     * 背景Paint
     */
    private Paint backgroundPaint;

    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_RECTANGLE = 2;
    public static final int SHAPE_OVAL = 3;
    public static final int SHAPE_ROUND_RECTANGLE = 4;
    public static final int SHAPE_SQUARE = 5;

    private int currentShape = SHAPE_CIRCLE;
    private int defaultTextColor = Color.WHITE;
    private int defaultTextSize;
    private int defaultBackgroundColor = Color.RED;

    private String showText = "";
    private boolean hasBind = false;
    private int verticalSpace = 0;
    private int horizontalSpace = 0;
    private int badgeGravity = Gravity.END | Gravity.TOP;

    public BadgeView(Context context) {
        super(context);
        init(context);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        defaultTextSize = dip2px(context, 1);

        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(defaultTextColor);
        numberPaint.setStyle(Paint.Style.FILL);
        numberPaint.setTextSize(defaultTextSize);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(defaultBackgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = badgeGravity;
        setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
        float textH = fontMetrics.descent - fontMetrics.ascent;
        switch (currentShape) {
            case SHAPE_CIRCLE:
                canvas.drawCircle(getMeasuredWidth() / 2f, getMeasuredHeight() / 2f, getMeasuredWidth() / 2, backgroundPaint);
                canvas.drawText(showText, getMeasuredWidth() / 2f, getMeasuredHeight() / 2f + (textH / 2f - fontMetrics.descent), numberPaint);
                break;
            case SHAPE_OVAL:
                canvas.drawOval(rectF, backgroundPaint);
                canvas.drawText(showText, getMeasuredWidth() / 2f, getMeasuredHeight() / 2f + (textH / 2f - fontMetrics.descent), numberPaint);
                break;
            case SHAPE_RECTANGLE:
                canvas.drawRect(rectF, backgroundPaint);
                canvas.drawText(showText, getMeasuredWidth() / 2f, getMeasuredHeight() / 2f + (textH / 2f - fontMetrics.descent), numberPaint);
                break;
            case SHAPE_SQUARE:
                int sideLength = Math.min(getMeasuredHeight(), getMeasuredWidth());
                RectF squareF = new RectF(0, 0, sideLength, sideLength);
                canvas.drawRect(squareF, backgroundPaint);
                canvas.drawText(showText, sideLength / 2f, sideLength / 2f + (textH / 2f - fontMetrics.descent), numberPaint);
                break;
            case SHAPE_ROUND_RECTANGLE:
                canvas.drawRoundRect(rectF, dip2px(getContext(), 5), dip2px(getContext(), 5), backgroundPaint);
                canvas.drawText(showText, getMeasuredWidth() / 2f, getMeasuredHeight() / 2f + (textH / 2f - fontMetrics.descent), numberPaint);
                break;
        }
    }

    private int dip2px(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public BadgeView setShape(int shape) {
        currentShape = shape;
        invalidate();
        return this;
    }

    /**
     * @param w dip the unit is dip
     * @param h dip this unit is dip
     */
    public BadgeView setWidthAndHeight(int w, int h) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.width = dip2px(getContext(), w);
        params.height = dip2px(getContext(), h);
        setLayoutParams(params);
        return this;
    }

    /**
     * @param sp dip
     */
    public BadgeView setWidth(int sp) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.width = dip2px(getContext(), sp);
        setLayoutParams(params);
        return this;

    }

    public BadgeView setHeight(int sp) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.height = dip2px(getContext(), sp);
        setLayoutParams(params);
        return this;
    }

    /*@Deprecated
    public BadgeView setMargin(int left, int top, int right, int bottom) {
        leftMargin = dip2px(getContext(), left);
        bottomMargin = dip2px(getContext(), bottom);
        topMargin = dip2px(getContext(), top);
        rightMargin = dip2px(getContext(), right);
        invalidate();
        return this;
    }*/

    /**
     * @param horizontal horizontal space  unit dp
     * @param vertical   vertical space unnit dp
     */
    public BadgeView setSpace(int horizontal, int vertical) {
        horizontalSpace = dip2px(getContext(), horizontal);
        verticalSpace = dip2px(getContext(), vertical);
        invalidate();
        return this;
    }

    /**
     * @param sp the unit is sp
     */
    public BadgeView setTextSize(int sp) {
        defaultTextSize = sp2px(getContext(), sp);
        numberPaint.setTextSize(sp2px(getContext(), sp));
        invalidate();
        return this;
    }

    public BadgeView setTextColor(int color) {
        defaultTextColor = color;
        numberPaint.setColor(color);
        invalidate();
        return this;
    }

    public BadgeView setBadgeBackground(int color) {
        defaultBackgroundColor = color;
        backgroundPaint.setColor(color);
        invalidate();
        return this;
    }

    public BadgeView setBadgeCount(int count) {
        showText = String.valueOf(count);
        invalidate();
        return this;
    }

    public BadgeView setBadgeCount(String count) {
        showText = count;
        invalidate();
        return this;
    }

    public String getBadgeCount() {
        return showText;
    }

    /**
     * set gravity must be before @link bind() method
     *
     * @param gravity gravity
     */
    public BadgeView setBadgeGravity(int gravity) {
        badgeGravity = gravity;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.gravity = gravity;
        setLayoutParams(params);
        return this;
    }

    public BadgeView bind(View view) {
        if (getParent() != null)
            ((ViewGroup) getParent()).removeView(this);
        if (view == null)
            return this;
        if ((view.getParent() instanceof FrameLayout) && hasBind == true) {
            ((FrameLayout) view.getParent()).addView(this);
            return this;
        } else if (view.getParent() instanceof ViewGroup) {
            ViewGroup parentContainer = (ViewGroup) view.getParent();
            int viewIndex = ((ViewGroup) view.getParent()).indexOfChild(view);
            ((ViewGroup) view.getParent()).removeView(view);
            FrameLayout container = new FrameLayout(getContext());
            ViewGroup.LayoutParams containerParams = view.getLayoutParams();
            int origionHeight = containerParams.height;
            int origionWidth = containerParams.width;
            FrameLayout.LayoutParams viewLayoutParams = new FrameLayout.LayoutParams(origionWidth, origionHeight);

            int topMargin = 0;
            int bottomMargin = 0;
            if (origionHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                containerParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewLayoutParams.topMargin = topMargin;
                viewLayoutParams.bottomMargin = bottomMargin;
            } else {
                containerParams.height = origionHeight + topMargin + bottomMargin + verticalSpace;
            }

            int leftMargin = 0;
            int rightMargin = 0;
            if (origionWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                containerParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewLayoutParams.leftMargin = leftMargin;
                viewLayoutParams.rightMargin = rightMargin;
            } else {
                containerParams.width = origionWidth + rightMargin + horizontalSpace + leftMargin;
            }
            container.setLayoutParams(containerParams);

            //setGravity
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
            if (params.gravity == (Gravity.END | Gravity.TOP) || params.gravity == Gravity.END || params.gravity == Gravity.TOP) {
                view.setPadding(0, verticalSpace, horizontalSpace, 0);
                viewLayoutParams.gravity = Gravity.START | Gravity.BOTTOM;
            } else if (params.gravity == (Gravity.START | Gravity.TOP) || params.gravity == Gravity.START || params.gravity == Gravity.TOP) {
                view.setPadding(horizontalSpace, verticalSpace, 0, 0);
                viewLayoutParams.gravity = Gravity.END | Gravity.BOTTOM;
            } else if (params.gravity == (Gravity.START | Gravity.BOTTOM)) {
                view.setPadding(horizontalSpace, 0, 0, verticalSpace);
                viewLayoutParams.gravity = Gravity.END | Gravity.TOP;
            } else if (params.gravity == (Gravity.END | Gravity.BOTTOM)) {
                view.setPadding(0, 0, horizontalSpace, verticalSpace);
                viewLayoutParams.gravity = Gravity.START | Gravity.TOP;
            } else {
                view.setPadding(0, verticalSpace, horizontalSpace, 0);
                viewLayoutParams.gravity = Gravity.START | Gravity.BOTTOM;
            }

            view.setLayoutParams(viewLayoutParams);
            container.setId(view.getId());
            container.addView(view);
            container.addView(this);
            parentContainer.addView(container, viewIndex);
            hasBind = true;
        } else if (view.getParent() == null) {
            Log.e("badgeview", "View must have a parent");
        }
        return this;
    }

    public boolean unbind() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
            return true;
        }
        return false;
    }

}