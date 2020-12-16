package ElevatorsMultiThread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginThread {

    private BlockingQueue<Element> queue;

    public LoginThread(BlockingQueue<Element> queue) {
        this.queue = queue;
        Thread producerThread = new Thread(() -> {
            produce();
        });
        producerThread.start();
    }

    private void produce() {
        Random random = new Random();
        while (true) {
            try {
                int peopleCount = random.nextInt(10) + 1;
                int floorNumber = random.nextInt(4) + 1;
                queue.add(new Element(peopleCount, floorNumber));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Login.java");
            }
        }
    }
}
