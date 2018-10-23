package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eric.ui.utils.Utils;
import com.eric.view.TableView;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: TableViewActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class TableViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableview);

        TableView tableView4 = findViewById(R.id.table_view4);
        View icons = LayoutInflater.from(this).inflate(R.layout.view_icons, null);
        tableView4.addRightChildView(icons);
        tableView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.show(TableViewActivity.this, "TableView点击");
            }
        });
        TableView tableView5 = findViewById(R.id.table_view5);
        tableView5.setLeftImage(R.drawable.rz_jia);
        tableView5.setContentImage(R.drawable.rz_shui);

        TableView tableView6 = findViewById(R.id.table_view6);
        tableView6.setLeftImage(R.drawable.rz_jia);
        tableView6.setContentImage(R.drawable.rz_shui);
    }
}
