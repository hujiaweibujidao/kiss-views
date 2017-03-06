package com.javayhu.kiss;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.javayhu.kiss.views.toast.ToastUtils;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.error(ToastActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.success(ToastActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.info(ToastActivity.this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.warning(ToastActivity.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.normal(ToastActivity.this, "Normal toast w/o icon").show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.ic_star_border_white_36dp);
                ToastUtils.normal(ToastActivity.this, "Normal toast w/ icon", icon).show();
            }
        });
    }
}
