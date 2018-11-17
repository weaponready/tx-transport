package cn.skadoosh.tx.transport;

/**
 * create by jimmy
 * 11/16/2018 7:54 PM
 */
public interface Future<T> {

    String getId();
    /**
     * cancle the task
     *
     * @return
     */
    boolean cancel();

    /**
     * task cancelled
     *
     * @return
     */
    boolean isCancelled();

    /**
     * task is complete : normal or exception
     *
     * @return
     */
    boolean isDone();

    /**
     * isDone() & normal
     *
     * @return
     */
    boolean isSuccess();

    /**
     * if task is success, return the result.
     *
     * @throws Exception when timeout, cancel, onFailure
     * @return
     */
    T getValue() throws Exception;

    /**
     * if task is done or cancle, return the exception
     *
     * @return
     */
    Exception getException();

    /**
     * add future listener , when task is success，failure, timeout, cancel, it will be called
     *
     * @param listener
     */
    void addListener(FutureListener listener);


}
