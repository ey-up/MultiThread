package ElevatorsMultiThread;

import java.util.List;

public class ElevatorThread {

    private Elevator elevator;

    private Thread elevatorThread;

    public ElevatorThread(Elevator elevator) {
        this.elevator = elevator;

    }

    private void control() {
        while (elevator.isActive()) {
            try {
                Thread.sleep(200);
                if (elevator.getFloor() == elevator.getDestination()) {
                    elevator.dropOffPassengers();
                    if (elevator.getInsideCount() == 0) {
                        if ((!elevator.isMode()) && elevator.getId() != 0) {
                            elevator.setActive(false);
                            break;
                        }
                        elevator.targetingWithFloors();
                        elevator.move();
                    } else {
                        elevator.targetingWithPassengers();
                        elevator.move();
                    }
                } else {
                    /*
                     if(elevator.getInsideQueue().isEmpty() && elevator.areThereAnyPassengersInFloor()){
                        elevator.targetingWithFloors();
                    }
                     */
                    elevator.checkFloor();
                    elevator.move();
                }
            } catch (InterruptedException e) {
                System.out.println("ElevatorThread.java");
            }
        }
    }

    synchronized void startThread() {
        elevator.setActive(true);
        elevator.setMode(true);
        elevatorThread = new Thread(() -> {
            control();
        });
        elevatorThread.start();
    }

    synchronized void stopThread() {
        elevator.setMode(false);
    }

}
