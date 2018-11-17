package cn.skadoosh.tx.core.message;

import lombok.Data;

/**
 * create by jimmy
 * 11/15/2018 10:01 PM
 */
@Data
public class Response<T> extends Packet {

    private int code = 200;
    private String message;
    private T data;

    public boolean success(){
        return this.code == 200;
    }

}
