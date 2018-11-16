package cn.skadoosh.tx.transport;


import cn.skadoosh.tx.transport.handler.ChannelHandler;
import cn.skadoosh.tx.transport.handler.ExceptionHandler;
import cn.skadoosh.tx.transport.handler.MessageHandler;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create by jimmy
 * 11/16/2018 2:30 PM
 */
@Slf4j
@io.netty.channel.ChannelHandler.Sharable
@Setter
public class Dispatcher extends ChannelInboundHandlerAdapter {
    private static final AttributeKey<TxChannel> CHANNEL = AttributeKey.valueOf("CHANNEL_WRAPPER");

    private Executor executor = new ThreadPoolExecutor(60, 500, 60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(600),
            new NamedThreadFactory("skadoosh")
    );

    private List<MessageHandler> msgHandlers = Lists.newArrayList();
    private List<ChannelHandler> channelHandlers = Lists.newArrayList();
    private List<ExceptionHandler> exceptionHandlers = Lists.newArrayList();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel actived", ctx);
        TxChannel channel = new TxChannel(ctx.channel());
        ctx.channel().attr(CHANNEL).set(channel);
        executor.execute(() -> {
            try {
                for (ChannelHandler handler : channelHandlers) {
                    handler.onConnect(channel);
                }
            } catch (Exception e) {
                caught(channel, e);
            }
        });
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel disconnect {}", ctx);
        TxChannel channel = ctx.channel().attr(CHANNEL).get();
        executor.execute(() -> {
            try {
                for (ChannelHandler handler : channelHandlers) {
                    handler.onDisconnect(channel);
                }
            } catch (Exception e) {
                caught(channel, e);
            }
        });
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TxChannel channel = ctx.channel().attr(CHANNEL).get();
        executor.execute(() -> {
            long start = System.currentTimeMillis();
            try {
                for (MessageHandler handler : msgHandlers) {
                    if (handler.supports(msg)) {
                        boolean pass = handler.onMessage(msg, channel);
                        if (!pass) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                caught(channel, e);
            } finally {
                log.debug("channel:{}, cost:[{}]ms msg:{}", ctx, System.currentTimeMillis() - start, msg);
            }
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        TxChannel channel = ctx.channel().attr(CHANNEL).get();
        this.caught(channel, cause);
        super.exceptionCaught(ctx, cause);
    }


    private void caught(TxChannel channel, Throwable t) {
        executor.execute(() -> {
            for (ExceptionHandler handler : exceptionHandlers) {
                handler.caught(channel, t);
            }
        });
    }
}
