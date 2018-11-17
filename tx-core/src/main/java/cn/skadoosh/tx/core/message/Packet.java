package cn.skadoosh.tx.core.message;

import cn.skadoosh.tx.common.Identities;
import lombok.Data;
import lombok.ToString;

/**
 * create by jimmy
 * 11/15/2018 9:57 PM
 */
@Data
@ToString
public class Packet implements Seq{

    private String id = Identities.uuid();
    private long timestamp = System.currentTimeMillis();


}
