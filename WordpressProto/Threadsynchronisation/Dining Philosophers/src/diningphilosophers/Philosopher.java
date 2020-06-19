/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Birgit
 */
public class Philosopher extends Thread {

    Forks forks;
    int id;
    int forkLeft;
    int forkRight;
    int nrDishes;

    public Philosopher(Forks f, int id, int nrDishes) {
        forks = f;
        this.id = id;
        if (id == 0) {
            this.forkRight = id;
            this.forkLeft = (id + 1) % Main.NR_PHILOSOPHERS;
        } else {
            this.forkLeft = id;
            this.forkRight = (id + 1) % Main.NR_PHILOSOPHERS; //der letzte bekommt die erste Gabel 

        }
        this.nrDishes = nrDishes;
    }

    @Override
    public void run() {

        for (int i = 0; i < nrDishes; i++) {
            think(1);
            // Try to start eating
            print("wants fork " + forkLeft);
            forks.take(forkLeft);
            think(1);
            print("wants fork " + forkRight);
            forks.take(forkRight);

            // Eating and thinking
            print("eating " + i);
            think(2);

            // finished with eating
            forks.place(forkLeft);
            think(1);
            forks.place(forkRight);

            print("finished dish " + i);
        }
    }

    private void think(int time) {
        try {
            this.sleep((long)(time * 1000.0));
            //this.sleep((long) (time * 1000.0 * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void print(String text) {
        String blanks = "";
        for (int i = 0; i < id * 2; i++) {
            blanks += " ";
        }
        System.out.println(blanks + id + " " + text);
    }
}
