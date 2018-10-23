package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.view.utils.ToastUtil;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: ToastActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */

public class ToastActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(ToastActivity.this, "发布失败，请重新操作");
            }
        });
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(ToastActivity.this, "添加成功", R.drawable.contact_gallery_image_select);
            }
        });
        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showSuccessToast(ToastActivity.this, "添加成功");
            }
        });

    }
}
