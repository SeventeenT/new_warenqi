package com.juying.warenqi.mvp.model.entity;

/**
 * <pre>
 * Author: @Administrator
 * Description:
 * Date: 2017/6/26 13:57
 * </pre>
 */
public class AdvancedPayTaskCount {

    /**
     * count : 0
     * searchStatus : APPEAL
     */

    private CountBean APPEAL;
    /**
     * count : 31
     * searchStatus : CANCEL
     */

    private CountBean CANCEL;
    /**
     * count : 4
     * searchStatus : FINISHED
     */

    private CountBean FINISHED;
    /**
     * count : 0
     * searchStatus : SELLER_PROCESS
     */

    private CountBean SELLER_PROCESS;
    /**
     * count : 0
     * searchStatus : WAIT
     */

    private CountBean WAIT;
    /**
     * count : 0
     * searchStatus : WAIT_COMMENT
     */

    private CountBean WAIT_COMMENT;

    public static class CountBean {
        private int count;
        private String searchStatus;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getSearchStatus() {
            return searchStatus;
        }

        public void setSearchStatus(String searchStatus) {
            this.searchStatus = searchStatus;
        }
    }

    public CountBean getAPPEAL() {
        return APPEAL;
    }

    public void setAPPEAL(CountBean APPEAL) {
        this.APPEAL = APPEAL;
    }

    public CountBean getCANCEL() {
        return CANCEL;
    }

    public void setCANCEL(CountBean CANCEL) {
        this.CANCEL = CANCEL;
    }

    public CountBean getFINISHED() {
        return FINISHED;
    }

    public void setFINISHED(CountBean FINISHED) {
        this.FINISHED = FINISHED;
    }

    public CountBean getSELLER_PROCESS() {
        return SELLER_PROCESS;
    }

    public void setSELLER_PROCESS(CountBean SELLER_PROCESS) {
        this.SELLER_PROCESS = SELLER_PROCESS;
    }

    public CountBean getWAIT() {
        return WAIT;
    }

    public void setWAIT(CountBean WAIT) {
        this.WAIT = WAIT;
    }

    public CountBean getWAIT_COMMENT() {
        return WAIT_COMMENT;
    }

    public void setWAIT_COMMENT(CountBean WAIT_COMMENT) {
        this.WAIT_COMMENT = WAIT_COMMENT;
    }
}
