package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/19 13:40
 * </pre>
 */
public class BuyerAccount {
    private boolean hasTask;
    private boolean hasTaskExecuting;
    public boolean isSelected;
    private int id;
    private int lockedDays;
    private int mouthTakenNum;
    private String nick;
    private int orderNumber;
    private int receivedTaskCount;
    private int totalTakenNum;
    private int userId;
    private int weekTakenNum;

    public boolean isHasTask() {
        return hasTask;
    }

    public void setHasTask(boolean hasTask) {
        this.hasTask = hasTask;
    }

    public boolean isHasTaskExecuting() {
        return hasTaskExecuting;
    }

    public void setHasTaskExecuting(boolean hasTaskExecuting) {
        this.hasTaskExecuting = hasTaskExecuting;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLockedDays() {
        return lockedDays;
    }

    public void setLockedDays(int lockedDays) {
        this.lockedDays = lockedDays;
    }

    public int getMouthTakenNum() {
        return mouthTakenNum;
    }

    public void setMouthTakenNum(int mouthTakenNum) {
        this.mouthTakenNum = mouthTakenNum;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getReceivedTaskCount() {
        return receivedTaskCount;
    }

    public void setReceivedTaskCount(int receivedTaskCount) {
        this.receivedTaskCount = receivedTaskCount;
    }

    public int getTotalTakenNum() {
        return totalTakenNum;
    }

    public void setTotalTakenNum(int totalTakenNum) {
        this.totalTakenNum = totalTakenNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWeekTakenNum() {
        return weekTakenNum;
    }

    public void setWeekTakenNum(int weekTakenNum) {
        this.weekTakenNum = weekTakenNum;
    }
}
