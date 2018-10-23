package com.eric.view.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.view.adapter
 * @Title: BaseSimpleAdapter
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public abstract class BaseSimpleAdapter<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context context;
    /**
     * 数据列表
     */
    protected List<T> data;

    /**
     * 构造器
     *
     * @param context 上下文
     * @param data    数据（List）
     */
    public BaseSimpleAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    }

    /**
     * 获取adapter数据个数
     *
     * @return 数据个数（不包括header和footer）
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获取数据实体
     *
     * @param position 位置。想要获取数据实体的索引
     * @return 数据实体
     */
    @Override
    public T getItem(int position) {
        if (position < 0 || position >= data.size()) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id。
     * PS：当Item有多种Type的时候  用getItemResource(int position)方法。
     *
     * @return layout id
     */
    public abstract int getItemResource();


    /**
     * 该方法需要子类实现，需要返回item布局的resource id. 当Item有多种Type的时候  用getItemResource(int position)方法。
     *
     * @param position 位置
     * @return layout的resourceId
     */
    public int getItemResource(int position) {
        return getItemResource();
    }


    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position    位置
     * @param convertView convertView
     * @param holder      holder
     * @return View
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, getItemResource(position), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    /**
     * 数据持有者，为的是listview滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
     */
    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

    /**
     * 添加集合数据到集合
     *
     * @param elem 集合数据
     */
    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据到集合
     *
     * @param elem 单个数据
     */
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据到集合头部
     *
     * @param elem 单个数据
     */
    public void addFirst(T elem) {
        data.add(0, elem);
        notifyDataSetChanged();
    }


    /**
     * 移除单个数据
     *
     * @param elem 要移除的数据
     */
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    /**
     * 移除单个数据
     *
     * @param index 要移除数据的索引
     */
    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 替换列表数据
     *
     * @param elem 替换的数据集合
     */
    public void replaceAll(List<T> elem) {
        data.clear();
        if (elem != null) {
            data.addAll(elem);
        }
        notifyDataSetChanged();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
}
