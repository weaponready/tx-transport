package cn.skadoosh.tx.transport;

/**
 * create by jimmy
 * 11/16/2018 7:57 PM
 */
public interface ResponseFuture<T> extends Future<T> {

    void onSuccess(T response);

    void onFailure(Exception e);

}
