package com.eric.view.entity;

import java.io.Serializable;

/**
 * @author li
 * @Package com.eric.view.entity
 * @Title: ListItem
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public class ListItem implements Serializable {
    /**
     * 标题
     */
    private String title;

    /**
     * 编号
     */
    private String code;

    /**
     * 是否被选择
     */
    private boolean selected;

    /**
     * 获取标题
     *
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编号
     *
     * @param code 编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 是否被选中
     *
     * @return 是否被选中 true:选中,false:未选中
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * 设置是否选中
     *
     * @param selected 是否选中
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
