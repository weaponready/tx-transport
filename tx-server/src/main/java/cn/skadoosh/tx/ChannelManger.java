package cn.skadoosh.tx;

import cn.skadoosh.tx.transport.TxChannel;
import com.google.common.collect.Sets;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;

import java.util.Set;

/**
 * create by jimmy
 * 11/16/2018 3:02 PM
 */
@Data
public class ChannelManger{

    private Set<TxChannel> clients = Sets.newConcurrentHashSet();

    public void add(TxChannel channel){
        clients.add(channel);
    }
    public void remove(TxChannel channel){
        clients.remove(channel);
    }


}
