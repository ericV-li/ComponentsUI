package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.view.AverageTabView;
import com.eric.view.TabHostTabView;
import com.eric.view.TabView;
import com.eric.view.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: TabViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class TabViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        //使用TabView 三步操作
        TabView tabView1 = findViewById(R.id.tab_view1);
        //第一步   设置数据
        tabView1.setDataString(getData("全部", "杂谈", "分享", "资讯", "美食", "电影", "游玩", "火车"));
        //第二步   设置监听
        tabView1.setOnSelectListener(new TabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        //第三步  设置需要选中的位置
        tabView1.setTabSelect(0, true);

        TabView tabView2 = findViewById(R.id.tab_view2);
        tabView2.setDataListItem(getDataListItem("全部", "杂谈", "分享", "资讯", "美食", "电影", "游玩", "火车"));
        tabView2.setOnSelectListener(new TabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        tabView2.setTabSelect(1, false);

        TabView tabView3 = findViewById(R.id.tab_view3);
        tabView3.setDataListItem(getDataListItem("全部", "杂谈", "分享", "资讯", "美食", "电影", "游玩", "火车"));
        tabView3.setOnSelectListener(new TabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }

        });
        tabView3.setTabSelect(3, false);

        final TabView tabView4 = findViewById(R.id.tab_view4);
        tabView4.setDataString(getData("全部", "杂谈", "分享", "资讯", "美食", "电影", "游玩", "火车"));
        tabView4.setOnSelectListener(new TabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        tabView4.setTabSelect(4, true);
        findViewById(R.id.txt_get_select_position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("current position " + tabView4.getTabSelectPosition());
            }
        });
        findViewById(R.id.txt_select_position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabView4.setTabSelect(3, true);
            }
        });


        AverageTabView tabView5 = findViewById(R.id.tab_view5);
        tabView5.setDataString(getData("全部", "杂谈", "分享", "资讯"));
        tabView5.setTabSelect(2, true);
        tabView5.setOnSelectListener(new AverageTabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });


        AverageTabView tabView6 = findViewById(R.id.tab_view6);
        tabView6.setDataString(getData("杂谈", "分享", "资讯"));
        tabView6.setTabSelect(0, true);
        tabView6.setOnSelectListener(new AverageTabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });


        //使用TabView 三步操作
        TabHostTabView tabHostView1 = findViewById(R.id.tab_host_tab_view1);
        //第一步   设置数据
        tabHostView1.setDataListItem(getDataListItem("杂谈", "分享", "资讯"));
        //第二步   设置监听
        tabHostView1.setOnSelectListener(new TabHostTabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        tabHostView1.setTabSelect(1);

        //使用TabView 三步操作
        TabHostTabView tabHostView2 = findViewById(R.id.tab_host_tab_view2);
        //第一步   设置数据
        tabHostView2.setDataListItem(getDataListItem("杂谈", "分享", "资讯"));
        //第二步   设置监听
        tabHostView2.setOnSelectListener(new TabHostTabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        tabHostView2.setTabSelect(2);

        //使用TabView 三步操作
        TabHostTabView tabHostView3 = findViewById(R.id.tab_host_tab_view3);
        //第一步   设置数据
        tabHostView3.setDataListItem(getDataListItem("全部", "杂谈"));
        //第二步   设置监听
        tabHostView3.setOnSelectListener(new TabHostTabView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position, ListItem listItem) {

            }
        });
        tabHostView3.setTabSelect(1);

    }

    private List<String> getData(String... item) {
        List<String> contents = new ArrayList<>();
        for (String tempItem : item) {
            contents.add(tempItem);
        }
        return contents;
    }

    private List<ListItem> getDataListItem(String... item) {
        List<ListItem> contents = new ArrayList<>();
        for (String tempItem : item) {
            ListItem listItem = new ListItem();
            listItem.setTitle(tempItem);
            contents.add(listItem);
        }
        return contents;
    }
}
