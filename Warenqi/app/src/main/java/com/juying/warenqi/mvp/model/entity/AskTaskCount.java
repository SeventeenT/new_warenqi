package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/26 14:12
 * </pre>
 */
public class AskTaskCount {
    private int APPEAL;
    private int CANCEL;
    private int FINISHED;
    private int WAIT_BUYER_SUBMIT;
    private int WAIT_SELLER;

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

    public int getWAIT_BUYER_SUBMIT() {
        return WAIT_BUYER_SUBMIT;
    }

    public void setWAIT_BUYER_SUBMIT(int WAIT_BUYER_SUBMIT) {
        this.WAIT_BUYER_SUBMIT = WAIT_BUYER_SUBMIT;
    }

    public int getWAIT_SELLER() {
        return WAIT_SELLER;
    }

    public void setWAIT_SELLER(int WAIT_SELLER) {
        this.WAIT_SELLER = WAIT_SELLER;
    }
}
