package com.eric.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.eric.ui.common.BaseActivity;
import com.eric.ui.utils.Utils;
import com.eric.view.RefreshLayout;
import com.eric.view.RefreshListView;
import com.eric.view.adapter.BaseSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: RefreshLayoutListViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class RefreshLayoutListViewActivity extends BaseActivity {

    private static final int PAGE_SIZE = 10;

    private RefreshLayout mRefreshLayout;
    private RefreshListView mScrollListView;
    private RefreshAdpter mArrayAdapter;
    private int mPageIndex = 1;
    private List<Item> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list_view_layout);
        initView();
        setListener();
        initData();
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void initView() {
        mRefreshLayout = findViewById(R.id.lj_swipe_refresh_layout);
        mScrollListView = findViewById(R.id.lj_scroll_list_view);
        View view = LayoutInflater.from(this).inflate(R.layout.baseview_view_listview_head, null);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showToast("99990");
            }
        });
        mScrollListView.addHeaderView(view);
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnViewRefreshListener() {
            @Override
            public void onRefresh(final boolean isLoadingMore) {
                //异步请求网络
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isLoadingMore) {
                            mPageIndex = 1;
                        }
                        loadData(isLoadingMore);
                    }
                }, 800);
            }


        });
        mRefreshLayout.setOnListViewScrollListener(new RefreshLayout.OnListViewScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("edwin", "firstVisibleItem:" + firstVisibleItem + ";visibleItemCount:" + visibleItemCount);
            }
        });
        mScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int realPosition = position - mScrollListView.getHeaderViewsCount();
                if (realPosition < 0 || realPosition > mArrayAdapter.getCount()) {
                    return;
                }
                Utils.show(RefreshLayoutListViewActivity.this, " position=" + position );
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mArrayAdapter = new RefreshAdpter(this, mList);
        mScrollListView.setAdapter(mArrayAdapter);
    }

    private void loadData(boolean loadMore) {
        mRefreshLayout.setRefreshing(false);
        List<Item> list = new ArrayList<>();
        Item item;
        for (int i = 0; i < 10; i++) {
            item = new Item();
            item.setName(Utils.geneUserName());
            item.setNumber(Utils.geneNumber());
            list.add(item);
        }
        // 1.设置listView状态

        if (mPageIndex > 5) {
            mRefreshLayout.setLoadingMoreEnabled(false, "从前有个太监....下面没了");
        } else {
            if (list.size() == PAGE_SIZE) {
                mRefreshLayout.setLoadingMoreEnabled(true);
                mPageIndex++;
            } else {
                mRefreshLayout.setLoadingMoreEnabled(false);
            }
        }
        // 2.通知adapter刷新数据
        if (!loadMore) {
            mList.clear();
        }
        mList.addAll(list);
        mArrayAdapter.replaceAll(mList);
    }

    private class Item {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        private String name;
        private String number;
    }

    private class RefreshAdpter extends BaseSimpleAdapter<Item> {

        public RefreshAdpter(Context context, List<Item> data) {
            super(context, data);
        }

        @Override
        public int getItemResource() {
            return android.R.layout.simple_list_item_1;
        }

        @Override
        public View getItemView(int position, View convertView, ViewHolder holder) {
            TextView textView = holder.getView(android.R.id.text1);
            Item item = data.get(position);
            textView.setText("姓名：" + item.getName() + "\n电话：" + item.getNumber());
            return convertView;
        }
    }
}
