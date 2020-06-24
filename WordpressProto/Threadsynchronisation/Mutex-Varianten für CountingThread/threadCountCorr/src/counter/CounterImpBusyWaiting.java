package counter;

import java.time.*;
import java.util.concurrent.locks.*;

/**
 * Multi-Threaded Counter (Schnittstellenversion)
 * <p>
 * @author Widmann, Manfred Dipl.-Ing.
 */
public class CounterImpBusyWaiting implements Runnable {

    private static int totalCount = 0;
    private int maxTotalCount;
    private String name;
    private static boolean lock = false;

    private static Lock l = new ReentrantLock();
    //private static Object obj = new Object();

    /**
     * Konstruktor aus String, int
     * <p>
     * @param name Name des Threads
     * @param maxTotalCount Z�hlerobergrenze
     */
    public CounterImpBusyWaiting(String name, int maxTotalCount) {
        this.name = name;
        this.maxTotalCount = maxTotalCount;
    }

    /**
     * Asynchrones Hochzählen bis Zählerobergrenze. <br>
     * Diese Methode wird über den Thread-Mechanismus asynchron gestartet und
     * beginnt, die gemeinsame Klassenvariable und zu Kontrollzwecken auch eine
     * lokale Variable hochzuzählen. Sie kann jederzeit unterbrochen und wieder
     * gestartet werden - leider jedoch nicht fehlerlos. Eigentlich müsste
     * nämlich am Ende die Summe aller lokalen Zähler (myOwnCount) gleich der
     * gemeinsam hochgezählten Klassenvariablen totalCount sein. Da diese
     * Methode bzw. die Klassenvariable aber nicht threadsicher sind, ergibt
     * sich hier eine Differenz!
     * <br>
     * Endlich kann Java zählen - dazu muss klarerweise der Zugriff auf die
     * gemeinsam genutzte Ressource synchronisiert werden. Die modernste Lösung
     * ist die Verwendung eines Lock-Objektes, das hier die Aufgabe des Monitors
     * übernimmt.
     * <br>
     * Aber: beachte die Laufzeit! Faktor 50!
     * <p>
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        int myOwnCount = 0;
        Instant beginn, ende;

        beginn = Instant.now();

        while (totalCount < maxTotalCount) {
            myOwnCount++;

            while (lock) {
                ;
            }
            lock = true;

            if (totalCount < maxTotalCount) {
                totalCount++;

            }
            lock = false;

            totalCount++;

            /*            synchronized (obj) {
                myOwnCount++;
                totalCount++;

            }*/
            // System.out.println(name + ": " + myOwnCount + "/" + totalCount);
        }

        ende = Instant.now();

        System.out.println("Endstand von " + name + " (implements): " + myOwnCount
                + "/" + totalCount + "/"
                + Duration.between(beginn, ende).toNanos() / 1000000);
    } // run
} // CounterImp
