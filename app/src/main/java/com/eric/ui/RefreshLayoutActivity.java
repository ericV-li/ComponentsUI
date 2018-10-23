package com.eric.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: RefreshLayoutActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class RefreshLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        findViewById(R.id.btn_lj_refresh_list_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RefreshLayoutActivity.this, RefreshLayoutListViewActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_lj_refresh_not_list_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RefreshLayoutActivity.this, RefreshLayoutNotListViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
