package producerconsumertest;

public class TheMain {

    public static void main(String[] args) {

        MessagePool pool = new MessagePool(10); // init with size of Pool

        Producer p1 = new Producer(pool, "P1", 100); // msgPool, name, amount of msg
        Consumer c1 = new Consumer(pool, "C1", 100);
        p1.start();
        c1.start();

    }
}
