package cn.skadoosh.tx.config;

import cn.skadoosh.tx.TxClient;
import cn.skadoosh.tx.handler.AuthHandler;
import cn.skadoosh.tx.transport.Dispatcher;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by jimmy
 * 11/16/2018 4:45 PM
 */
@Configuration
public class ClientConfig {

    @Value("${netty.port}")
    private int port;
    @Value("${netty.server}")
    private String server;

    @Bean
    public Dispatcher dispatcher(){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMsgHandlers(Lists.newArrayList(new AuthHandler()));
        return dispatcher;
    }

    @Bean
    public TxClient client(Dispatcher dispatcher){
        TxClient client = new TxClient();
        client.setServer(server);
        client.setPort(port);
        client.setReadTimeout(30);
        client.setDispatcher(dispatcher);
        dispatcher.setMsgHandlers(Lists.newArrayList(new AuthHandler()));
        client.connect();
        return client;
    }

}
