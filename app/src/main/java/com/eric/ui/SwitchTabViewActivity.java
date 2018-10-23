package com.eric.ui;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.eric.ui.common.BaseActivity;
import com.eric.view.SwitchTabView;


/**
 * @author li
 * @Package com.eric.ui
 * @Title: SwitchTabViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class SwitchTabViewActivity extends BaseActivity {

    private SwitchTabView mSwitchTabView;
    private SwitchTabView mSwitchTabView2;
    private SwitchTabView mSwitchTabView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_tab_view);
        initView();
        initData();
        mSwitchTabView.setOnTabClickListener(new SwitchTabView.OnTabClickListener() {
            @Override
            public void onTabClick(int position, boolean show) {
                Log.e("ai", "position:" + position + ";show：" + show);
                switch (position) {
                    case 0:
                        if (show) {
                            showDialog1();
                        } else {
                            showToast("关闭电话TAb");
                        }
                        break;
                    case 1:
                        if (show) {
                            showDialog2();
                        } else {
                            showToast("关闭朋友TAb");
                        }
                        break;
                }

            }
        });


    }

    private void initData() {
        mSwitchTabView.setTabCount(2);
        mSwitchTabView.setTabText("电话", "朋友", null);
        mSwitchTabView.setShowTopDivider(true);
        mSwitchTabView2.setTabCount(3);
        mSwitchTabView2.setTabText("电话", "朋友", "GG");
        mSwitchTabView2.setShowTopDivider(true);
        mSwitchTabView3.setTabCount(3);
        mSwitchTabView3.setTabText("司机位置", null, true, "车型", null, true, "查找", getResources().getDrawable(R.drawable.baseview_search_color_gray), false);
        mSwitchTabView3.setShowTopDivider(true);
    }

    private void initView() {
        mSwitchTabView = findViewById(R.id.btn_switch_tab_view1);
        mSwitchTabView2 = findViewById(R.id.btn_switch_tab_view2);
        mSwitchTabView3 = findViewById(R.id.btn_switch_tab_view3);

    }

    private void showDialog1() {
        new AlertDialog.Builder(this).setItems(
                new String[]{"电话1", "电话2", "电话3", "电话4"}, null).show();
    }

    private void showDialog2() {
        new AlertDialog.Builder(this).setItems(
                new String[]{"朋友1", "朋友2", "朋友3", "朋友4"}, null).show();

    }
}


