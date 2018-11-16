package cn.skadoosh.tx.core.message;

import cn.skadoosh.tx.common.Identities;

/**
 * create by jimmy
 * 11/16/2018 6:35 PM
 */
public class Heartbeat extends Packet {

    public Heartbeat() {
        setId(Identities.uuid());
    }
}
