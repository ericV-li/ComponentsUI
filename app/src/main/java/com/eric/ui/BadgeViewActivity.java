package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.view.BadgeView;

/**
 * Created by Administrator on 2017/8/2.
 */

public class BadgeViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        BadgeView badgeView1  = (BadgeView) findViewById(R.id.view_badge1);
        badgeView1.setText("吃饭了时间到了，出发吱声");

        BadgeView badgeView2  = (BadgeView) findViewById(R.id.view_badge2);
        badgeView2.setText("吃饭了时间到了，出发吱声");
        badgeView2.setOnClearClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("不走");
            }
        });

        BadgeView badgeView3  = (BadgeView) findViewById(R.id.view_badge3);
        badgeView3.setText("吃饭了时间到了，出发吱声");
        badgeView3.setOnNextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("走起");
            }
        });

    }
}
