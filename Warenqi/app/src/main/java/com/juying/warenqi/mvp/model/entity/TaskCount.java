package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/19 16:23
 * </pre>
 */
public class TaskCount {
    private int hdCount;
    private int orderCount;
    private int ztcCount;
    private int dfCount;
    private int daysDfCount;

    public int getHdCount() {
        return hdCount;
    }

    public void setHdCount(int hdCount) {
        this.hdCount = hdCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getZtcCount() {
        return ztcCount;
    }

    public void setZtcCount(int ztcCount) {
        this.ztcCount = ztcCount;
    }

    public int getDfCount() {
        return dfCount;
    }

    public void setDfCount(int dfCount) {
        this.dfCount = dfCount;
    }

    public int getDaysDfCount() {
        return daysDfCount;
    }

    public void setDaysDfCount(int daysDfCount) {
        this.daysDfCount = daysDfCount;
    }
}
