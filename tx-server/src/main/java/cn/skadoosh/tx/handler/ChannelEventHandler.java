package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.ChannelManger;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.ChannelHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * create by jimmy
 * 11/16/2018 3:07 PM
 */
public class ChannelEventHandler implements ChannelHandler {
    @Autowired
    private ChannelManger channelManger;

    @Override
    public void onConnect(TxChannel ctx) {

    }

    @Override
    public void onDisconnect(TxChannel ctx) {

    }
}
