package com.eric.view.wheelWidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.eric.view.R;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.wheelWidget
 * @Title: WheelViewDialogHelper
 * @Description: Copyright (c)
 * Create DateTime: 2018/10/19
 */
public class WheelViewDialogHelper {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 左侧滚轮
     */
    private WheelView viewOption1;

    /**
     * 中间滚轮
     */
    private WheelView viewOption2;

    /**
     * 右侧滚轮
     */
    private WheelView viewOption3;

    /**
     * 滚轮弹窗
     */
    private WheelViewDialog mDialog;

    /**
     * 左侧列表数据内容
     */
    private List<String> mOptions1Items;

    /**
     * 中间列表数据内容
     */
    private List<List<String>> mOptions2Items;

    /**
     * 右侧列表数据内容
     */
    private List<List<List<String>>> mOptions3Items;

    /**
     * 左侧滚轮滚动回调
     */
    private OnItemSelectedListener mOption1WheelListener;

    /**
     * 中间滚轮滚动回调
     */
    private OnItemSelectedListener mOption2WheelListener;

    /**
     * 是否是联动。联动前提，左侧、中间、右侧数据匹配，滚轮数据 List<String> ， 数据List<List<String>>，数据List<List<List<String>>>。
     */
    private boolean mLinkage = false;

    /**
     * 左侧滚轮循环滚动
     */
    private boolean mOption1Cycle;
    /**
     * 中间滚轮循环滚动
     */
    private boolean mOption2Cycle;
    /**
     * 右侧滚轮循环滚动
     */
    private boolean mOption3Cycle;
    /**
     * 左侧滚轮开始位置
     */
    private int mOption1StartPosition;
    /**
     * 中间滚轮开始位置
     */
    private int mOption2StartPosition;
    /**
     * 右侧滚轮开始位置
     */
    private int mOption3StartPosition;

    private int mTextSize;


    public WheelViewDialogHelper(Context context) {

        mContext = context;
    }

    /**
     * 设置数据
     *
     * @param optionsItems 左侧滚轮数据
     */
    public void setPicker(List<String> optionsItems) {
        setPicker(optionsItems, null, null, false);
    }

    /**
     * 设置数据
     *
     * @param options1Items 左侧滚轮数据
     * @param options2Items 中间滚轮数据
     * @param linkage       是否联动 注意：联动前提，左侧、中间、右侧数据匹配，滚轮数据 List<String> ，
     *                      数据List<List<String>>，数据List<List<List<String>>>。
     */
    public void setPicker(List<String> options1Items, List<List<String>> options2Items, boolean linkage) {
        setPicker(options1Items, options2Items, null, linkage);
    }

    /**
     * 设置数据
     *
     * @param options1Items 左侧滚轮数据
     * @param options2Items 中间滚轮数据
     * @param options3Items 右侧滚轮数据
     * @param linkage       是否联动 注意：联动前提，左侧、中间、右侧数据匹配，滚轮数据 List<String> ，
     *                      数据List<List<String>>，数据List<List<List<String>>>。
     */
    public void setPicker(List<String> options1Items, List<List<String>> options2Items, List<List<List<String>>> options3Items,
                          boolean linkage) {
        this.mLinkage = linkage;
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        if (mDialog != null) {
            mDialog.initDataAndView();
        }
    }

    /**
     * 显示dialog
     */
    public void show() {
        if (mDialog == null) {
            mDialog = new WheelViewDialog(mContext);
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialogAnimStyle);
        }
        mDialog.show();
    }

    /**
     * 设置选中的item位置
     *
     * @param option1 左侧滚轮选中位置
     */
    public void setSelectOptions1(int option1) {
        if (viewOption1 != null) {
            viewOption1.setCurrentItem(option1);
        }
    }

    /**
     * 设置选中的item位置
     *
     * @param option2 中间滚轮选中位置
     */
    public void setSelectOptions2(int option2) {
        if (viewOption2 != null) {
            viewOption2.setCurrentItem(option2);
        }
    }

    /**
     * 设置选中的item位置
     *
     * @param option3 右侧滚轮选中位置
     */
    public void setSelectOptions3(int option3) {
        if (viewOption3 != null) {
            viewOption3.setCurrentItem(option3);
        }
    }

    /**
     * 设置选中的item位置，其他位置默认0
     *
     * @param option1 左侧轮选中位置
     */
    public void setSelectOptions(int option1) {
        setSelectOptions(option1, 0, 0);
    }

    /**
     * 设置选中的item位置，右侧滚轮默认0
     *
     * @param option1 左侧滚轮选中位置
     * @param option2 中间滚轮选中位置
     */
    public void setSelectOptions(int option1, int option2) {
        setSelectOptions(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1 左侧滚轮选中位置
     * @param option2 中间滚轮选中位置
     * @param option3 右侧滚轮选中位置
     */
    public void setSelectOptions(int option1, int option2, int option3) {

        if (viewOption1 != null) {
            viewOption1.setCurrentItem(option1);
        }
        if (viewOption2 != null) {
            viewOption2.setCurrentItem(option2);
        }
        if (viewOption3 != null) {
            viewOption3.setCurrentItem(option3);
        }
    }

    /**
     * 设置滚轮当前位置
     *
     * @param option1 左侧滚轮位置
     * @param option2 中间滚轮位置
     * @param option3 右侧滚轮位置
     */
    public void setCurrentItems(int option1, int option2, int option3) {
        mOption1StartPosition = option1;
        mOption2StartPosition = option2;
        mOption3StartPosition = option3;
    }

    /**
     * 设置是否全部循环滚动
     *
     * @param cyclic 是否
     */
    public void setCyclic(boolean cyclic) {
        setCyclic(cyclic, cyclic, cyclic);
    }

    /**
     * 设置各滚轮是否循环滚动
     *
     * @param cyclic1 左侧滚轮是否循环
     * @param cyclic2 中间滚轮是否循环
     * @param cyclic3 右侧滚轮是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        mOption1Cycle = cyclic1;
        mOption2Cycle = cyclic2;
        mOption3Cycle = cyclic3;
    }

    /**
     * 时间滚轮弹窗
     */
    class WheelViewDialog extends Dialog {

        private ArrayWheelAdapter option1Adapter;
        private ArrayWheelAdapter option2Adapter;
        private ArrayWheelAdapter option3Adapter;

        public WheelViewDialog(@NonNull Context context) {
            this(context, R.style.dialogWidthMatchParent);
        }

        public WheelViewDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.baseview_dialog_wheelview_pickerview_options);
            initWindowMeasure();
            initView();
            initDataAndView();
            setListener();
        }


        /**
         * 初始化window尺寸
         */
        private void initWindowMeasure() {
            Window window = getWindow();
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            onWindowAttributesChanged(wl);
        }

        private void initView() {
            viewOption1 = (WheelView) findViewById(R.id.options1);
            viewOption2 = (WheelView) findViewById(R.id.options2);
            viewOption3 = (WheelView) findViewById(R.id.options3);
        }


        private void setListener() {

            findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isDismiss = true;
                    if (mOptionsSelectListener != null) {
                        isDismiss = mOptionsSelectListener.onOptionsSelect(viewOption1.getCurrentItem(),
                                viewOption2.getCurrentItem(), viewOption3.getCurrentItem());
                    }
                    if (isDismiss) {
                        dismiss();
                    }
                }
            });
        }

        /**
         * 初始化数据
         */
        public void initDataAndView() {
            initOption1();
            initOption2();
            initOption3();
        }

        /**
         * 初始化左侧滚轮
         */
        private void initOption1() {
            option1Adapter = new ArrayWheelAdapter(mOptions1Items);
            viewOption1.setAdapter(option1Adapter);// 设置显示数据
            viewOption1.setCyclic(mOption1Cycle);
            viewOption1.setCurrentItem(mOption1StartPosition);
            viewOption1.setTextSize(mTextSize);
            // 联动监听器.如果是数据级联 就用默认的级联监听器
            if (mLinkage) {
                mOption1WheelListener = new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(int index) {
                        int opt2Select = 0;
                        if (mOptions2Items != null) {
                            opt2Select = viewOption2.getCurrentItem();//上一个opt2的选中位置
                            //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                            opt2Select = opt2Select >= mOptions2Items.get(index).size() - 1 ? mOptions2Items.get(index).size()
                                    - 1 : opt2Select;

                            viewOption2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(index)));
                            viewOption2.setCurrentItem(opt2Select);
                        }
                        if (mOptions3Items != null) {
                            mOption2WheelListener.onItemSelected(opt2Select);
                        }
                    }
                };
            }
            // 添加联动监听
            viewOption1.setOnItemSelectedListener(mOption1WheelListener);
        }

        /**
         * 初始化中间滚轮
         */
        private void initOption2() {
            // 选项2
            if (mOptions2Items != null) {
                option2Adapter = new ArrayWheelAdapter(mLinkage ? mOptions2Items.get(mOption1StartPosition) : mOptions2Items
                        .get(0));
                viewOption2.setAdapter(option2Adapter);// 设置显示数据
                viewOption2.setCyclic(mOption2Cycle);
                viewOption2.setCurrentItem(mOption2StartPosition);
            }
            viewOption2.setTextSize(mTextSize);
            if (mOptions2Items == null) {
                viewOption2.setVisibility(View.GONE);
            } else {
                viewOption2.setVisibility(View.VISIBLE);
            }
            // 联动监听器.如果是数据级联 就用默认的级联监听器
            if (mLinkage) {
                mOption2WheelListener = new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(int index) {
                        if (mOptions3Items != null) {
                            int opt1Select = viewOption1.getCurrentItem();
                            opt1Select = opt1Select >= mOptions3Items.size() - 1 ? mOptions3Items.size() - 1 : opt1Select;
                            index = index >= mOptions2Items.get(opt1Select).size() - 1 ? mOptions2Items.get(opt1Select).size()
                                    - 1 : index;
                            int opt3 = viewOption3.getCurrentItem();//上一个opt3的选中位置
                            //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                            opt3 = opt3 >= mOptions3Items.get(opt1Select).get(index).size() - 1 ? mOptions3Items.get
                                    (opt1Select).get(index).size() - 1 : opt3;

                            viewOption3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(viewOption1.getCurrentItem()).get
                                    (index)));
                            viewOption3.setCurrentItem(opt3);
                        }
                    }
                };
            }

            // 添加联动监听
            viewOption2.setOnItemSelectedListener(mOption2WheelListener);
        }

        /**
         * 初始化右侧滚轮
         */
        private void initOption3() {

            // 选项3
            if (mOptions3Items != null) {
                option3Adapter = new ArrayWheelAdapter(mLinkage ? mOptions3Items.get(mOption1StartPosition).get
                        (mOption2StartPosition) : mOptions3Items.get(0).get(0));
                viewOption3.setAdapter(option3Adapter);// 设置显示数据
                viewOption3.setCyclic(mOption3Cycle);
                viewOption3.setCurrentItem(mOption3StartPosition);
            }
            viewOption3.setTextSize(mTextSize);
            if (mOptions3Items == null) {
                viewOption3.setVisibility(View.GONE);
            } else {
                viewOption3.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setOption1WheelListener(OnItemSelectedListener option1WheelListener) {
        mOption1WheelListener = option1WheelListener;
    }

    public void setOption2WheelListener(OnItemSelectedListener option2WheelListener) {
        mOption2WheelListener = option2WheelListener;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    private OnOptionsSelectListener mOptionsSelectListener;

    /**
     * 时间控件选中监听
     */
    public interface OnOptionsSelectListener {

        /**
         * @param options1 时间控件选中的position
         * @param options2 时间控件选中的position
         * @param options3 时间控件选中的position
         * @return 是否关闭Dialog
         */
        boolean onOptionsSelect(int options1, int options2, int options3);
    }

    /**
     * 设置时间控件选中监听
     *
     * @param optionsSelectListener
     */
    public void setOnOptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
        this.mOptionsSelectListener = optionsSelectListener;
    }
}
