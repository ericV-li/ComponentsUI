package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.view.ListPopWindow;
import com.eric.view.SearchBar;
import com.eric.view.entity.PopupWindowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: PopupWindowActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */

public class PopupWindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        final SearchBar ljTitleBar =  findViewById(R.id.view_title);
        ljTitleBar.setSearchType("物价");
        ljTitleBar.setSearchTypeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListPopWindow popWindow = new ListPopWindow(PopupWindowActivity.this,getData());
                popWindow.showAsDropDown(ljTitleBar.getSearchTypeView());
                popWindow.setOnItemSelectListener(new ListPopWindow.OnItemSelectListener() {
                    @Override
                    public void onItemSelect(PopupWindowItem item) {
                        showToast(item.getTitle());
                        popWindow.dismiss();
                    }
                });
            }
        });
    }

    private List<PopupWindowItem> getData() {
        List list = new ArrayList();
        PopupWindowItem entity;
        entity = new PopupWindowItem();
        entity.setTitle("略略略");
        entity.setCode("1");
        list.add(entity);
        entity = new PopupWindowItem();
        entity.setTitle("略略略");
        entity.setCode("2");
        list.add(entity);
        entity = new PopupWindowItem();
        entity.setTitle("略略略");
        entity.setCode("3");
        list.add(entity);
        return list;
    }
}
