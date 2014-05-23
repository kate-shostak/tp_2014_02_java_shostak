package messagesystem;

import frontend.FrontendWithThreads;
import interfaces.Abonent;

/**
 * Created by kate on 19.04.14.
 */
public abstract class MessageToFrontend extends Message {
    public MessageToFrontend(Address recievedFrom, Address sendTo) {
        super(recievedFrom, sendTo);
    }

    public void execute(Abonent abonent) {
        if (abonent instanceof FrontendWithThreads) {
            execute((FrontendWithThreads) abonent);
        }
    }

    abstract void execute(FrontendWithThreads frontendWithThreads);
}
