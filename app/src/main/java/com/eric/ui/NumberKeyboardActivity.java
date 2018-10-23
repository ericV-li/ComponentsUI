package com.eric.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.eric.ui.common.BaseActivity;
import com.eric.view.ButtonTextView;
import com.eric.view.dialog.NumberKeyboardDialog;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: NumberKeyboardActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class NumberKeyboardActivity extends BaseActivity {

    private ButtonTextView button1;
    private ButtonTextView button2;
    private ButtonTextView button3;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    private NumberKeyboardDialog dialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_keyboard);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberKeyboardDialog dialog = new NumberKeyboardDialog(NumberKeyboardActivity.this, new
                        NumberKeyboardDialog.OnKeyListener() {


                            @Override
                            public void onKey(String clicked) {
                                Log.e("hant", clicked);
                            }

                            @Override
                            public boolean onCheck(String checkStr) {

                                button1.setText(checkStr);
                                return true;
                            }

                            @Override
                            public void onCommit() {
                                Log.e("hant", "onCommit");
                            }
                        });
                dialog.show();
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberKeyboardDialog dialog = new NumberKeyboardDialog(NumberKeyboardActivity.this, new
                        NumberKeyboardDialog.OnKeyListener() {


                            @Override
                            public void onKey(String clicked) {
                                editText1.setText(clicked);
                                editText1.setSelection(clicked.length());

                            }

                            @Override
                            public boolean onCheck(String checkStr) {
                                return true;
                            }

                            @Override
                            public void onCommit() {
                                button2.setText(editText1.getText().toString());

                            }
                        }, true);
                View view = LayoutInflater.from(NumberKeyboardActivity.this).inflate(R.layout.view_keyboard_header, null);
                editText1 = view.findViewById(R.id.edt_input);
                dialog.addHeader(view);
                dialog.show();
            }
        });


        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog3 = new NumberKeyboardDialog(NumberKeyboardActivity.this, new NumberKeyboardDialog.OnKeyListener() {


                    @Override
                    public void onKey(String clicked) {
                        if (editText2 != null && editText2.hasFocus()) {
                            editText2.setText(clicked);
                            editText2.setSelection(clicked.length());
                        }
                        if (editText3 != null && editText3.hasFocus()) {
                            editText3.setText(clicked);
                            editText3.setSelection(clicked.length());
                        }
                    }

                    @Override
                    public boolean onCheck(String checkStr) {
                        return true;
                    }

                    @Override
                    public void onCommit() {
                        button3.setText(editText2.getText().toString() + "-" + editText3.getText().toString());
                    }
                }, true);
                View view = LayoutInflater.from(NumberKeyboardActivity.this).inflate(R.layout.view_keyboard_header2, null);
                editText2 = view.findViewById(R.id.edt_input1);
                editText2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (editText2 != null) {
                            dialog3.setInput(editText2.getText().toString());
                        }
                        return false;
                    }
                });
                editText3 = view.findViewById(R.id.edt_input2);
                editText3.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (editText3 != null) {
                            dialog3.setInput(editText3.getText().toString());
                        }
                        return false;
                    }
                });
                dialog3.addHeader(view);
                dialog3.show();
            }
        });

    }
}
