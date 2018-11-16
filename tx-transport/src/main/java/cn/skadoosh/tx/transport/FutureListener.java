package cn.skadoosh.tx.transport;

/**
 * create by jimmy
 * 11/16/2018 7:54 PM
 */
public interface FutureListener {

    void complete(Future future) throws Exception;
}
