package cn.skadoosh.tx.core.message;

import lombok.Data;

/**
 * create by jimmy
 * 11/17/2018 10:28 AM
 */
@Data
public class DemoCmd extends Packet implements Request {

    private String greece;
}
