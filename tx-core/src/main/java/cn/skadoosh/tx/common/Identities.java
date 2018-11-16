package cn.skadoosh.tx.common;

import java.util.UUID;

/**
 * create by jimmy
 * 11/17/2018 1:22 AM
 */
public class Identities {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
