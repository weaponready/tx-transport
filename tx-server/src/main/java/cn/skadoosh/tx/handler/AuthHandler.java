package cn.skadoosh.tx.handler;

import cn.skadoosh.tx.ChannelManger;
import cn.skadoosh.tx.core.message.AuthRequest;
import cn.skadoosh.tx.core.message.Response;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.transport.handler.MessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * create by jimmy
 * 11/16/2018 3:16 PM
 */
@Slf4j
public class AuthHandler extends MessageHandlerAdapter<AuthRequest> {

    @Autowired
    private ChannelManger manager;

    @Override
    public boolean supports(Object msg) {
        return msg instanceof AuthRequest;
    }

    @Override
    public boolean onMessage(AuthRequest message, TxChannel channel) {
        //通道鉴权

        //鉴权失败直接关闭
        //ctx.close();
        String token = message.getToken();

        log.debug("token:{}", token);

        channel.setIp(message.getIp());
        channel.setPort(message.getPort());
        channel.setName(message.getNode());

        manager.add(channel);

        log.debug("{}", message);
        //鉴权应答
        Response response = new Response<>();
        response.setId(message.getId());
        channel.write(response);
        return false;
    }
}
