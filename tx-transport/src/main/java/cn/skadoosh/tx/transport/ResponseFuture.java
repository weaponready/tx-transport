package cn.skadoosh.tx.transport;

import cn.skadoosh.tx.core.message.Response;

/**
 * create by jimmy
 * 11/16/2018 7:57 PM
 */
public interface ResponseFuture<T> extends Future<T> {

    void onSuccess(Response<T> response);

    void onFailure(Response<T> response);

}
