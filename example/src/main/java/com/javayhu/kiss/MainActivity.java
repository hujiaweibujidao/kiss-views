package com.javayhu.kiss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.javayhu.kiss.views.FadingTextView;

public class MainActivity extends AppCompatActivity {

    private FadingTextView mFadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFadingTextView = (FadingTextView) findViewById(R.id.fadingTextView);
        String[] texts = new String[]{"Hello, World!", "Google returns to China", "Happy new Year"};
        mFadingTextView.setTexts(texts);
        mFadingTextView.setTimeout(1000);
    }

}
