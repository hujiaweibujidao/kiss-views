package com.javayhu.kiss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.javayhu.kiss.views.textview.FadingTextView;

public class TextActivity extends AppCompatActivity {

    private FadingTextView mFadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        mFadingTextView = (FadingTextView) findViewById(R.id.fadingTextView);
        String[] texts = new String[]{"Hello, World!", "Google returns to China", "Happy New Year"};
        mFadingTextView.setTexts(texts);
        mFadingTextView.setTimeout(1000);
    }
}
