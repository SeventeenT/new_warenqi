package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/26 13:56
 * </pre>
 */
public class FlowTaskCount {
    private int APPEAL;
    private int CANCEL;
    private int FINISHED;
    private int SELLER_PROCESS;
    private int WAIT;

    public int getAPPEAL() {
        return APPEAL;
    }

    public void setAPPEAL(int APPEAL) {
        this.APPEAL = APPEAL;
    }

    public int getCANCEL() {
        return CANCEL;
    }

    public void setCANCEL(int CANCEL) {
        this.CANCEL = CANCEL;
    }

    public int getFINISHED() {
        return FINISHED;
    }

    public void setFINISHED(int FINISHED) {
        this.FINISHED = FINISHED;
    }

    public int getSELLER_PROCESS() {
        return SELLER_PROCESS;
    }

    public void setSELLER_PROCESS(int SELLER_PROCESS) {
        this.SELLER_PROCESS = SELLER_PROCESS;
    }

    public int getWAIT() {
        return WAIT;
    }

    public void setWAIT(int WAIT) {
        this.WAIT = WAIT;
    }
}
