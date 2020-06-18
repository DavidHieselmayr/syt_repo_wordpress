package producerconsumertest;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagePool {

    private String[] pool;
    private int indexForProduction; //
    private int indexForConsumer; //
    private final Semaphore availableElementsForConsumption;//Wie viele wurden genereiert
    private final Semaphore availableSpaceForProduction;//Wie viele können generiert werden

    public MessagePool(int size) {
        pool = new String[size];
        indexForProduction = 0;
        indexForConsumer = 0;
        this.availableElementsForConsumption = new Semaphore(0, false);
        this.availableSpaceForProduction = new Semaphore(size, false);

    }

    /**
     * *
     * get() is used from the consumerF
     *
     * @return
     */
    public String get() {
        String value = "";
        try {
            this.availableElementsForConsumption.acquire(); // Der Counter wird erniedrig um 1
        } catch (InterruptedException ex) {
            Logger.getLogger(MessagePool.class.getName()).log(Level.SEVERE, null, ex);
        }

        synchronized (this) {
            value = pool[indexForConsumer];
            indexForConsumer = (indexForConsumer + 1) % pool.length;
        }
        this.availableSpaceForProduction.release(); // Der Counter wird erhöht um 1
        return value;
    }

    /**
     * *
     * put is used from the producer to put an object to the data pool
     *
     * @param value
     */
    public void put(String value) {
        try {
            this.availableSpaceForProduction.acquire();//ernidriegen (der aktuelle wert wird minus 1 gerechnet)
        } catch (InterruptedException ex) {
            Logger.getLogger(MessagePool.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized (this) {//der synchronized block ist notwendig weil es sich um eine critcial section handelt, sprich
            // 1) Es gibt mehr als einen Thread 
            //2) die auf eine gemeinsame Ressource zugreifen wollen
            pool[indexForProduction] = value;
            indexForProduction = (indexForProduction + 1) % pool.length;
        }

        this.availableElementsForConsumption.release();//Erhöht den Counter um eins

    }
}
