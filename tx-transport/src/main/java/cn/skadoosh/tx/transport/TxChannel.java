package cn.skadoosh.tx.transport;

import cn.skadoosh.tx.core.message.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;

/**
 * create by jimmy
 * 11/16/2018 3:09 PM
 */
@Data
public class TxChannel {
    private Channel ctx;
    private String name;
    private String ip;
    private int port;
    private long timestamp;

    public TxChannel(Channel ctx) {
        this.ctx = ctx;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 异步发送
     * @param msg
     */
    public void write(Object msg){
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    /**
     * 同步发送并等待结果
     */
    public Response ask(){
        return null;
    }

    public void close(){
        this.ctx.close();
    }
}
