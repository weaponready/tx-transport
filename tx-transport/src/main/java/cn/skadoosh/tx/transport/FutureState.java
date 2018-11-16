package cn.skadoosh.tx.transport;

/**
 * create by jimmy
 * 11/16/2018 8:01 PM
 */
public enum FutureState {
    /**
     * the task is doing
     **/
    DOING(0),
    /**
     * the task is done
     **/
    DONE(1),
    /**
     * ths task is cancelled
     **/
    CANCELLED(2);

    public final int value;

    FutureState(int value) {
        this.value = value;
    }

    public boolean isCancelledState() {
        return this == CANCELLED;
    }

    public boolean isDoneState() {
        return this == DONE;
    }

    public boolean isDoingState() {
        return this == DOING;
    }
}
