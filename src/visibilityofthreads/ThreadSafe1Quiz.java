package visibilityofthreads;

import java.util.concurrent.TimeUnit;

public class ThreadSafe1Quiz {

    public static void main(final String[] args) throws Exception {

        ThreadSafe1 runnable = new ThreadSafe1();

        new Thread(runnable).start();

        TimeUnit.SECONDS.sleep(5);

        runnable.cancel();
    }

    public static class ThreadSafe1 implements Runnable {

         private volatile boolean done; // sometimes changes are not visible

        @Override
        public void run() {

            while (!done) {
                System.out.println("Running");
            }

            System.out.println("Done");
        }

        public void cancel() {
            done = true;
        }
    }
}
