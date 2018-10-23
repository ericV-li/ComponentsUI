package com.eric.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eric.ui.common.BaseActivity;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ButtonActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        (findViewById(R.id.lj_button1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ButtonActivity.this, "点击了1", Toast.LENGTH_SHORT).show();
            }
        });
        (findViewById(R.id.lj_button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ButtonActivity.this, "点击了2", Toast.LENGTH_SHORT).show();
            }
        });
        (findViewById(R.id.lj_button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ButtonActivity.this, "点击了3", Toast.LENGTH_SHORT).show();
            }
        });
        (findViewById(R.id.lj_button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button8)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button9)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.lj_button10)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
