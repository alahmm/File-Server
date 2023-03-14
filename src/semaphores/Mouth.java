package semaphores;
import java.util.Scanner;
import java.util.concurrent.*;

class Mouth extends Thread{

    private final Semaphore semaphore;
    private final long timeout; // ms
    //declare your attributes here

    public Mouth(Character name, Semaphore semaphore, long timeout) {
        super(String.valueOf(name));
        this.semaphore = semaphore;
        this.timeout = timeout;

        //init your attributes here
    }

    // Update the method
    public void first() throws InterruptedException {
        System.out.print("I "); // Do not change or remove this line
    }

    // Update the method
    public void second() throws InterruptedException {
        System.out.print("love "); // Do not change or remove this line
    }

    // Update the method
    public void third() throws InterruptedException {
        System.out.print("programming!"); // Do not change or remove this line
    }
    public void ToLove() {
        start();
    }

    @Override
    public void run() {
        try {
            if (semaphore.tryAcquire()) {
                first();
                semaphore.acquire();
            }
            second();
            Thread.sleep(timeout); // shopping
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ": there is a problem here");
            e.printStackTrace();
        } finally {
            try {
                third();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
        }
    }

}
class Main {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(3);
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        String string = scanner.next();
        //for (int i = 0; i < 5; i += 2) {
            Mouth mouth = new Mouth(string.charAt(0), sem, 3000L);

            mouth.ToLove();
        //}
    }
}

