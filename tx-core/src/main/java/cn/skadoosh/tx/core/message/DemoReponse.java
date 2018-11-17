package cn.skadoosh.tx.core.message;

import lombok.Data;

/**
 * create by jimmy
 * 11/17/2018 10:29 AM
 */
@Data
public class DemoReponse extends Response {

    private String answer;

    public DemoReponse(String greece) {
        this.answer = "hello," + greece;
    }
}
