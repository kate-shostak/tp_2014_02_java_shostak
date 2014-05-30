package messagesystem;

import dbwiththreads.DBservice;
import frontend.FrontendWithThreads;

/**
 * Created by kate on 21.04.14.
 */
public class AddressService {
    private Address dbServiceAddress;
    private Address frontendWithThreadsAddress;

    public Address getDbServiceAddressAddress() {
        return dbServiceAddress;
    }

    public Address getFrontendWithThreadsAddress() {
        return frontendWithThreadsAddress;
    }

    public void setDbServiceAddress(DBservice dbService) {
        this.dbServiceAddress = dbService.getAddress();
    }

    public void setFrontendWithThreadsAddress(FrontendWithThreads frontendWithThreads) {
        this.frontendWithThreadsAddress = frontendWithThreads.getAddress();
    }
}
