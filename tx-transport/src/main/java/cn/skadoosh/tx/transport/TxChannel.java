package cn.skadoosh.tx.transport;

import cn.skadoosh.tx.core.message.Packet;
import cn.skadoosh.tx.core.message.Request;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.HashedWheelTimer;
import lombok.Data;

import java.util.Map;

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

    private Map<String, ResponseFuture> futures = Maps.newConcurrentMap();
    private HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();

    public TxChannel(Channel ctx) {
        this.ctx = ctx;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 异步发送
     *
     * @param msg
     */
    public Future write(Packet msg) {
        DefaultFuture<Object> future = new DefaultFuture<>(msg);
        ctx.writeAndFlush(msg);
        if(msg instanceof Request) {
            futures.put(msg.getId(), future);
        }
//        hashedWheelTimer.newTimeout(new TimerTask() {
//            @Override
//            public void run(Timeout timeout) throws Exception {
//                ResponseFuture f = futures.remove(msg.getId());
//                f.onFailure(null);
//            }
//        },20, TimeUnit.SECONDS);
        return future;
    }


    public void notify(Packet packet) {
        if (futures.containsKey(packet.getId())) {
            ResponseFuture future = futures.get(packet.getId());
            future.onSuccess(packet);
            futures.remove(packet.getId());
        }
    }


    public void close() {
        this.ctx.close();
    }
}
