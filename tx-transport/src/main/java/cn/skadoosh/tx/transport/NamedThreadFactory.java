package cn.skadoosh.tx.transport;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by jimmy
 * 11/16/2018 2:37 PM
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String baseName;
    private final AtomicInteger counter = new AtomicInteger(0);

    public NamedThreadFactory(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = Executors.defaultThreadFactory().newThread(r);

        t.setName(baseName + "-" + counter.getAndIncrement());

        return t;
    }
}
