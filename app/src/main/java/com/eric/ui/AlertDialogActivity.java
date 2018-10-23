package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.view.dialog.ListComponentDialog;
import com.eric.view.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: AlertDialogActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class AlertDialogActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                showAlertDialog("标题", "确认购买该股票", "确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("你离百万富翁已经不远了");
                    }
                }, "坚决不买", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("不买拉倒，我再去骗别人");
                    }
                }, false);
                break;
            case R.id.btn_2:
                // showAlertDialog("", "确认不用安利了吗？", "确定", null, "取消", null, false);
                showAlertDialog("请设置属性", "渲染UI", "设置",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                            }
                        }, "取消", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                            }
                        }, false);
                break;
            case R.id.btn_3:
                showConfirmDialog("标题", "恭喜你，注册成功恭喜你，注册成功恭喜你，注册成功恭喜你，注册成功", "我知道了", null);
                break;
            case R.id.btn_4:
                showConfirmDialog("", "恭喜你，中了10元红包恭喜你，中了10元红包恭喜你，中了10元红包恭喜你，中了10元红包", "退下吧", null, false);
                break;
            case R.id.btn_5:
                showListDialog(null, true);
                break;
            case R.id.btn_6:
                showListDialog("选择", false);
                break;
        }
    }

    private void showListDialog(String title, boolean useBlueStyle) {
        showListDialog(title, mockListDialogData(), new ListComponentDialog.OnListDialogListener() {
            @Override
            public void onDismissed() {
                showToast("哎呀，我消失了");
            }

            @Override
            public void onCancel() {
                showToast("我被关闭了");
            }

            @Override
            public void onItemClick(int position, ListItem item) {
                showToast("我被点了" + ";position:" + position + ";title:" + item.getTitle() + ";code:" + item.getCode());
            }
        }, useBlueStyle);
    }


    private List<ListItem> mockListDialogData() {
        List<ListItem> list = new ArrayList<>();
        ListItem item = new ListItem();
        item.setTitle("Good");
        item.setCode("first");
        list.add(item);
        item = new ListItem();
        item.setTitle("Morning");
        item.setCode("second");
        list.add(item);
        item = new ListItem();
        item.setTitle("Driver");
        item.setCode("third");
        list.add(item);
        item.setTitle("Good");
        item.setCode("first");
        list.add(item);
        item = new ListItem();
        item.setTitle("Morning");
        item.setCode("second");
        list.add(item);
        item = new ListItem();
        item.setTitle("Driver");
        item.setCode("third");
        list.add(item);
        item.setTitle("Good");
        item.setCode("first");
        list.add(item);
        item = new ListItem();
        item.setTitle("Morning");
        item.setCode("second");
        list.add(item);
        item = new ListItem();
        item.setTitle("Driver");
        item.setCode("third");
        list.add(item);
        return list;
    }
}
