package cn.skadoosh.tx.core.message;

import lombok.Data;

/**
 * create by jimmy
 * 11/15/2018 9:57 PM
 */
@Data
public class Packet implements Seq{

    private String id;
    private long timestamp;


}
