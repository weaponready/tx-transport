package cn.skadoosh.tx.config;

import cn.skadoosh.tx.ChannelManger;
import cn.skadoosh.tx.TxServer;
import cn.skadoosh.tx.handler.AuthHandler;
import cn.skadoosh.tx.handler.ChannelEventHandler;
import cn.skadoosh.tx.handler.DataMessageHandler;
import cn.skadoosh.tx.handler.ExceptionHandler;
import cn.skadoosh.tx.transport.Dispatcher;
import cn.skadoosh.tx.transport.handler.ChannelHandler;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by jimmy
 * 11/16/2018 3:16 PM
 */
@Configuration
public class ServerConfig {

    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Value("${netty.timeout}")
    private int readTimeout;

    @Bean
    public ChannelHandler channelHandler(){
        return new ChannelEventHandler();
    }

    @Bean
    public AuthHandler authHandler(){
        return new AuthHandler();
    }

    @Bean
    public ChannelManger channelManger(){
        return new ChannelManger();
    }

    @Bean
    public DataMessageHandler dataMessageHandler(){
        return new DataMessageHandler();
    }

    @Bean
    public ExceptionHandler exceptionHandler(){
        return new ExceptionHandler();
    }

    @Bean
    public Dispatcher dispatcher(ChannelHandler channelHandler,
                           AuthHandler authHandler,
                           DataMessageHandler messageHandler,
                           ExceptionHandler exceptionHandler
                           ){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setChannelHandlers(Lists.newArrayList(channelHandler));
        dispatcher.setExceptionHandlers(Lists.newArrayList(exceptionHandler));
        dispatcher.setMsgHandlers(Lists.newArrayList(authHandler, messageHandler));
        return dispatcher;
    }

    @Bean
    public TxServer server(Dispatcher dispatcher){
        TxServer server = new TxServer();
        server.setHost(host);
        server.setPort(port);
        server.setReadTimeout(readTimeout);
        server.setDispatcher(dispatcher);
        server.boot();
        return server;
    }

}
