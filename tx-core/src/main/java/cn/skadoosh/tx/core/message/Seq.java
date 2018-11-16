package cn.skadoosh.tx.core.message;

import java.io.Serializable;

/**
 * create by jimmy
 * 11/15/2018 9:56 PM
 */
public interface Seq extends Serializable {
    /**
     * 消息流水号
     * @return
     */
    String getId();

}
