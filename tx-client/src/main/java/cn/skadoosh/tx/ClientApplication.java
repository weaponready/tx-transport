package cn.skadoosh.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * create by jimmy
 * 11/16/2018 4:14 PM
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ClientApplication.class, args);
        new CountDownLatch(1).await();
    }
}
