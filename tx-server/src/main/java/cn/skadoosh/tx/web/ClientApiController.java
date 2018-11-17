package cn.skadoosh.tx.web;

import cn.skadoosh.tx.ChannelManger;
import cn.skadoosh.tx.core.message.DemoCmd;
import cn.skadoosh.tx.core.message.Heartbeat;
import cn.skadoosh.tx.core.message.Packet;
import cn.skadoosh.tx.transport.TxChannel;
import cn.skadoosh.tx.web.dto.Device;
import cn.skadoosh.tx.web.dto.R;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * create by jimmy
 * 11/17/2018 10:25 AM
 */
@RestController
@RequestMapping("api/client")
public class ClientApiController extends BaseController {
    @Autowired
    private ChannelManger channelManger;


    @GetMapping
    public R list(){
        List<Device> devices = Lists.newArrayList();
        Set<TxChannel> clients = channelManger.getClients();
        for (TxChannel client : clients) {
            Device device = new Device();
            device.setIp(client.getIp());
            device.setPort(client.getPort());
            devices.add(device);
        }
        return success().data(devices);
    }

    @PostMapping("broadcast")
    public R broadcast(){
        Packet heartbeat = new Heartbeat();
        for (TxChannel client : channelManger.getClients()) {
            client.write(heartbeat);
        }
        return success();
    }

    @PostMapping("cmd")
    public R cmd(String name) throws Exception {
        DemoCmd greece = new DemoCmd();
        greece.setGreece(name);
        Set<TxChannel> clients = channelManger.getClients();
        if(clients.size()>0){
            return success().data(clients.iterator().next().write(greece).getValue());
        }
        return success();
    }

}
