package cn.skadoosh.tx.web;

import cn.skadoosh.tx.web.dto.R;

/**
 * create by jimmy
 * 11/17/2018 10:23 AM
 */
public class BaseController {

    protected <T> R<T> success(){
        return new R<T>();
    }

}
