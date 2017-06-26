package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/25 16:17
 * </pre>
 */
public class MyTaskStateContent {
    private String taskStatus;
    private int taskTaskCount;

    public MyTaskStateContent(String taskType, int taskTaskCount) {
        this.taskStatus = taskType;
        this.taskTaskCount = taskTaskCount;
    }

    public String getTaskType() {
        return taskStatus;
    }

    public void setTaskType(String taskType) {
        this.taskStatus = taskType;
    }

    public int getTaskTaskCount() {
        return taskTaskCount;
    }

    public void setTaskTaskCount(int taskTaskCount) {
        this.taskTaskCount = taskTaskCount;
    }

}
