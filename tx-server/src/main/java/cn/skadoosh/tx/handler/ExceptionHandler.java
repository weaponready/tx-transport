package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.transport.TxChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * create by jimmy
 * 11/16/2018 3:43 PM
 */
@Slf4j
public class ExceptionHandler implements cn.skadoosh.tx.transport.handler.ExceptionHandler {

    @Override
    public void caught(TxChannel channel, Throwable t) {

    }
}
