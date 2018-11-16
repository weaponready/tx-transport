package cn.skadoosh.tx.transport.handler;

import cn.skadoosh.tx.transport.TxChannel;
import io.netty.channel.ChannelHandlerContext;

/**
 * create by jimmy
 * 11/16/2018 3:17 PM
 */
public class MessageHandlerAdapter<T> implements MessageHandler<T>  {

    @Override
    public boolean supports(Object msg) {
        return true;
    }

    @Override
    public boolean onMessage(T message, TxChannel ctx) {
        return false;
    }
}
