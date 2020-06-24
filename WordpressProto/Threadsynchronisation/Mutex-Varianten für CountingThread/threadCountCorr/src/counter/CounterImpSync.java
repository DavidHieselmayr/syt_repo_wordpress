package counter;

import java.time.*;

/**
 * Multi-Threaded Counter (Schnittstellenversion)
 * <p>
 * @author Widmann, Manfred Dipl.-Ing.
 */
public class CounterImpSync implements Runnable {

    private static int totalCount = 0;
    private int maxTotalCount;
    private String name;

    /**
     * Konstruktor aus String, int
     * <p>
     * @param name Name des Threads
     * @param maxTotalCount Z�hlerobergrenze
     */
    public CounterImpSync(String name, int maxTotalCount) {
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
     * gemeinsam genutzte Ressource synchronisiert werden. Einen Monitor zu
     * finden ist gar nicht leicht: Alle zu synchronisierenden Threads müssen
     * ihn kennen! Nachdem totalCount eine Klassenvariable ist, bietet sich
     * dafür die Klasse selbst an, auf die man eine Referenz mit
     * Object.getClass() bekommt.
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

            synchronized (getClass()) { // Monitor = Klassenobjekt!
                if (totalCount < maxTotalCount) {
                    myOwnCount++;
                    totalCount++;
                }

            }
            // System.out.println(name + ": " + myOwnCount + "/" + totalCount);
        }

        ende = Instant.now();

        System.out.println("Endstand von " + name + " (implements): " + myOwnCount
                + "/" + totalCount + "/" + Duration.between(beginn, ende).toNanos() / 1000000);
    } // run
} // CounterImpSync
