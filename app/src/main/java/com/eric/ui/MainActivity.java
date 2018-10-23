package com.eric.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_title_bar).setOnClickListener(this);
        findViewById(R.id.btn_lj_scroll_list_view).setOnClickListener(this);
        findViewById(R.id.btn_lj_refresh_layout).setOnClickListener(this);
        findViewById(R.id.btn_lj_table_view).setOnClickListener(this);
        findViewById(R.id.btn_my_list_view).setOnClickListener(this);
        findViewById(R.id.btn_process_dialog_action).setOnClickListener(this);
        findViewById(R.id.btn_text_bubble_menu).setOnClickListener(this);
        findViewById(R.id.btn_icon_font_text_view).setOnClickListener(this);
        findViewById(R.id.btn_lj_tab_view).setOnClickListener(this);
        findViewById(R.id.btn_lj_dialog).setOnClickListener(this);
        findViewById(R.id.btn_lj_lrtextview).setOnClickListener(this);
        findViewById(R.id.btn_lj_search_bar).setOnClickListener(this);
        findViewById(R.id.btn_lj_switch_tab_view).setOnClickListener(this);
        findViewById(R.id.btn_lj_clear_edit).setOnClickListener(this);
        findViewById(R.id.btn_lj_loading).setOnClickListener(this);
        findViewById(R.id.btn_button).setOnClickListener(this);
        findViewById(R.id.btn_toast).setOnClickListener(this);
        findViewById(R.id.btn_badge).setOnClickListener(this);
        findViewById(R.id.btn_timer_select).setOnClickListener(this);
        findViewById(R.id.btn_keyboard_dialog).setOnClickListener(this);
        findViewById(R.id.btn_lj_popup_window).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_title_bar:
                intent.setClass(this, TitleBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_scroll_list_view:
                intent.setClass(this, ScrollListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_refresh_layout:
                intent.setClass(this, RefreshLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_table_view:
                intent.setClass(this, TableViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_list_view:
                intent.setClass(this, MyListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_process_dialog_action:
                intent.setClass(this, ProcessDlgActionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_text_bubble_menu:
                intent.setClass(this, BubbleMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_icon_font_text_view:
                intent.setClass(this, IconFontTextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_tab_view:
                intent.setClass(this, TabViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_dialog:
                intent.setClass(this, AlertDialogActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_lj_lrtextview:
                intent.setClass(this, LeftRightTextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_clear_edit:
                intent.setClass(this, ClearEditTextActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_search_bar:
                intent.setClass(this, SearchBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_switch_tab_view:
                intent.setClass(this, SwitchTabViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_popup_window:
                intent.setClass(this, PopupWindowActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lj_loading:
                intent.setClass(this, LoadingViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_button:
                intent.setClass(this, ButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toast:
                intent.setClass(this, ToastActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_badge:
                intent.setClass(this, BadgeViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_timer_select:
                intent.setClass(this, TimerSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_keyboard_dialog:
                intent.setClass(this, NumberKeyboardActivity.class);
                startActivity(intent);
                break;

        }
    }
}
