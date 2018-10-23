package com.eric.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eric.view.utils.DensityUtils;
import com.eric.view.R;
import com.eric.view.adapter.ListItemAdapter;
import com.eric.view.entity.ListItem;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.dialog
 * @Title: ListComponentDialog
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 显示列表选择样式的弹窗
 */
public class ListComponentDialog {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 弹窗标题
     */
    private String mTitle;

    /**
     * 是否使用蓝色主题
     */
    private boolean mUseBlueStyle;

    /**
     * 列表数据
     */
    private List<ListItem> mList;

    /**
     * 头部title
     */
    private TextView txtTitle;

    /**
     * 弹窗的监听，有三个回调（取消cancel，关闭dismiss，点击onItemClick）
     */
    private OnListDialogListener mListener;

    public ListComponentDialog(Context context) {
        mContext = context;
    }

    private ListItemAdapter mListItemAdapter;

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * 设置数据
     *
     * @param list 数据列表
     */
    public void setDataSource(List<ListItem> list) {
        mList = list;
    }

    /**
     * 显示dialog
     */
    public void show() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.baseview_list_dialog, null);
        final Dialog dialog = new Dialog(mContext, R.style.dialogWidthMatchParent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListener != null) {
                    mListener.onCancel();
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mListener != null) {
                    mListener.onDismissed();
                }
            }
        });
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialogAnimStyle);
        dialog.show();
        txtTitle = (TextView) contentView.findViewById(R.id.title);
        View viewDivider = contentView.findViewById(R.id.titleDivider);
        txtTitle.setText(mTitle);
        txtTitle.setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        viewDivider.setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        contentView.findViewById(R.id.titleDivider).setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        final ListView listView = (ListView) contentView.findViewById(R.id.listview);
        setListViewHeight(listView, mTitle, mList);
        listView.setHeaderDividersEnabled(!TextUtils.isEmpty(mTitle));
        mListItemAdapter = new ListItemAdapter(mContext, mList, mUseBlueStyle);
        listView.setAdapter(mListItemAdapter);
        View footer = new View(mContext);
        footer.setBackgroundColor(mContext.getResources().getColor(R.color.baseview_app_bg_color));
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils
                .dip2px(mContext, 10));
        footer.setLayoutParams(layoutParams);
        listView.addFooterView(footer);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int realPosition = position - listView.getHeaderViewsCount();
                if (realPosition < 0 || realPosition >= mListItemAdapter.getCount()) {
                    return;
                }
                if (mListener != null) {
                    mListener.onItemClick(position, mListItemAdapter.getItem(realPosition));
                }
                dialog.dismiss();
            }
        });
        TextView textView = (TextView) contentView.findViewById(R.id.txt_cancel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCancel();
                }
                dialog.dismiss();
            }
        });
    }

    private void setListViewHeight(final ListView listView, String title, List<ListItem> mList) {

        //获取屏幕高度
        Point point = new Point();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getSize(point);
        //dialog最高高度是屏幕的2/3
        int maxDialogHeight = point.y * 2 / 3;
        //ListView高度 = 最大dialog高度 - dialog的title - dialog的取消按钮
        int headerHeight = TextUtils.isEmpty(title) ? 0 : DensityUtils.dip2px(mContext, 48);
        final int maxListViewHeight = maxDialogHeight - headerHeight - DensityUtils.dip2px(mContext, 48);
        if (maxListViewHeight < DensityUtils.dip2px(mContext, 56) * mList.size()) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
            layoutParams.height = maxListViewHeight;
            listView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置是否使用蓝色样式
     *
     * @param useBlueStyle 是否使用蓝色样式
     */
    public void setUseBlueStyle(boolean useBlueStyle) {
        mUseBlueStyle = useBlueStyle;
    }

    /**
     * 设置监听
     *
     * @param listener 弹窗的监听，有三个回调（取消cancel，关闭dismiss，点击onItemClick）
     */
    public void setOnListDialogListener(OnListDialogListener listener) {
        mListener = listener;
    }

    /**
     * 弹窗的监听，有三个回调（取消cancel，关闭dismiss，点击onItemClick）
     */
    public interface OnListDialogListener {

        /**
         * 关闭
         */
        void onDismissed();

        /**
         * 取消
         */
        void onCancel();

        /**
         * 点击item
         *
         * @param position 位置
         * @param item     item
         */
        void onItemClick(int position, ListItem item);
    }

    /**
     * 返回title控件
     * @return
     */
    public TextView getTitleView(){
        return txtTitle;
    }
}
