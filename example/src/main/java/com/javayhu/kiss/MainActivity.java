package com.javayhu.kiss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.javayhu.kiss.views.common.BadgeFactory;
import com.javayhu.kiss.views.common.DraggableBadgeView;
import com.javayhu.kiss.views.imageview.CircleImageView;
import com.javayhu.kiss.views.textview.FadingTextView;

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

        CircleImageView civ = (CircleImageView) findViewById(R.id.civ);
        Button dialogbutton = (Button) findViewById(R.id.dialogbutton);
        Button badgebutton = (Button) findViewById(R.id.badgebutton);

        DraggableBadgeView badgeView = new DraggableBadgeView(this);
        badgeView.setShowShadow(true).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeNumber(14).bindTarget(badgebutton);

        BadgeFactory.createCircle(this).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeCount(12).bind(civ);
    }

    public void gotoDialog(View view) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void gotoBadge(View view) {
        startActivity(new Intent(this, BadgeViewActivity.class));
    }
}
