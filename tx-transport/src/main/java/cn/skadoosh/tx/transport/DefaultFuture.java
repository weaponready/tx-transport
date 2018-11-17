package cn.skadoosh.tx.transport;

import cn.skadoosh.tx.core.message.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * create by jimmy
 * 11/16/2018 7:59 PM
 */
@Slf4j
public class DefaultFuture<T> implements ResponseFuture<T> {

    private volatile FutureState state = FutureState.DOING;
    private T result = null;

    private Object lock = new Object();
    private Exception exception = null;
    protected List<FutureListener> listeners;
    private long createTime = System.currentTimeMillis();
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private Packet request;

    public DefaultFuture(Packet request) {
        this.request = request;
    }

    @Override
    public void onSuccess(T response) {
        this.result = response;
        done();
    }

    @Override
    public void onFailure(Exception e) {
        this.exception = e;
        done();
    }

    @Override
    public boolean cancel() {
        countDownLatch.countDown();
        state = FutureState.CANCELLED;
        notifyListeners();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return state.isCancelledState();
    }

    @Override
    public boolean isDone() {
        return state.isDoneState();
    }

    @Override
    public boolean isSuccess() {
        return isDone() && (exception == null);
    }

    @Override
    public T getValue() throws Exception {
        countDownLatch.await();
        if (this.exception != null) {
            throw this.exception;
        }
        return this.result;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public void addListener(FutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("FutureListener is null");
        }

        boolean notifyNow = false;
        synchronized (lock) {
            if (!isDoing()) {
                notifyNow = true;
            } else {
                if (listeners == null) {
                    listeners = new ArrayList<>(1);
                }

                listeners.add(listener);
            }
        }

        if (notifyNow) {
            notifyListener(listener);
        }
    }

    private boolean isDoing() {
        return state.isDoingState();
    }

    protected boolean done() {
        countDownLatch.countDown();
        this.state = FutureState.DONE;
        notifyListeners();
        return true;
    }

    public String getId() {
        return this.request.getId();
    }

    private void notifyListeners() {
        if (listeners != null) {
            for (FutureListener listener : listeners) {
                notifyListener(listener);
            }
        }
    }

    private void notifyListener(FutureListener listener) {
        try {
            listener.complete(this);
        } catch (Throwable t) {
            log.error("notifyListener Error: ", t);
        }
    }
}
