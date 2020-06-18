package diningphilosophers;


import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Birgit
 */
public class Forks {
    
    int nrForks;
    Semaphore[] forkSemaphore;
    
    public Forks(int n){ 
        nrForks = n;
        forkSemaphore = new Semaphore[nrForks];
        for (int i = 0; i < nrForks; i++){
            forkSemaphore[i] = new Semaphore(1);
        }
    }
    
    public void take(int i){
        forkSemaphore[i].acquireUninterruptibly();
        print(i, "=> acquired");
    }
    
    public void place(int i){
        forkSemaphore[i].release();
        print(i, "=> released");
    }

    private void print(int nr, String text) {
        String blanks = "";
        for (int i = 0; i < (nr*2); i++){
            blanks += " ";
        }
        System.out.println(blanks + text + " " + nr);
    }   
}
