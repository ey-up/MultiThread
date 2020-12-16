package ElevatorsMultiThread;

import java.util.ArrayList;
import java.util.Random;

public class ExitThread {

    private ArrayList<Floor> floors;

    public ExitThread(ArrayList<Floor> floors) {
        this.floors = floors;
        Thread consumerThread = new Thread(() -> {
            consume();
        });
        consumerThread.start();
    }

    private void consume() {

        while (true) {
            try {
                exit();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Exit.java");
            }
        }
    }

    private synchronized void exit() {
        Random random = new Random();
        int peopleCount = random.nextInt(5) + 1;
        int floorNo = random.nextInt(4) + 1;
        int insideCount = floors.get(floorNo).getInsideCount();
        if (insideCount > 0) {
            if (insideCount > peopleCount) {
                floors.get(floorNo).addElementToQueue(new Element(peopleCount, 0));
                floors.get(floorNo).setInsideCount(insideCount - peopleCount);
            } else {
                floors.get(floorNo).addElementToQueue(new Element(insideCount, 0));
                floors.get(floorNo).setInsideCount(0);
            }
        }
    }
}
