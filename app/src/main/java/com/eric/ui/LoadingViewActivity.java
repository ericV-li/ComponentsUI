package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eric.view.LoadingView;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: LoadingViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class LoadingViewActivity extends Activity {

    private LoadingView mLoadingViewCenter;
    private LoadingView mLoadingView1;
    private LoadingView mLoadingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        mLoadingViewCenter = new LoadingView(this);
        mLoadingView1 = new LoadingView(this);
        mLoadingView2 = new LoadingView(this);
        final TextView textView1 = findViewById(R.id.txt_show_loading_view_center);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingViewCenter.attachToWindowCenter((View) textView1.getParent());
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingViewCenter.detachFromView();
                    }
                }, 10000);
            }
        });
        findViewById(R.id.txt_show_loading_view_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView1.attachToView(v, 5, 5, 5, 5);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingView1.detachFromView();
                    }
                }, 10000);
            }
        });
        findViewById(R.id.txt_show_loading_view_view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView2.attachToView(v);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingView2.detachFromView();
                    }
                }, 8000);
            }
        });
    }
}
