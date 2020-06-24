package counter;


import java.time.*;


/**
 * Multi-Threaded Counter (Vererbungsversion)
 * <p>
 * @author Widmann, Manfred Dipl.-Ing.
 */
public class CounterExtStillNot extends Thread {

  private static int totalCount = 0;
  private int maxTotalCount;


  /**
   * Konstruktor aus String, int
   * <p>
   * @param name          Name des Threads
   * @param maxTotalCount Zählerobergrenze
   */
  public CounterExtStillNot(String name, int maxTotalCount) {
    super(name);
    this.maxTotalCount = maxTotalCount;
  }


  /**
   * Asynchrones Hochzählen bis Zählerobergrenze. <br>
   * Diese Methode wird über den Thread-Mechanismus asynchron gestartet und
   * beginnt, die gemeinsame Klassenvariable und zu Kontrollzwecken auch eine
   * lokale Variable hochzuzählen. Sie kann jederzeit unterbrochen und wieder
   * gestartet werden - leider jedoch nicht fehlerlos. Eigentlich müsste nämlich
   * am Ende die Summe aller lokalen Zähler (myOwnCount) gleich der gemeinsam
   * hochgezählten Klassenvariablen totalCount sein. Da diese Methode bzw. die
   * Klassenvariable aber nicht threadsicher sind, ergibt sich hier eine
   * Differenz! Das "synchronized" der Methode run() bringt leider keine
   * Abhilfe, weil das bewirkt, dass das this-Objekt nur von einem Thread zu
   * einer Zeit betreten werden darf. Dieses Objekt gibt es aber ohnehin
   * mehrfach - nämlich je Thread, somit ist die Einschränkung unwirksam.
   * <p>
   * @see java.lang.Runnable#run()
   */
  @Override
  synchronized public void run() {
    int myOwnCount = 0;
    Instant beginn, ende;

    beginn = Instant.now();

    while (totalCount < maxTotalCount) {
      myOwnCount++;
      totalCount++;
      // System.out.println(name + ": " + myOwnCount + "/" + totalCount);
    }

    ende = Instant.now();

    System.out.println("Endstand von " + getName() + " (extends)   : "
            + myOwnCount + "/" + totalCount + "/" + Duration.between(beginn, ende).toNanos() / 1000000);

  } // run
} // CounterExtStillNot
