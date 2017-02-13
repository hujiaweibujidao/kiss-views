package com.javayhu.kiss.views.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.javayhu.kiss.views.R;

/**
 * A textview that changes its content automatically every few seconds
 * <p>
 * https://github.com/rosenpin/FadingTextView
 * <p>
 * Updated by hujiawei on 2017/2/11.
 * Created by rosenpin on 12/8/16.
 */
public class FadingTextView extends TextView {

    private Animation mFadeInAnimation, mFadeOutAnimation;
    private Handler mHandler;
    private CharSequence[] mTexts;
    private boolean isShown;
    private int mPosition;
    private int mTimeout = 15000;

    public FadingTextView(Context context) {
        super(context);
        init(context);
    }

    public FadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        handleAttrs(context, attrs);
    }

    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        handleAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        handleAttrs(context, attrs);
    }

    public void resume() {
        isShown = true;
        startAnimation();
    }

    public void pause() {
        isShown = false;
        stopAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resume();
    }

    private void init(Context context) {
        mFadeInAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        mHandler = new Handler();
        isShown = true;
    }

    private void handleAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.fading_text_view);
        this.mTexts = a.getTextArray(R.styleable.fading_text_view_texts);
        this.mTimeout = Math.abs(a.getInteger(R.styleable.fading_text_view_timeout, 14500)) + getResources().getInteger(android.R.integer.config_longAnimTime);
        a.recycle();
    }

    public CharSequence[] getTexts() {
        return mTexts;
    }

    public void setTexts(@ArrayRes int texts) {
        if (getResources().getStringArray(texts).length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.mTexts = getResources().getStringArray(texts);
            stopAnimation();
            mPosition = 0;
            startAnimation();
        }
    }

    public void setTexts(@NonNull String[] texts) {
        if (texts.length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.mTexts = texts;
            stopAnimation();
            mPosition = 0;
            startAnimation();
        }
    }

    public void setTimeout(int timeout) {
        if (timeout < 1)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else
            this.mTimeout = timeout;
    }

    private void stopAnimation() {
        mHandler.removeCallbacksAndMessages(null);
        if (getAnimation() != null) getAnimation().cancel();
    }

    protected void startAnimation() {
        setText(mTexts[mPosition]);
        startAnimation(mFadeInAnimation);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation(mFadeOutAnimation);
                getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (isShown) {
                            mPosition = mPosition == mTexts.length - 1 ? 0 : mPosition + 1;
                            startAnimation();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }, mTimeout);
    }
}