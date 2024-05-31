package org.app.bot.telegram.subscriber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;

public interface LatchableSubscriber<T> extends Flow.Subscriber<T> {
    void setLatch(CountDownLatch latch);
}
