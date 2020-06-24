package main;

import counter.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CounterTest {

    public static void main(String[] args) {
        Thread t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15;
        int maxCount = 100_000_000;

        // Vererbungsversion
        // Treads erzeugen
        t1 = new CounterExtStillNot("Tick ", maxCount);
        t2 = new CounterExtStillNot("Trick", maxCount);
        t3 = new CounterExtStillNot("Track", maxCount);

        // Threads starten
        t1.start();
        t2.start();
        t3.start();

        // Zusammenwarten
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CounterTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Test CounterExt fertig!");

        // Schnittstellenversion mit synchronize
        // Threads erzeugen
        t4 = new Thread(new CounterImpSync("Ene  ", maxCount));
        t5 = new Thread(new CounterImpSync("Mene ", maxCount));
        t6 = new Thread(new CounterImpSync("Mu   ", maxCount));

        // Threads starten
        t4.start();
        t5.start();
        t6.start();

        // Zusammenwarten
        try {
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CounterTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Test CounterImpSync fertig!");

        // Schnittstellenversion mit Lock
        // Threads erzeugen
        t7 = new Thread(new CounterImpLock("Ene  ", maxCount));
        t8 = new Thread(new CounterImpLock("Mene ", maxCount));
        t9 = new Thread(new CounterImpLock("Mu   ", maxCount));

        // Threads starten
        t7.start();
        t8.start();
        t9.start();

        // Zusammenwarten
        try {
            t7.join();
            t8.join();
            t9.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CounterTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        t10 = new Thread(new CounterImpLock("atomic1  ", maxCount));
        t11 = new Thread(new CounterImpLock("atomic2  ", maxCount));
        t12 = new Thread(new CounterImpLock("atomic3  ", maxCount));

        t10.start();
        t11.start();
        t12.start();
        try {
            t10.join();
            t11.join();
            t12.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        t13 = new Thread(new CounterImpBusyWaiting("CounterImpBusyWaiting 1 ", maxCount));
        t14 = new Thread(new CounterImpBusyWaiting("CounterImpBusyWaiting  2", maxCount));
        t15 = new Thread(new CounterImpBusyWaiting("CounterImpBusyWaiting  3", maxCount));

        t13.start();
        t14.start();
        t15.start();
        try {
            t13.join();
            t14.join();
            t15.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Test CounterImpLock fertig!");
    }
}
