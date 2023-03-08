package executors;

import java.util.concurrent.*;

/* Do not change this class */
class Message {
    final String text;
    final String from;
    final String to;

    Message(String from, String to, String text) {
        this.text = text;
        this.from = from;
        this.to = to;
    }
}

/* Do not change this interface */
interface AsyncMessageSender {
    void sendMessages(Message[] messages);
    void stop();
}

class AsyncMessageSenderImpl implements AsyncMessageSender {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // TODO initialize the executor
    private final int repeatFactor;

    public AsyncMessageSenderImpl(int repeatFactor) {
        this.repeatFactor = repeatFactor;
    }

    @Override
    public void sendMessages(Message[] messages) {
        for (Message msg : messages) {
            // TODO repeat messages
            for (int i = 0; i < repeatFactor; i++) {
                executor.submit(() -> {
                    System.out.printf("(%s>%s): %s\n", msg.from, msg.to, msg.text); // do not change it
                });
            }
        }
    }

    @Override
    public void stop() {
        executor.shutdown();

        // TODO stop the executor and wait for it
    }
    public static void notifyAboutEnd() throws InterruptedException {
        boolean terminated = executor.awaitTermination(60, TimeUnit.MILLISECONDS);

        if (terminated) {
            System.out.println("The executor was successfully stopped");
        } else {
            System.out.println("Timeout elapsed before termination");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AsyncMessageSender sender = new AsyncMessageSenderImpl(3);

        Message[] messages = {

                new Message("John", "Mary", "Hello!"),
                new Message("Clara", "Bruce", "How are you today?")
        };

        sender.sendMessages(messages);
        sender.stop();

        notifyAboutEnd(); // it prints something after the sender successfully stop
    }
}
