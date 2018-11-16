package cn.skadoosh.tx.transport.handler;

import cn.skadoosh.tx.transport.TxChannel;
import io.netty.channel.ChannelHandlerContext;

/**
 * create by jimmy
 * 11/16/2018 2:40 PM
 */
public interface MessageHandler<T> {

    boolean supports(Object msg);

    boolean onMessage(T message, TxChannel ctx);

}
