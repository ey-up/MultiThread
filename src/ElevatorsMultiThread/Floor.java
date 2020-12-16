package ElevatorsMultiThread;

import java.util.concurrent.BlockingQueue;

public class Floor {

    private int insideCount;
    private int id;
    private BlockingQueue<Element> floorQueue;

    public int getFloorQueueCount() {
        int result = 0;
        for (Element element : floorQueue) {
            result = result + element.getPeopleCount();
        }
        return result;
    }

    public int getInsideCount() {
        return insideCount;
    }

    public void addElementToQueue(Element e) {
        floorQueue.add(e);
    }

    public void setInsideCount(int insideCount) {
        this.insideCount = insideCount;
    }

    public void addInsideCount(int insideCount) {
        this.insideCount = this.insideCount + insideCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlockingQueue<Element> getFloorQueue() {
        return floorQueue;
    }

    public void setFloorQueue(BlockingQueue<Element> floorQueue) {
        this.floorQueue = floorQueue;
    }

    public Floor(int insideCount, int id, BlockingQueue<Element> floorQueue) {
        this.insideCount = insideCount;
        this.id = id;
        this.floorQueue = floorQueue;
    }

}
