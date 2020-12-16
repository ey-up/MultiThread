package ElevatorsMultiThread;

import java.util.ArrayList;


public class Elevator {

    private int id;
    private boolean mode;
    private int floor;
    private int destination;
    private boolean direction;
    private int capacity;
    private int insideCount;
    private ArrayList<Element> insideQueue;
    private boolean active;
    private ArrayList<Floor> floors;

    public Elevator(int id, boolean mode, int floor, int destination, boolean direction, int capacity, int insideCount, ArrayList<Element> insideQueue, boolean active, ArrayList<Floor> floors) {
        this.id = id;
        this.mode = mode;
        this.floor = floor;
        this.destination = destination;
        this.direction = direction;
        this.capacity = capacity;
        this.insideCount = insideCount;
        this.insideQueue = insideQueue;
        this.active = active;
        this.floors = floors;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public synchronized boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public int getCapasity() {
        return capacity;
    }

    public void setCapasity(int capasity) {
        this.capacity = capasity;
    }

    public int getInsideCount() {
        return insideCount;
    }

    public void setInsideCount(int insideCount) {
        this.insideCount = insideCount;
    }

    public ArrayList<Element> getInsideQueue() {
        return insideQueue;
    }

    public void setInsideQueue(ArrayList<Element> insideQueue) {
        this.insideQueue = insideQueue;
    }

    public synchronized void move() {
        if (this.floor - this.destination > 0) {
            down();
        } else {
            up();
        }
    }

    private  void down() {
        this.floor--;
        this.direction = false;
    }

    private  void up() {
        this.floor++;
        this.direction = true;
    }

    public synchronized void dropOffPassengers() {
        int insideQueueSize = this.insideQueue.size();
        int k = 0;
        for (int i = 0; i < insideQueueSize; i++) {
            int targetFloor = this.insideQueue.get(k).getTargetFloor();
            if (targetFloor == this.floor) {
                int peopleCount = this.insideQueue.get(k).getPeopleCount();
                floors.get(this.floor).addInsideCount(peopleCount);
                this.insideQueue.remove(k);
                this.insideCount = this.insideCount - peopleCount;
            } else {
                k++;
            }
        }
    }

    public void pickUpPassengers() {
        while (this.insideCount < this.capacity) {
            if (!floors.get(this.floor).getFloorQueue().isEmpty()) {
                int newPassengersCount = this.capacity - this.insideCount;
                int waitingCount = floors.get(this.floor).getFloorQueue().peek().getPeopleCount();
                if (waitingCount > newPassengersCount) {
                    int targetFloor = floors.get(this.floor).getFloorQueue().peek().getTargetFloor();
                    this.getInsideQueue().add(new Element(newPassengersCount, targetFloor));
                    floors.get(this.floor).getFloorQueue().peek().setPeopleCount(waitingCount - newPassengersCount);
                    this.insideCount = this.insideCount + newPassengersCount;
                    break;
                } else {
                    int targetFloor = floors.get(this.floor).getFloorQueue().poll().getTargetFloor();
                    this.getInsideQueue().add(new Element(waitingCount, targetFloor));
                    this.insideCount = this.insideCount + waitingCount;

                }
            } else {
                break;
            }
        }
        
    }

    public  boolean areThereAnyPassengersInFloor() {
        if(floors.get(this.destination).getFloorQueueCount() == 0){
            return false;
        }
        return true;
    }

    public  synchronized void targetingWithPassengers() {
        int newTarget = 5;
        boolean test = false;
        for (int i = 0; i < insideQueue.size(); i++) {
            if (insideQueue.get(i).getTargetFloor() < newTarget) {
                test = true;
                newTarget = insideQueue.get(i).getTargetFloor();
            }
        }
        if (test) {
            this.destination = newTarget;
        } else {
            this.destination = 0;
        }
    }

    public synchronized void checkFloor() { 
        while (this.insideCount < this.capacity && !(this.direction)) {
            if (!floors.get(this.floor).getFloorQueue().isEmpty()) {
                int newPassengersCount = this.capacity - this.insideCount;
                int waitingCount = floors.get(this.floor).getFloorQueue().peek().getPeopleCount();
                if (waitingCount > newPassengersCount) {
                    int targetFloor = floors.get(this.floor).getFloorQueue().peek().getTargetFloor();
                    this.getInsideQueue().add(new Element(newPassengersCount, targetFloor));
                    floors.get(this.floor).getFloorQueue().peek().setPeopleCount(waitingCount - newPassengersCount);
                    this.insideCount = this.insideCount + newPassengersCount;
                    break;
                } else {
                    int targetFloor = floors.get(this.floor).getFloorQueue().poll().getTargetFloor();
                    this.getInsideQueue().add(new Element(waitingCount, targetFloor));
                    this.insideCount = this.insideCount + waitingCount;

                }
            } else {
                break;
            }
        }
    }

    public  synchronized void targetingWithFloors() {
        if (this.direction) { // up
            checkAllFloors();
        } else { // down
            if (floors.get(this.floor).getFloorQueueCount() > 0) {
                this.destination = this.floor;
            } else {
                this.destination = 0;
                //checkAllFloors();
            }
        }
        if (this.destination == this.floor) {
            pickUpPassengers();
            targetingWithPassengers();
        }

    }

    public void checkAllFloors() {
        for (int i = floors.size() - 1; i >= 0; i--) {
            if (!floors.get(i).getFloorQueue().isEmpty()) {
                this.destination = i;
                break;
            }
        }
    }

}
