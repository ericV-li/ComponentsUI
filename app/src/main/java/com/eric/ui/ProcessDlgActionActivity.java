package com.eric.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eric.view.dialog.ProgressDialog;


/**
 * @author li
 * @Package com.eric.ui
 * @Title: ProcessDlgActionActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class ProcessDlgActionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processdlg);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog processDlgAction = new ProgressDialog();
                ProgressDialog.OnProgressDialogListener onProcessDialogListener = new ProgressDialog.OnProgressDialogListener() {
                    @Override
                    public void onCancelled() {
                        Toast.makeText(ProcessDlgActionActivity.this, "还可以设置取消dialog监听", Toast.LENGTH_SHORT).show();
                    }
                };
                processDlgAction.showDialog(ProcessDlgActionActivity.this, "", onProcessDialogListener);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog processDlgAction = new ProgressDialog();
                ProgressDialog.OnProgressDialogListener onProcessDialogListener = new ProgressDialog.OnProgressDialogListener() {
                    @Override
                    public void onCancelled() {
                        Toast.makeText(ProcessDlgActionActivity.this, "还可以设置取消dialog监听", Toast.LENGTH_SHORT).show();
                    }
                };
                processDlgAction.showDialog(ProcessDlgActionActivity.this, "正在请求会员信息...", onProcessDialogListener);
            }
        });
    }
}
