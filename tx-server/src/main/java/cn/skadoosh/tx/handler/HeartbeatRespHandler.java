package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.core.message.Heartbeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳响应处理
 * create by jimmy
 * 11/16/2018 6:34 PM
 */
@Slf4j
public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Heartbeat){
            ctx.writeAndFlush(msg);
        }
        ctx.fireChannelRead(msg);

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof TimeoutException){
            log.debug("[{}] timeout closing...", ctx.channel().remoteAddress());
            ctx.close();
        }else{
            ctx.fireExceptionCaught(cause);
        }
    }
}
