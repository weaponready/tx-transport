package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.core.message.DemoCmd;
import cn.skadoosh.tx.core.message.DemoReponse;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.MessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * create by jimmy
 * 11/17/2018 10:32 AM
 */
@Slf4j
public class DemoCmdHandler extends MessageHandlerAdapter<DemoCmd> {

    @Override
    public boolean supports(Object msg) {
        return msg instanceof DemoCmd;
    }

    @Override
    public boolean onMessage(DemoCmd message, TxChannel ctx) {
        log.debug("greece from {}", message.getGreece());
        DemoReponse demoReponse = new DemoReponse(message.getGreece());
        demoReponse.setId(message.getId());
        ctx.write(demoReponse);
        return false;
    }
}
