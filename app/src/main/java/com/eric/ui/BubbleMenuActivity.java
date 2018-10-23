package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.eric.ui.utils.Utils;
import com.eric.view.BubbleMenu;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: BubbleMenuActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class BubbleMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_bubble_menu);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menus = new String[]{"发送"};
                showTextBubbleMenu(v,menus);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menus = new String[]{"发送", "点击"};
                showTextBubbleMenu(v,menus);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menus = new String[]{"发送", "点击", "弹出"};
                showTextBubbleMenu(v,menus);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menus = new String[]{"发送", "点击", "弹出", "模拟"};
                showTextBubbleMenu(v,menus);
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menus = new String[]{"发送", "点击", "弹出", "模拟"};
                showTextBubbleMenu(v,menus);
            }
        });
    }

    /**
     *
     * @param currentView 用于获得location
     */
    private void showTextBubbleMenu(View currentView,String[] menus){
        BubbleMenu.OnMenuItemClickListener onMenuItemClickListener = new BubbleMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, String s) {
                Utils.show(BubbleMenuActivity.this, s);
            }
        };
        BubbleMenu textBubbleMenu = new BubbleMenu(BubbleMenuActivity.this, menus, currentView, onMenuItemClickListener);
        textBubbleMenu.show();
    }
}
