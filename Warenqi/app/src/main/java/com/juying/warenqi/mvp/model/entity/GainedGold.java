package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/24 15:34
 * </pre>
 */
public class GainedGold {
    private long ingotProcess;//预估收入
    private long inviteIngot;//推广奖励
    private long totalIngot;//任务收入
    private long pledgeIngot;//冻结本金
    private long todayIngot; //新今日收入

    public long getIngotProcess() {
        return ingotProcess;
    }

    public void setIngotProcess(long ingotProcess) {
        this.ingotProcess = ingotProcess;
    }

    public long getInviteIngot() {
        return inviteIngot;
    }

    public void setInviteIngot(long inviteIngot) {
        this.inviteIngot = inviteIngot;
    }

    public long getTotalIngot() {
        return totalIngot;
    }

    public void setTotalIngot(long totalIngot) {
        this.totalIngot = totalIngot;
    }

    public long getPledgeIngot() {
        return pledgeIngot;
    }

    public void setPledgeIngot(long pledgeIngot) {
        this.pledgeIngot = pledgeIngot;
    }

    public long getTodayIngot() {
        return todayIngot;
    }

    public void setTodayIngot(long todayIngot) {
        this.todayIngot = todayIngot;
    }
}
