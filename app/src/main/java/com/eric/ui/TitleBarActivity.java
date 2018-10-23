package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eric.ui.utils.Utils;
import com.eric.view.TitleBar;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: TitleBarActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class TitleBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlebar);

        TitleBar titleBar1 = findViewById(R.id.title_bar1);
        titleBar1.showBack(false);

        TitleBar titleBar2 = findViewById(R.id.title_bar2);
        titleBar2.setRightBtn1Icon(R.drawable.icon_collect);
        titleBar2.showBack(false);

        TitleBar titleBar3 = findViewById(R.id.title_bar3);
        titleBar3.showBack(false);
        titleBar3.setRightBtn1Icon(R.drawable.icon_collect);
        titleBar3.setRightBtn2Icon(R.drawable.icon_share);

        TitleBar titleBar4 = findViewById(R.id.title_bar4);
        titleBar4.setTitleRightIconVisible(true);
        titleBar4.getTitleIconView().setImageResource(R.drawable.baseview_drop_down);
        titleBar4.setRightText("取消");
        titleBar4.setRightTextVisibility(true);
        titleBar4.showBack(false);

        TitleBar titleBa5 = findViewById(R.id.title_bar5);

        TitleBar titleBar6 = findViewById(R.id.title_bar6);
        titleBar6.setRightBtn1Icon(R.drawable.icon_collect);

        TitleBar titleBar7 = findViewById(R.id.title_bar7);
        titleBar7.setRightBtn1Icon(R.drawable.icon_collect);
        titleBar7.setRightBtn2Icon(R.drawable.icon_share);

        TitleBar titleBar8 = findViewById(R.id.title_bar8);
        titleBar8.setTitleRightIconVisible(true);
        titleBar8.setRightText("取消");
        titleBar8.setRightTextVisibility(true);
        titleBar8.showBack(false);

        TitleBar titleBar9 = findViewById(R.id.title_bar9);
        titleBar9.setRightText("投诉");
        titleBar9.setRightTextVisibility(true);

        TitleBar titleBar10 = findViewById(R.id.title_bar10);
        titleBar10.setBackTextVisible(true);
        titleBar10.setLeftIconVisible(true);
        titleBar10.getLeftIconView().setImageResource(R.drawable.baseview_icon_down);
        titleBar10.setBackBtnText("杭州");
        titleBar10.showBack(false);

        TitleBar titleBar11 = findViewById(R.id.title_bar11);
        ImageView imageViewBack = titleBar11.getLeftImageView();
        imageViewBack.setImageResource(R.drawable.icon_close);


        TitleBar titleBar14 = findViewById(R.id.title_bar14);
        titleBar14.setRightBtn1Icon(R.drawable.icon_search);
        titleBar14.setRightBtn2Icon(R.drawable.icon_qrcode);
        titleBar14.setTitleRightIconVisible(true);
        titleBar14.getLeftIconView().setImageResource(R.drawable.baseview_icon_down);
        titleBar14.getTitleIconView().setImageResource(R.drawable.baseview_icon_down);
        titleBar14.setRightTextVisibility(true);
        titleBar14.setBackTextVisible(true);
        titleBar14.setBackBtnText("返回");
        titleBar14.setLeftIconVisible(true);
        titleBar14.setRightBtn1ClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TitleBarActivity.this, "Btn1Click");
            }
        });
        titleBar14.setRightBtn2ClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TitleBarActivity.this, "Btn2Click");
            }
        });
        titleBar14.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TitleBarActivity.this, "RightTextClick");
            }
        });
        titleBar14.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TitleBarActivity.this, "LeftClick");
            }
        });
        titleBar14.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TitleBarActivity.this, "TitleClick");
            }
        });
    }
}