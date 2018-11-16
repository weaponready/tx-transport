package cn.skadoosh.tx.core.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * create by jimmy
 * 11/15/2018 9:56 PM
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest extends Packet{

    private String ip;
    private int port;
    private String node;
    private String token;

}
