package com.juying.warenqi.mvp.model.entity;

import java.util.List;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/19 13:39
 * </pre>
 */
public class TakeTask {

    private String finishedRate;
    private boolean isTake;
    private int taskFlowCount;
    private int sdTaskId;
    private int taskId;
    private int daysTaskId;
    private String buyerAccountId;
    private String taskType;
    private String online;
    private List<BuyerAccount> buyerAccount;

    public String getFinishedRate() {
        return finishedRate;
    }

    public void setFinishedRate(String finishedRate) {
        this.finishedRate = finishedRate;
    }

    public boolean isTake() {
        return isTake;
    }

    public void setTake(boolean take) {
        isTake = take;
    }

    public int getTaskFlowCount() {
        return taskFlowCount;
    }

    public void setTaskFlowCount(int taskFlowCount) {
        this.taskFlowCount = taskFlowCount;
    }

    public int getSdTaskId() {
        return sdTaskId;
    }

    public void setSdTaskId(int sdTaskId) {
        this.sdTaskId = sdTaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getDaysTaskId() {
        return daysTaskId;
    }

    public void setDaysTaskId(int daysTaskId) {
        this.daysTaskId = daysTaskId;
    }

    public String getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(String buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public List<BuyerAccount> getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(List<BuyerAccount> buyerAccount) {
        this.buyerAccount = buyerAccount;
    }
}
