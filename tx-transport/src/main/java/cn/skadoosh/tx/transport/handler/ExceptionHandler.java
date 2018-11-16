package cn.skadoosh.tx.transport.handler;

import cn.skadoosh.tx.transport.TxChannel;

/**
 * create by jimmy
 * 11/16/2018 2:47 PM
 */
public interface ExceptionHandler {

    void caught(TxChannel channel, Throwable t);
}
