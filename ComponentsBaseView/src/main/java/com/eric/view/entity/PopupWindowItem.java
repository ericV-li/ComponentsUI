package com.eric.view.entity;

/**
 * @author li
 * @Package com.eric.view.entity
 * @Title: PopupWindowItem
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public class PopupWindowItem {
    /**
     * 标题
     */
    private String title;

    /**
     * 编号
     */
    private String code;

    /**
     * 需要显示图片的资源ID
     */
    private int drawableId;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
