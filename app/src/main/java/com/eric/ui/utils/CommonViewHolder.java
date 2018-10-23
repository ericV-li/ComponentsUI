package com.eric.ui.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 通用ViewHolder，直接在Adapter的getView时，将要想获取到的View的id传进来即可
 */
public class CommonViewHolder {

    /**
     * @param view View视图对象
     * @param id   资源ID
     * @param <T>  泛型View
     * @return ViewHolder缓存View对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            if (childView != null) {
                viewHolder.put(id, childView);
            }
        }
        return (T) childView;
    }
}
