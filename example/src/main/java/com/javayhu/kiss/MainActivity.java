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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleImageView civ = (CircleImageView) findViewById(R.id.civ);
        Button textbutton = (Button) findViewById(R.id.textbutton);
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

    public void gotoText(View view) {
        startActivity(new Intent(this, TextActivity.class));
    }
}
