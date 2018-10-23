package com.eric.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eric.view.adapter.BaseSimpleAdapter;
import com.eric.view.entity.PopupWindowItem;
import com.eric.view.utils.DensityUtils;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.ListPopWindow
 * @Title: ListPopWindow
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 用于列表的popupwindow
 */

public class ListPopWindow {

    /**
     * 背景的弹窗 底部阴影
     */
    private PopupWindow background;

    /**
     * 前景弹窗 内容列表
     */
    private PopupWindow foreground;

    /**
     * 不带图标数据默认宽度
     */
    private final int DEFAULT_WIDTH = 180;

    public ListPopWindow(Context context, List<PopupWindowItem> data) {
        initBackGround(context);
        initForeGround(context, data);
    }


    /**
     * 构造背景
     *
     * @param context 上下文
     */
    private void initBackGround(Context context) {
        TextView textView = new TextView(context);
        textView.setBackgroundColor(Color.parseColor("#33000000"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPopWindow.this.dismiss();
            }
        });
        background = new PopupWindow(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initForeGround(Context context, final List<PopupWindowItem> data) {

        if (data == null || data.isEmpty()) {
            return;
        }
        int width = getItemMaxWidth(context, data);
        ExpandListView expandListView = new ExpandListView(context);
        expandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onItemSelect(data.get(position));
                }
                dismiss();
            }
        });
        expandListView.setBackgroundResource(R.drawable.baseview_shape_solid_white_corner4);
        expandListView.setDivider(context.getResources().getDrawable(R.drawable.baseview_divider_height1px));
        expandListView.setDividerHeight(1);
        expandListView.setAdapter(new PopupWindowAdapter(context, data));
        foreground = new PopupWindow(expandListView, isHasIcon(data) ? DensityUtils.dip2px(context, DEFAULT_WIDTH) : width, ViewGroup
                .LayoutParams.WRAP_CONTENT);
    }

    /**
     * 获取最大边长
     *
     * @param context 上下文
     * @param data    数据
     * @return item宽度
     */
    private int getItemMaxWidth(Context context, List<PopupWindowItem> data) {
        float maxWidth = 0;
        Paint paint = new Paint();
        paint.setTextSize(16);
        for (PopupWindowItem temp : data) {
            Log.e("item-title", paint.measureText(temp.getTitle()) + "");
            if (paint.measureText(temp.getTitle()) > maxWidth) {
                maxWidth = paint.measureText(temp.getTitle());
            }
        }
        return DensityUtils.dip2px(context, maxWidth + 30);
    }

    /**
     * 数据是否要展示icon
     *
     * @param data 数据
     * @return 是否显示icon
     */
    private boolean isHasIcon(List<PopupWindowItem> data) {
        boolean hasIcon = false;
        for (PopupWindowItem temp : data) {
            if (temp.getDrawableId() > 0) {
                return true;
            }
        }
        return hasIcon;
    }

    /**
     * popupwindow 显示的锚
     *
     * @param anchor 锚点
     */
    public void showAsDropDown(View anchor) {
        background.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        foreground.showAsDropDown(anchor, 0, 0);
    }

    /**
     * popupwindow 显示的锚
     *
     * @param anchor 锚点
     */
    public void showAsDropDown(View anchor, int x, int y) {
        background.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        foreground.showAsDropDown(anchor, x, y);
    }

    /**
     * popupwindow 显示的锚
     *
     * @param token   锚点
     * @param gravity 中心
     * @param x       x坐标位置
     * @param y       y坐标位置
     */
    public void showAtLocation(View token, int gravity, int x, int y) {
        background.showAtLocation(token, Gravity.CENTER, 0, 0);
        foreground.showAtLocation(token, gravity, x, y);
    }

    /**
     * 取消窗口
     */
    public void dismiss() {
        foreground.dismiss();
        background.dismiss();
    }


    private class PopupWindowAdapter extends BaseSimpleAdapter<PopupWindowItem> {

        /**
         * 构造器
         *
         * @param context 上下文
         * @param data    数据（List）
         */
        public PopupWindowAdapter(Context context, List<PopupWindowItem> data) {
            super(context, data);
        }

        @Override
        public int getItemResource() {
            return R.layout.baseview_list_popup_item;
        }

        @Override
        public View getItemView(int position, View convertView, ViewHolder holder) {
            PopupWindowItem item = getItem(position);
            TextView textView = holder.getView(R.id.txt_content);
            textView.setText(item.getTitle());
            if (item.getDrawableId() == 0) {
                textView.setCompoundDrawablePadding(0);
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                textView.setCompoundDrawablePadding(DensityUtils.dip2px(context, 12));
                textView.setCompoundDrawablesWithIntrinsicBounds(item.getDrawableId(), 0, 0, 0);
            }
            return convertView;
        }
    }

    private OnItemSelectListener mListener;

    public OnItemSelectListener getOnItemSelectListener() {
        return mListener;
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.mListener = listener;
    }

    public interface OnItemSelectListener {
        void onItemSelect(PopupWindowItem item);
    }
}
