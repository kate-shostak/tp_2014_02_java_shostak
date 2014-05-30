package messagesystem;

import interfaces.Abonent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kate on 19.04.14.
 */
public class MessageManager {
    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    public AddressService addressService;

    public MessageManager(AddressService addressService) {
        this.addressService = addressService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void addService(Abonent abonent) {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

    public void sendMessage(Message message) {
        Queue<Message> messagesToReciver = messages.get(message.getReciever());
        messagesToReciver.add(message);
    }

    public void executeForAbonent(Abonent abonent) throws SQLException {
        Queue<Message> abonentMessages = messages.get(abonent.getAddress());
        if (abonentMessages == null) {
            return;
        }
        while (!abonentMessages.isEmpty()) {
            Message message = abonentMessages.poll();
            message.execute(abonent);
        }
    }

}
