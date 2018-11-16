package cn.skadoosh.tx;

import cn.skadoosh.tx.handler.HeartbeatRespHandler;
import cn.skadoosh.tx.transport.Dispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * create by jimmy
 * 11/16/2018 1:50 PM
 */
@Slf4j
@Setter
public class TxServer {

    private String host;
    private int port;
    private int readTimeout = 60;
    private int writeTimeout = 60;

    private Dispatcher dispatcher;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void boot() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(readTimeout))
                                .addLast("lengthEncoder", new LengthFieldPrepender(4, false))
                                .addLast("lengthDecoder", new LengthFieldBasedFrameDecoder(4096, 0, 4, 0 ,4))
                                .addLast("encoder", new ObjectEncoder())
                                .addLast("decoder", new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
                                .addLast("heartbeat", new HeartbeatRespHandler())
                                .addLast("writeTimeoutHandler", new WriteTimeoutHandler(writeTimeout))
                                .addLast("dispatcher", dispatcher);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = null;
        try {
            future = bootstrap.bind(host, port).sync();

            future.addListener(f -> {
                if (f.isSuccess()) {
                    log.info("server boot success, binding on {}:{}", host, port);
                } else {
                    log.error("server boot failed.");
                }
            });
            future.channel().closeFuture();

        } catch (Exception e) {
            log.error("boot error", e);
            if (future != null) {
                future.channel().close();
            }
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown(){
        if(bossGroup!=null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup!=null){
            workerGroup.shutdownGracefully();
        }
    }

}
