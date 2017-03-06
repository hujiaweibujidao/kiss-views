package com.javayhu.kiss.views.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.javayhu.kiss.views.R;
import com.javayhu.kiss.views.span.RoundedBackgroundSpan;

/**
 * Copyright 2017 Tim Qi
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 可以隐藏和点击显示全文的TextView
 * <p>
 * https://github.com/timqi/CollapsibleTextView/blob/master/library/src/main/res/values/attrs.xml
 * <p>
 * <com.timqi.collapsibletextview.CollapsibleTextView
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * android:id="@+id/normal"
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * android:padding="5dp"
 * app:collapsedLines="2"
 * app:collapsedText=" Show All"
 * app:expandedText=" Hide"
 * app:suffixColor="#0000ff"
 * app:suffixTrigger="true" // whether it is triggered by suffix only
 * />
 * <p>
 * mCollapsibleTextView.setCollapsedLines(1);
 * mCollapsibleTextView.setCollapsedText(" Show Text");
 * mCollapsibleTextView.setExpandedText(" Hide Text");
 * mCollapsibleTextView.setSuffixColor(0xff0000ff);
 * mCollapsibleTextView.setSuffixTrigger(true);
 * <p>
 * CollapsibleTextView mCollapsibleTextView = (CollapsibleTextView) findViewById(R.id.normal);
 * mCollapsibleTextView.setFullString(mLongText);
 * mCollapsibleTextView.setExpanded(true);
 * <p>
 * Updated by javayhu on 2017/3/2.
 * Created by timqi on 2017/2/23.
 */
public class CollapsibleTextView extends TextView {

    private static final String DEFAULT_EXPANDED_TEXT = " Hide";
    private static final String DEFAULT_COLLAPSED_TEXT = " Show All";

    private int mCollapsedLines = 1;
    private int mSuffixColor = 0xff0000ff;

    private boolean mExpanded = false;
    private boolean mSuffixTrigger = false;//默认是false，也就是点击TextView或者suffix字符串都可以触发显示和隐藏
    private boolean mShouldInitLayout = true;

    private String mText;
    private String mExpandedText = DEFAULT_EXPANDED_TEXT;
    private String mCollapsedText = DEFAULT_COLLAPSED_TEXT;

    private OnClickListener mCustomClickListener;

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView, defStyleAttr, 0);

        mText = getText() == null ? null : getText().toString();
        mSuffixColor = attributes.getColor(R.styleable.CollapsibleTextView_suffixColor, 0xff0000ff);
        mCollapsedLines = attributes.getInt(R.styleable.CollapsibleTextView_collapsedLines, 1);
        mSuffixTrigger = attributes.getBoolean(R.styleable.CollapsibleTextView_suffixTrigger, false);
        mCollapsedText = attributes.getString(R.styleable.CollapsibleTextView_collapsedText);
        if (TextUtils.isEmpty(mCollapsedText)) mCollapsedText = DEFAULT_COLLAPSED_TEXT;
        mExpandedText = attributes.getString(R.styleable.CollapsibleTextView_expandedText);
        if (TextUtils.isEmpty(mExpandedText)) mExpandedText = DEFAULT_EXPANDED_TEXT;

        setMovementMethod(LinkMovementMethod.getInstance());
        super.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mShouldInitLayout && getLineCount() > mCollapsedLines) {
            mShouldInitLayout = false;
            applyState(mExpanded);
        }
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mSuffixTrigger) {
                mExpanded = !mExpanded;
                applyState(mExpanded);
            }

            if (mCustomClickListener != null) {
                mCustomClickListener.onClick(v);
            }
        }
    };

    @Override
    public void setOnClickListener(OnClickListener l) {
        mCustomClickListener = l;
    }

    private void applyState(boolean expanded) {
        if (TextUtils.isEmpty(mText)) return;

        String note = mText, suffix;
        if (expanded) {
            suffix = mExpandedText;
        } else {
            if (mCollapsedLines < 1) {
                throw new RuntimeException("CollapsedLines must equal or greater than 1");
            }
            int lineEnd = getLayout().getLineEnd(mCollapsedLines - 1);//文本收起来的最后一行的最后位置
            suffix = mCollapsedText;
            int newEnd = lineEnd - suffix.length() - 1;//减去后缀+一个空格
            int end = newEnd > 0 ? newEnd : lineEnd;//

            TextPaint paint = getPaint();
            int maxWidth = mCollapsedLines * (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());//计算在收缩状态下的最大长度
            while (paint.measureText(note.substring(0, end) + suffix) > maxWidth) {
                end--;//如果超过了最大长度那么就减少note的字符串
            }
            note = note.substring(0, end);//上面的代码主要就是判断在note的哪个位置开始截断，然后插入suffix字符串
        }

        final SpannableString str = new SpannableString(note + suffix);
        if (mSuffixTrigger) {
            str.setSpan(mClickableSpan, note.length(), note.length() + suffix.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        str.setSpan(new RoundedBackgroundSpan(Color.MAGENTA), note.length() + 1, note.length() + suffix.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        //str.setSpan(new ForegroundColorSpan(mSuffixColor), note.length(), note.length() + suffix.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        post(new Runnable() {
            @Override
            public void run() {
                setText(str);
            }
        });
    }

    //重新设置了text之后就要重新layout
    public void setFullText(String str) {
        this.mText = str;
        mShouldInitLayout = true;
        setText(mText);
    }

    //自定义的可以点击的Span，去掉了下划线，并且重写了点击处理逻辑
    private ClickableSpan mClickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            if (mSuffixTrigger) {
                mExpanded = !mExpanded;
                applyState(mExpanded);
            }
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
        }
    };

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean mExpanded) {
        if (this.mExpanded != mExpanded) {
            this.mExpanded = mExpanded;
            applyState(mExpanded);
        }
    }

    public int getSuffixColor() {
        return mSuffixColor;
    }

    public void setSuffixColor(int mSuffixColor) {
        this.mSuffixColor = mSuffixColor;
        applyState(mExpanded);
    }

    public int getCollapsedLines() {
        return mCollapsedLines;
    }

    public void setCollapsedLines(int mCollapsedLines) {
        this.mCollapsedLines = mCollapsedLines;
        mShouldInitLayout = true;
        setText(mText);
    }

    public boolean isSuffixTrigger() {
        return mSuffixTrigger;
    }

    public void setSuffixTrigger(boolean mSuffixTrigger) {
        this.mSuffixTrigger = mSuffixTrigger;
        applyState(mExpanded);
    }

    public String getCollapsedText() {
        return mCollapsedText;
    }

    public void setCollapsedText(String mCollapsedText) {
        this.mCollapsedText = mCollapsedText;
        applyState(mExpanded);
    }

    public String getExpandedText() {
        return mExpandedText;
    }

    public void setExpandedText(String mExpandedText) {
        this.mExpandedText = mExpandedText;
        applyState(mExpanded);
    }
}