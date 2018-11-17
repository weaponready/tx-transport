package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.TxClient;
import cn.skadoosh.tx.core.message.AuthRequest;
import cn.skadoosh.tx.core.message.Response;
import cn.skadoosh.tx.transport.Future;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.ChannelHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * create by jimmy
 * 11/16/2018 4:45 PM
 */
@Slf4j
public class AuthHandler extends ChannelHandlerAdapter {

    private TxClient client;

    public AuthHandler(TxClient client) {
        this.client = client;
    }

    @Override
    public void onConnect(TxChannel ctx) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.getCtx().localAddress();
        log.debug("start auth...");
        Future<Response> future = ctx.write(new AuthRequest(socketAddress.getHostName(), socketAddress.getPort(), "", ""));
        try {
            Response response = future.getValue();
            log.debug("got auth response!");
            if(response.success()){
                log.debug("auth success start heartbeat");
                client.startHeartbeat(ctx);
            }
        } catch (Exception e) {
            log.error("auth fail", e);
        }
    }

    @Override
    public void onDisconnect(TxChannel ctx) {
        log.debug("disconnected! try reconnecting...");
        client.connect();
    }
}
