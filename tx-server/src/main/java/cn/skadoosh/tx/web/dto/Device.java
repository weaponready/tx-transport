package cn.skadoosh.tx.web.dto;

import lombok.Data;

/**
 * create by jimmy
 * 11/17/2018 10:36 AM
 */
@Data
public class Device {
    private String ip;
    private int port;
    private long timestamp;
}
