package messagesystem;

/**
 * Created by kate on 21.04.14.
 */
public class TimeHelper {
    public static void sleep(int period) {
        try {
            Thread.sleep(period);
        } catch (InterruptedException exeption) {//Wherefore?
            exeption.printStackTrace();
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException exeption) {
            exeption.printStackTrace();
        }
    }
}
