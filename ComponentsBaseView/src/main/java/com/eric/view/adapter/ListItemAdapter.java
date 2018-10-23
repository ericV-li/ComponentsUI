package com.eric.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.eric.view.R;
import com.eric.view.entity.ListItem;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.adapter
 * @Title: ListItemAdapter
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public class ListItemAdapter extends BaseSimpleAdapter<ListItem>{
    /**
     * 列表数据集，数据实体ListItem
     */
    private List<ListItem> mList;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 是否是蓝色样式。 true：文字颜色为蓝色。false:文字颜色灰色
     */
    private boolean mUseBlueStyle;

    /**
     * 构造器
     *
     * @param context      上下文
     * @param list         数据集合
     * @param useBlueStyle 是否使用蓝色样式
     */
    public ListItemAdapter(Context context, List<ListItem> list, boolean useBlueStyle) {
        super(context, list);
        mContext = context;
        mList = list;
        mUseBlueStyle = useBlueStyle;
    }

    /**
     * 需要返回item布局的resource id. 当Item有多种Type的时候  用getItemResource(int position)方法。
     *
     * @return layout的resourceId
     */
    @Override
    public int getItemResource() {
        return R.layout.baseview_view_lj_list_item;
    }

    /**
     * 获取当前item的View容器
     *
     * @param position    位置索引
     * @param convertView 单个item显示的容器
     * @param holder      数据持有者，为的是listView滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
     * @return item容器
     */
    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        TextView title = holder.getView(R.id.tv_title);
        ListItem en = mList.get(position);
        title.setText(en.getTitle());
        if (mUseBlueStyle) {
            title.setTextColor(mContext.getResources().getColor(R.color.lj_color_blue));
        } else {
            title.setTextColor(mContext.getResources().getColor(R.color.lj_color_black_light));
        }
        return convertView;
    }

}
