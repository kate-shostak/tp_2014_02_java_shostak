package messagesystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kate on 19.04.14.
 */
public class Address {
    static private AtomicInteger addressCreator = new AtomicInteger();
    final private int abonentAddress;

    public Address() {
        this.abonentAddress = addressCreator.incrementAndGet();
    }
}
