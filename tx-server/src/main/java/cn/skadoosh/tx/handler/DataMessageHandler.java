package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.core.message.DataMessage;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.MessageHandlerAdapter;

/**
 * create by jimmy
 * 11/16/2018 3:40 PM
 */
public class DataMessageHandler extends MessageHandlerAdapter<DataMessage> {

    @Override
    public boolean supports(Object msg) {
        return msg instanceof DataMessage;
    }

    @Override
    public boolean onMessage(DataMessage message, TxChannel ctx) {
        //插入数据库
        return super.onMessage(message, ctx);
    }
}
