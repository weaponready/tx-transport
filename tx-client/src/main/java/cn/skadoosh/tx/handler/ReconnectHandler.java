package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.TxClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * create by jimmy
 * 11/16/2018 4:35 PM
 */
@Slf4j
@ChannelHandler.Sharable
public class ReconnectHandler extends ChannelInboundHandlerAdapter {
    private TxClient client;

    public ReconnectHandler(TxClient client) {
        this.client = client;
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.connect();
        super.channelInactive(ctx);
    }

}
