package com.eric.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.eric.ui.common.BaseActivity;
import com.eric.view.SearchBar;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: SearchBarActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class SearchBarActivity extends BaseActivity {
    private Toast toast;


    private SearchBar searchBar1;
    private SearchBar searchBar2;
    private SearchBar searchBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        initView();
        initData();
        searchBar1.setSearchClickerListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast = Toast.makeText(getApplicationContext(),
                        "搜索中，耐心等待！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });
        searchBar1.setSearchBtnText("搜索");
        searchBar1.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("搜索");
            }
        });

        searchBar2.setSearchClickerListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast = Toast.makeText(getApplicationContext(),
                        "搜索中，耐心等待！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });


        searchBar3.setSearchType("商品");
        searchBar3.setSearchBtnText("搜索");
        searchBar3.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("搜索");
            }
        });
    }

    private void initView() {
        searchBar1 = findViewById(R.id.search_bar1);
        searchBar2 = findViewById(R.id.search_bar2);
        searchBar3 = findViewById(R.id.search_bar3);


    }

    private void initData() {
        searchBar2.setHint("价廉物美，尽快来");

    }
}
