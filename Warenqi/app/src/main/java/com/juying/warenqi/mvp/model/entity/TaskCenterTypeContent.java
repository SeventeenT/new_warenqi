package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @LvYan
 * Description:
 * Date: 2017/6/25 16:17
 * </pre>
 */
public class TaskCenterTypeContent {
    private String taskType;
    private int taskTaskCount;
    private int taskTaskIconResId;

    public TaskCenterTypeContent(String taskType, int taskTaskIconResId) {
        this.taskType = taskType;
        this.taskTaskIconResId = taskTaskIconResId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTaskTaskCount() {
        return taskTaskCount;
    }

    public void setTaskTaskCount(int taskTaskCount) {
        this.taskTaskCount = taskTaskCount;
    }

    public int getTaskTaskIconResId() {
        return taskTaskIconResId;
    }

    public void setTaskTaskIconResId(int taskTaskIconResId) {
        this.taskTaskIconResId = taskTaskIconResId;
    }
}
