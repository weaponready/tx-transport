package cn.skadoosh.tx.transport.handler;

import cn.skadoosh.tx.transport.TxChannel;
import io.netty.channel.ChannelHandlerContext;

/**
 * create by jimmy
 * 11/16/2018 2:42 PM
 */
public interface ChannelHandler {

    void onConnect(TxChannel ctx);

    void onDisconnect(TxChannel ctx);


}
