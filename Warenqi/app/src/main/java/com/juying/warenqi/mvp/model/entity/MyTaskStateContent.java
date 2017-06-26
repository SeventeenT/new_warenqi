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
    private String taskType;
    private int taskTaskCount;

    public MyTaskStateContent(String taskType, String taskStatus, int taskTaskCount) {
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskTaskCount = taskTaskCount;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getTaskTaskCount() {
        return taskTaskCount;
    }

    public void setTaskTaskCount(int taskTaskCount) {
        this.taskTaskCount = taskTaskCount;
    }

}
