package com.eric.view.wheelWidget;

import com.eric.view.interf.WheelAdapterInterf;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.wheelWidget
 * @Title: ArrayWheelAdapter
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public class ArrayWheelAdapter<T> implements WheelAdapterInterf {
    /** The default items length */
    public static final int DEFAULT_LENGTH = 4;

    // items
    private List<T> items;
    // length
    private int length;

    /**
     * Constructor
     * @param items the items
     * @param length the max items length
     */
    public ArrayWheelAdapter(List<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     * @param items the items
     */
    public ArrayWheelAdapter(List<T> items) {
        this(items, DEFAULT_LENGTH);
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o){
        return items.indexOf(o);
    }
}
