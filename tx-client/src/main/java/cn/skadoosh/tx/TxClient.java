package cn.skadoosh.tx;

import cn.skadoosh.tx.core.message.Heartbeat;
import cn.skadoosh.tx.handler.ReconnectHandler;
import cn.skadoosh.tx.transport.Dispatcher;
import cn.skadoosh.tx.transport.TxChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by jimmy
 * 11/16/2018 4:14 PM
 */
@Setter
@Slf4j
public class TxClient {
    private String server;
    private int port;
    private int readTimeout;

    private HashedWheelTimer wheelTimer = new HashedWheelTimer();

    private AtomicBoolean connecting = new AtomicBoolean(false);
    private AtomicBoolean alive = new AtomicBoolean(false);
    private AtomicInteger tryCounter = new AtomicInteger(0);


    private Dispatcher dispatcher;
    private EventLoopGroup loopGroup;
    private Bootstrap bootstrap;

    private TxChannel channel;

    private Timer heartbeatTimer;


    /**
     *
     */
    public void connect() {
        if (!connecting.get()) {
            stopHeartbeat();
            log.info("try connecting No.[{}]....", tryCounter.getAndIncrement());
            connecting.set(true);
            new Thread(() -> this.boot()).start();

        }
    }

    public void boot() {
        bootstrap = new Bootstrap();
        loopGroup = new NioEventLoopGroup();
        bootstrap.group(loopGroup);
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("readTimeoutHnadler", new ReadTimeoutHandler(readTimeout))
                                .addLast("lengthEncoder", new LengthFieldPrepender(4, false))
                                .addLast("lengthDecoder", new LengthFieldBasedFrameDecoder(4096, 0, 4, 0, 4))
                                .addLast("encoder", new ObjectEncoder())
                                .addLast("decoder", new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
                                .addLast("reconnectHandler", new ReconnectHandler(TxClient.this))
                                .addLast("dispatcher", dispatcher);
                    }
                });

        bootstrap.remoteAddress(server, port);
        try {
            Channel channel = bootstrap.connect().sync().channel();
            this.channel = new TxChannel(channel);
            log.debug("connected!");
            startHeartbeat(this.channel);
            this.alive.set(true);
            this.tryCounter.set(0);
            this.connecting.set(false);
        } catch (Exception e) {
            log.error("connect fail", e);
            this.connecting.set(false);
            shutdown();
            try {
                TimeUnit.SECONDS.sleep(10L);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            connect();
        }

    }

    private void startHeartbeat(TxChannel channel) {
        heartbeatTimer = new Timer();
        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.debug("sending heartbeat...");
                channel.write(new Heartbeat());
            }
        }, 6000, 10000);
    }

    private void stopHeartbeat() {
        if (heartbeatTimer != null) {
            heartbeatTimer.cancel();
            heartbeatTimer = null;
        }
    }


    public void shutdown() {
        if (loopGroup != null) {
            loopGroup.shutdownGracefully();
        }
    }

}
