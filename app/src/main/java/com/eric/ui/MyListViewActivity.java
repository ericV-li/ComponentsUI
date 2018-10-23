package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.eric.ui.utils.Utils;
import com.eric.view.MyListView;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: MyListViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class MyListViewActivity extends Activity {

    private MyListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);
        mListView = findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Utils.getData());
        mListView.setAdapter(arrayAdapter);
        mListView.setonRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //当前日期（格式自己定）
                        mListView.onRefreshComplete("2016-08-02 15:30:25");
                    }
                }, 2000);
            }
        });
    }
}
