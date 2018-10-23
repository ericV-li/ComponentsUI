package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.eric.view.RefreshLayout;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: RefreshLayoutNotListViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class RefreshLayoutNotListViewActivity extends Activity {

    private RefreshLayout mLJRefreshLayout;
    private TextView mTextView;
    private int mCount = 1;//刷新次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_not_list_view_layout);
        initView();
        setListener();
    }

    private void initView() {

        mLJRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mLJRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mLJRefreshLayout.setRefreshing(true);
            }
        });
        mTextView =  findViewById(R.id.text_view);
    }

    private void setListener() {

        mLJRefreshLayout.setOnRefreshListener(new RefreshLayout.OnViewRefreshListener() {
            @Override
            public void onRefresh(boolean isLoadingMore) {
                // isLoadingMore,是否为加载更多的回调。
                mLJRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLJRefreshLayout.setRefreshing(false);
                        mTextView.setText("我被刷新了" + mCount++);
                    }
                }, 2000);
            }
        });
    }
}
