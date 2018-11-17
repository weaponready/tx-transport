package cn.skadoosh.tx.web.dto;

import lombok.Data;

/**
 * create by jimmy
 * 11/17/2018 10:21 AM
 */
@Data
public class R<T> {
    private int code = 200;
    private String message;
    private T data;

    public R<T> code(int code){
        this.setCode(code);
        return this;
    }

    public R<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public R<T> data(T data){
        this.setData(data);
        return this;
    }
}
