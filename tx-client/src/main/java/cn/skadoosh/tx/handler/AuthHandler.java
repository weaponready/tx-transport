package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.core.message.Response;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.MessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * create by jimmy
 * 11/16/2018 4:45 PM
 */
@Slf4j
public class AuthHandler extends MessageHandlerAdapter<Response> {

    @Override
    public boolean supports(Object msg) {
        return msg instanceof Response;
    }

    @Override
    public boolean onMessage(Response message, TxChannel ctx) {
        log.debug("{}", message);
        return super.onMessage(message, ctx);
    }
}
