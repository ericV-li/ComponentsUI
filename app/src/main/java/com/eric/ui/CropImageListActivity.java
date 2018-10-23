package com.eric.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.eric.ui.common.BaseActivity;
import com.eric.view.RefreshLayout;
import com.eric.view.RefreshListView;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: CropImageListActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class CropImageListActivity extends BaseActivity {

    private RefreshLayout mLJRefreshLayout;
    private RefreshListView mLJScrollListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image_list);
        initView();
        setListener();
        mLJRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mLJRefreshLayout.setRefreshing(true);
            }
        });
    }


    private void initView() {
        mLJRefreshLayout = findViewById(R.id.lj_swipe_refresh_layout);
        mLJScrollListView = findViewById(R.id.lj_scroll_list_view);
    }

    private void setListener() {
        mLJRefreshLayout.setOnRefreshListener(new RefreshLayout.OnViewRefreshListener() {
            @Override
            public void onRefresh(final boolean isLoadingMore) {
                //异步请求网络
                mLJRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isLoadingMore) {

                        }
                    }
                }, 800);
            }
        });
        mLJScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
